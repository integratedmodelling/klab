package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

public enum Indexer {

    INSTANCE;

    private Directory index;
    private IndexWriter writer;
    private StandardAnalyzer analyzer;
    private ReferenceManager<IndexSearcher> searcherManager;
    private ControlledRealTimeReopenThread<IndexSearcher> nrtReopenThread;

    public static final int MAX_RESULT_COUNT = 9;
    
    private Indexer() {
        try {
            this.index = new RAMDirectory(); // new MMapDirectory(Configuration.INSTANCE.getDataPath("index").toPath());
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
        
        if (object.isErrors()) {
            return null;
        }
        
        if (object instanceof IKimConceptStatement) {

            ret = new SearchMatch(Match.Type.CONCEPT, ((IKimConceptStatement) object).getType());
            ret.setDescription(((IKimConceptStatement) object).getDocstring());
            ret.setId(namespaceId + ":" + ((IKimConceptStatement) object).getName());
            ret.setName(((IKimConceptStatement) object).getName());

            // concept that 'equals' something should index its definition with high weight
            // concept that 'is' something should index its parent's definition with lower weight

            for (IKimScope child : object.getChildren()) {
                if (child instanceof IKimConceptStatement) {
                    index((IKimConceptStatement) child, namespaceId);
                }
            }

        } else if (object instanceof IKimModel) {

            ret = new SearchMatch(Match.Type.MODEL, ((IKimModel) object).getObservables().get(0).getMain().getType());
            ret.setDescription(((IKimModel) object).getDocstring());
            ret.setName(((IKimModel) object).getName());
            ret.setId(((IKimModel) object).getName());

        } else if (object instanceof IKimObserver) {

            ret = new SearchMatch(Match.Type.OBSERVATION, ((IKimObserver) object).getObservable().getMain().getType());
            ret.setDescription(((IKimObserver) object).getDocstring());
            ret.setName(((IKimObserver) object).getName());
            ret.setId(((IKimObserver) object).getName());
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
                this.writer.addDocument(document);

            } catch (Throwable e) {
                throw new KlabInternalErrorException(e);
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
    
    public void updateNamespace(INamespace namespace) {
        
        Thread writerThread = new Thread() {

            @Override
            public void run() {
                try {
                    // delete everything within namespace
                    // reindex namespace
//                    for (int i = 0; i < 100000; ++i) {
//                        Document doc = new Document();
//                        doc.add(new LongPoint("time", System.currentTimeMillis()));
//                        doc.add(new StringField("counter", "" + i, Field.Store.YES));
//                        writer.addDocument(doc);
//                        searcherManager.maybeRefresh();
//                        Thread.sleep(100);
//                    }
//                    writer.commit();
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
        
        SearchContext context = (SearchContext)searchContext;
        List<Match> ret = new ArrayList<>();
        try {
            IndexSearcher searcher = searcherManager.acquire();
            TopDocs docs = searcher.search(context.buildQuery(currentTerm, this.analyzer), maxResults);
            ScoreDoc[] hits = docs.scoreDocs;
            for (ScoreDoc hit : hits) {
                
                Document document = searcher.doc(hit.doc);
                
                SearchMatch match = new SearchMatch();
                match.setId(document.get("id"));
                match.setName(document.get("name"));
                match.setDescription(document.get("description"));
                match.setScore(hit.score);
                match.setMatchType(Match.Type.values()[Integer.parseInt(document.get("vmtype"))]);
                match.getConceptType().add(IKimConcept.Type.values()[Integer.parseInt(document.get("vctype"))]);
                
                ret.add(match);
                
            }
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
        return ret;
    }
}
