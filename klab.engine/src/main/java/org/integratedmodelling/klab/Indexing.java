package org.integratedmodelling.klab;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
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
		return SearchContext.createNew();
	}

	@Override
	public List<Match> query(String currentTerm, Context context) {
		return Indexer.INSTANCE.query(currentTerm, (SearchContext) context);
	}

	public Context createContext(Set<Match.Type> matchTypes, Set<IKimConcept.Type> semanticTypes) {
		return new SearchContext(matchTypes, semanticTypes);
	}

    public void releaseNamespace(String namespaceId, IMonitor monitor) {
        // TODO Auto-generated method stub
        
    }

}
