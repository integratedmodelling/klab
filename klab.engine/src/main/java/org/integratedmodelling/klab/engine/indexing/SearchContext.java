package org.integratedmodelling.klab.engine.indexing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;

public class SearchContext implements IIndexingService.Context {

	private List<Match> accepted = new ArrayList<>();
	Set<IKimConcept.Type> typeFilter = EnumSet.noneOf(IKimConcept.Type.class);
	
	@Override
	public int accept(Match match) {
		accepted.add(match);
		resetFilters();
		return accepted.size() - 1;
	}
	
	@Override
	public void remove(int matchIndex) {
		accepted.remove(matchIndex);
		resetFilters();
	}
	
	
	private void resetFilters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canChooseMore() {
		// TODO Auto-generated method stub
		return false;
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
