package org.integratedmodelling.mca.model;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.mca.api.ICriterion;

public class Criterion implements ICriterion {

	public Criterion(IState state) {
		
	}

	public Criterion(IObservable observable) {
		
	}

	@Override
	public IObservable getObservable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDistributed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
