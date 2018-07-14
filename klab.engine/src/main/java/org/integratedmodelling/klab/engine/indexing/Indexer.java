package org.integratedmodelling.klab.engine.indexing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.exceptions.KlabIOException;

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

    public Map<String, MatchIterator> indexWorkspace() {
        Map<String, MatchIterator> ret = new HashMap<>();
        for (IProject project : Resources.INSTANCE.getLocalWorkspace().getProjects()) {
            for (INamespace namespace : project.getNamespaces()) {
                for (IKimObject object : namespace.getAllObjects()) {
                    // index it
                }
            }
        }
        return ret;
    }

    public Map<String, MatchIterator> indexNetwork() {
        Map<String, MatchIterator> ret = new HashMap<>();
        return ret;
    }

    public Match index(IKimStatement object) {
        SearchMatch ret = null;
        if (object instanceof IKimConceptStatement) {

            ret = new SearchMatch(Match.Type.CONCEPT, ((IKimConceptStatement) object).getType());
            ret.setDescription(((IKimConceptStatement) object).getDocstring());

            // concept that 'equals' something should index its definition with high weight
            // concept that 'is' something should index its parent's definition with lower weight

            for (IKimScope child : object.getChildren()) {
                if (child instanceof IKimConceptStatement) {
                    index((IKimConceptStatement) child);
                }
            }

        } else if (object instanceof IKimModel) {

            ret = new SearchMatch(Match.Type.MODEL, ((IKimModel) object).getObservables().get(0).getMain().getType());
            ret.setDescription(((IKimModel) object).getDocstring());

        } else if (object instanceof IKimObserver) {

            ret = new SearchMatch(Match.Type.OBSERVATION, ((IKimObserver) object).getObservable().getMain().getType());
            ret.setDescription(((IKimObserver) object).getDocstring());

        }

        if (ret != null) {

            Document document = new Document();

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
