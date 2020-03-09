package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.Statement;

public class KActorsText extends KActorsStatement {
	
	public KActorsText(Statement statement, KActorCodeStatement parent) {
		super(statement, parent, Type.TEXT_BLOCK);
	}

}
