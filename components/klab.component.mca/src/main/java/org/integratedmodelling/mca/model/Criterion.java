package org.integratedmodelling.mca.model;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.mca.api.ICriterion;

public class Criterion implements ICriterion {

	IObservable observable;
	IState state;
	Type type;
	
	public Criterion(IState state, Type type) {
		this.state = state;
		this.observable = state.getObservable();
		this.type = type;
	}

	public Criterion(IObservable observable, Type type) {
		this.observable = observable;
		this.type = type;
	}

	@Override
	public String getName() {
		return observable.getLocalName();
	}
	
	@Override
	public IObservable getObservable() {
		return observable;
	}

	@Override
	public boolean isDistributed() {
		return state != null && state.getScale().isSpatiallyDistributed();
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public IState getState() {
		return state;
	}

}
