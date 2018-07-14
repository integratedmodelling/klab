package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

public enum Indexer {

    INSTANCE;

    enum IndexType {
        OPERATORS,
        OBSERVATIONS,
        URNS,
        CONCEPTS,
        IDENTITIES,
        ATTRIBUTES,
        REALMS,
        SUBJECTS,
        PROCESSES,
        QUALITIES,
        EVENTS,
        RELATIONSHIPS
    }

    private Directory index;
    private Map<String, Lookup> suggesters = new HashMap<>();
    private IndexWriter writer;
    private StandardAnalyzer analyzer;

    private Indexer() {
        try {
            this.index = new MMapDirectory(Configuration.INSTANCE.getDataPath("index").toPath());
            this.analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
            this.writer = new IndexWriter(index, config);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    public Map<String, MatchIterator> indexNetwork() {
        Map<String, MatchIterator> ret = new HashMap<>();
        return ret;
    }

    public Match index(IKimStatement object, String namespaceId) {
        SearchMatch ret = null;
        if (object instanceof IKimConceptStatement) {

            ret = new SearchMatch(Match.Type.CONCEPT, ((IKimConceptStatement) object).getType());
            ret.setDescription(((IKimConceptStatement) object).getDocstring());
            ret.setId(((IKimConceptStatement) object).getName());
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
                // type and concepttype as ints
                document.add(new IntPoint("ctype", Kim.INSTANCE.getFundamentalType(ret.conceptType).ordinal()));
                document.add(new IntPoint("mtype", ret.getMatchType().ordinal()));
                document.add(new IntPoint("abstract", ret.isAbstract() ? 1 : 0));

                this.writer.addDocument(document);
                
            } catch (Throwable e) {
                throw new KlabInternalErrorException(e);
            }
        }

        return ret;
    }

    public boolean ensureClosed() {
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

    public Iterator<Match> query(String currentTerm, SearchContext context) {
        // TODO Auto-generated method stub
        return null;
    }
}
