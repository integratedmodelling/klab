package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.Statement;

public class KActorsCall extends KActorStatement {

	public enum Type {
		ACTION_CALL,
		IF_STATEMENT,
		DO_STATEMENT,
		WHILE_STATEMENT,
		TEXT_BLOCK,
		VALUE,
		ASSIGNMENT,
		CALL_GROUP
	}
	
	public KActorsCall(Statement statement, KActorStatement parent) {
		super(statement, parent);
	}

	public static KActorsCall create(Statement first) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
