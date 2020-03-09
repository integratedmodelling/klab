package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.IfStatement;

public class KActorsIf extends KActorsStatement {
	
	public KActorsIf(IfStatement ifStatement, KActorCodeStatement parent) {
		super(ifStatement, parent, Type.IF_STATEMENT);
	}

}
