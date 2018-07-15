package org.integratedmodelling.klab.engine.indexing;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.api.services.IIndexingService.Match.Type;

public class SearchContext implements IIndexingService.Context {

	private List<Match> accepted = new ArrayList<>();
	// these are in OR - anything matching any of these is acceptable
	private List<Constraint> constraints = new ArrayList<>();
	
	class Condition {
	    
	}
	
	class Constraint {
	    
	    // these are in AND
	    List<Condition> conditions = new ArrayList<>();
	    Type type;
	    
	}
	
	@Override
	public SearchContext accept(Match match) {
		accepted.add(match);
		return this; // TODO new context with this as parent, may be end, and constraints for the next match
	}
	
	@Override
	public void remove(int matchIndex) {
		accepted.remove(matchIndex);
		resetFilters();
	}
	
	@Override
	public boolean isEnd() {
	    return false;
	}
	
	@Override
	public boolean isConsistent() {
	    return false;
	}
	
	public SearchContext cancel() {
	    return null;
	}
	
	private void resetFilters() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getUrn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return accepted.isEmpty();
	}

}
