package org.integratedmodelling.kactors.api;

import org.integratedmodelling.klab.utils.Pair;

public interface IKActorStatement {

	String getSourceCode();
	
	Pair<Integer, Integer> getBegin();
	
	Pair<Integer, Integer> getEnd();
	
}
