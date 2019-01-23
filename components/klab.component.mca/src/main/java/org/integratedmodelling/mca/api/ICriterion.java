package org.integratedmodelling.mca.api;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;

public interface ICriterion {

	public static enum DataType {
		ORDINAL, BINARY, RATIO
	}

	public static enum Type {
		COST, BENEFIT
	}

	IObservable getObservable();

	IState getState();
	
	boolean isDistributed();

	Type getType();

	String getName();
}
