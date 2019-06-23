package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.engine.indexing.SearchContext.Constraint;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.NumberUtils;

public enum Indexer {

    INSTANCE;

    private Directory index;
    private IndexWriter writer;
    private StandardAnalyzer analyzer;
    private ReferenceManager<IndexSearcher> searcherManager;
    private ControlledRealTimeReopenThread<IndexSearcher> nrtReopenThread;
    // private QueryParser namespaceRemover;

    public static final int MAX_RESULT_COUNT = 9;

    private Indexer() {
        try {
            this.index = new RAMDirectory(); // new
                                             // MMapDirectory(Configuration.INSTANCE.getDataPath("index").toPath());
            this.analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
            this.writer = new IndexWriter(index, config);

            this.searcherManager = new SearcherManager(writer, true, true, null);
            /**
             * Thread supporting near-realtime index background refresh
             */
            nrtReopenThread = new ControlledRealTimeReopenThread<IndexSearcher>(writer, searcherManager, 1.0, 0.1);
            nrtReopenThread.setName("NRT Reopen Thread");
            nrtReopenThread.setPriority(Math.min(Thread.currentThread().getPriority() + 2, Thread.MAX_PRIORITY));
            nrtReopenThread.setDaemon(true);
            nrtReopenThread.start();

        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    public Match index(IKimStatement object, String namespaceId) {

        SearchMatch ret = null;
        Set<IKimConcept.Type> semanticType = null;

        if (object.isErrors()) {
            return null;
        }

        if (object instanceof IKimConceptStatement) {

            ret = new SearchMatch(Match.Type.CONCEPT, ((IKimConceptStatement) object).getType());
            ret.setDescription(((IKimConceptStatement) object).getDocstring());
            ret.setId(namespaceId + ":" + ((IKimConceptStatement) object).getName());
            ret.setName(((IKimConceptStatement) object).getName());

            semanticType = (((IKimConceptStatement) object).getType());

            // concept that 'equals' something should index its definition with high weight
            // concept that 'is' something should index its parent's definition with lower
            // weight

            for (IKimScope child : object.getChildren()) {
                if (child instanceof IKimConceptStatement) {
                    index((IKimConceptStatement) child, namespaceId);
                }
            }

        } else if (object instanceof IKimModel && ((IKimModel) object).isSemantic()) {

            ret = new SearchMatch(Match.Type.MODEL, ((IKimModel) object).getObservables().get(0).getMain().getType());
            ret.setDescription(((IKimModel) object).getDocstring());
            ret.setName(((IKimModel) object).getName());
            ret.setId(((IKimModel) object).getName());
            semanticType = (((IKimModel) object).getObservables().get(0).getMain().getType());

        } else if (object instanceof IKimObserver) {

            ret = new SearchMatch(Match.Type.OBSERVATION, ((IKimObserver) object).getObservable().getMain().getType());
            ret.setDescription(((IKimObserver) object).getDocstring());
            ret.setName(((IKimObserver) object).getName());
            ret.setId(((IKimObserver) object).getName());
            semanticType = (((IKimObserver) object).getObservable().getMain().getType());
        }

        if (ret != null) {

            try {

                Document document = new Document();

                document.add(new StringField("id", ret.getId(), Store.YES));
                document.add(new StringField("namespace", namespaceId, Store.YES));
                document.add(new TextField("name", ret.getName(), Store.YES));
                document.add(new TextField("description", ret.getDescription(), Store.YES));
                for (String key : ret.getIndexableFields().keySet()) {
                    document.add(new TextField(key, ret.getIndexableFields().get(key), Store.YES));
                }

                // index type and concepttype as ints
                document.add(new IntPoint("ctype", Kim.INSTANCE.getFundamentalType(ret.conceptType).ordinal()));
                document.add(new IntPoint("mtype", ret.getMatchType().ordinal()));
                document.add(new IntPoint("abstract", ret.isAbstract() ? 1 : 0));
                // ..store them
                document.add(new StoredField("vctype", Kim.INSTANCE.getFundamentalType(ret.conceptType).ordinal()));
                document.add(new StoredField("vmtype", ret.getMatchType().ordinal()));
                document.add(new StoredField("smtype", encodeType(semanticType)));

                this.writer.addDocument(document);

            } catch (Throwable e) {
                throw new KlabInternalErrorException(e);
            }
        }

        return ret;
    }

    private String encodeType(Set<Type> semanticType) {

        String ret = "";
        if (semanticType != null) {
            for (Type type : semanticType) {
                ret += (ret.isEmpty() ? "" : ",") + type.ordinal();
            }
        }
        return ret;
    }

    public void commitChanges() {
        try {
            this.writer.commit();
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    public void updateNamespace(IKimNamespace namespace) {

        Thread writerThread = new Thread() {

            @Override
            public void run() {
                try {
                    writer.deleteDocuments(new TermQuery(new Term("namespace", namespace.getName())));
                    writer.commit();
                    for (IKimScope statement : namespace.getChildren()) {
                        if (statement instanceof IKimStatement) {
                            index((IKimStatement) statement, namespace.getName());
                        }
                    }
                    writer.commit();
                } catch (Exception e) {
                    throw new KlabIOException(e);
                }
            }
        };
        writerThread.start();
    }

    public boolean ensureClosed() {
        nrtReopenThread.close();
        if (this.writer.isOpen()) {
            try {
                this.writer.close();
            } catch (IOException e) {
                Logging.INSTANCE.error(e);
                return false;
            }
        }
        return true;
    }

    public List<Match> query(String currentTerm, Context searchContext) {
        return query(currentTerm, searchContext, MAX_RESULT_COUNT);
    }

    public List<Match> query(String currentTerm, Context searchContext, int maxResults) {

        SearchContext context = (SearchContext) searchContext;
        List<Match> ret = new ArrayList<>();

        //
        //        
        //        System.out.println("QUERYING context: ================\n" + ((SearchContext)context).dump());

        for (Constraint constraint : context.getConstraints()) {

            List<Match> cret = new ArrayList<>();

            // FIXME this is to avoid duplications, which should not be necessary if this whole thing
            // could take a month.
            Set<String> ids = new HashSet<>();

            if (constraint.isMatcher()) {
                for (Match match : constraint.getMatches(currentTerm)) {
                    if (!ids.contains(match.getId())) {
                        cret.add(match);
                        ids.add(match.getId());
                    }
                }
            }

            if (constraint.isQuery()) {

                IndexSearcher searcher;
                try {
                    searcher = searcherManager.acquire();
                } catch (IOException e) {
                    // adorable exception management
                    throw new KlabIOException(e);
                }

                try {
                    TopDocs docs = searcher.search(constraint.buildQuery(currentTerm, this.analyzer), maxResults);
                    ScoreDoc[] hits = docs.scoreDocs;

                    for (ScoreDoc hit : hits) {

                        Document document = searcher.doc(hit.doc);

                        Match.Type matchType = Match.Type.values()[Integer.parseInt(document.get("vmtype"))];

                        if (constraint.getType() == matchType && !ids.contains(document.get("id"))) {

                            SearchMatch match = new SearchMatch();
                            match.setId(document.get("id"));
                            match.setName(document.get("name"));
                            match.setDescription(document.get("description"));
                            match.setScore(hit.score);
                            match.setSemantics(decodeType(document.get("smtype")));
                            match.setMatchType(matchType);
                            match.getConceptType()
                                    .add(IKimConcept.Type.values()[Integer.parseInt(document.get("vctype"))]);

                            cret.add(match);
                            ids.add(document.get("id"));
                        }
                    }

                } catch (Exception e) {
                    throw new KlabIOException(e);
                } finally {
                    try {
                        searcherManager.release(searcher);
                    } catch (IOException e) {
                        // fucking unbelievable, they want it in finally and make it throw a checked exception
                        throw new KlabIOException(e);
                    }
                }
            }

            if (constraint.isFilter()) {
                List<Match> fret = new ArrayList<>();
                for (Match match : cret) {
                    if (constraint.filter(match)) {
                        fret.add(match);
                    }
                }
                cret.clear();
                for (Match match : fret) {
                    if (!ids.contains(match.getId())) {
                        cret.add(match);
                        ids.add(match.getId());
                    }
                }
            }

            ret.addAll(cret);

        }

        //		System.out.println("MATCHES: ==============================\n");
        //		int i = 0;
        //        for (Match match : ret) {
        //        	System.out.println(++i + "] " + match);
        //        }

        return ret;
    }

    private Set<Type> decodeType(String string) {
        EnumSet<Type> ret = EnumSet.noneOf(Type.class);
        for (int n : NumberUtils.intArrayFromString(string)) {
            ret.add(Type.values()[n]);
        }
        return ret;
    }
}
