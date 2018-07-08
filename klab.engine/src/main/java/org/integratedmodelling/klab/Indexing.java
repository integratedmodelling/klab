package org.integratedmodelling.klab;

import java.util.Iterator;

import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.indexing.SearchContext;

public enum Indexing implements IIndexingService {

    INSTANCE;

    /**
     * Get a new, empty context to start a query.
     * 
     * @return a new empty context.
     */
    public Context createContext() {
        return new SearchContext();
    }

    @Override
    public Iterator<Match> query(String currentTerm, Context context) {
        return Indexer.INSTANCE.query(currentTerm, (SearchContext) context);
    }

}
