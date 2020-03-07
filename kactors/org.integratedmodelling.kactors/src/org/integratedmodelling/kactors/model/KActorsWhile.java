package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.WhileStatement;

public class KActorsWhile extends KActorsStatement {
	
	public KActorsWhile(WhileStatement whileStatement, KActorCodeStatement parent) {
		super(whileStatement, parent, Type.WHILE_STATEMENT);
	}

}
