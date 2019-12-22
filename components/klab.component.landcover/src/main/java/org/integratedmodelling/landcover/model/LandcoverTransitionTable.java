package org.integratedmodelling.landcover.model;

import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.owl.IntelligentMap;

public class LandcoverTransitionTable {

	class Transition {
		boolean always;
		boolean never;
		Long minimumAge;
		ITimeInstant after;
		IExpression selector;
	}
	
	/*
	 * map the source concept to its transitions to the target concept, using
	 * specificity-driven transitive search for all.
	 */
	IntelligentMap<IntelligentMap<Transition>> transitions = new IntelligentMap<>();
	public Transition DEFAULT_TRANSITION;	
	
	public LandcoverTransitionTable() {
		this.DEFAULT_TRANSITION = new Transition();
		this.DEFAULT_TRANSITION.always = true;
	}
	
	public void parse(IKimTable table) {

	}
	
}
