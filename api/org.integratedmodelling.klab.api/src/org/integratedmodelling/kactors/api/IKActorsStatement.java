package org.integratedmodelling.kactors.api;

public interface IKActorsStatement extends IKActorsCodeStatement {

	public enum Type {
		ACTION_CALL, IF_STATEMENT, FOR_STATEMENT, DO_STATEMENT, WHILE_STATEMENT, TEXT_BLOCK, FIRE_VALUE, ASSIGNMENT,
		STATEMENT_GROUP
	}

	Type getType();
}
