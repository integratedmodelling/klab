package org.integratedmodelling.mca.api;

import org.integratedmodelling.klab.api.knowledge.IObservable;

public interface ICriterion {

	public static enum DataType {
		ORDINAL, BINARY, RATIO
	}

	public static enum Type {
		COST, BENEFIT
	}

	IObservable getObservable();

	boolean isDistributed();

	Type getType();
}
