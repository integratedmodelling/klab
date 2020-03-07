package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.DoStatement;

public class KActorsDo extends KActorsStatement {
	
	public KActorsDo(DoStatement doStatement, KActorCodeStatement parent) {
		super(doStatement, parent, Type.DO_STATEMENT);
	}

}
