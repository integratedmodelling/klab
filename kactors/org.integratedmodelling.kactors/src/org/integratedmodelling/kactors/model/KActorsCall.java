package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.Statement;

public class KActorsCall {

	public enum Type {
		ACTION_CALL,
		IF_STATEMENT,
		DO_STATEMENT,
		WHILE_STATEMENT,
		TEXT_BLOCK,
		VALUE,
		CALL_GROUP
	}
	
	public KActorsCall(Statement statement) {
		
	}
	
}
