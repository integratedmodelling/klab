package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.ForStatement;

public class KActorsFor extends KActorsStatement {
	
	public KActorsFor(ForStatement forStatement, KActorCodeStatement parent) {
		super(forStatement, parent, Type.FOR_STATEMENT);
	}

}
