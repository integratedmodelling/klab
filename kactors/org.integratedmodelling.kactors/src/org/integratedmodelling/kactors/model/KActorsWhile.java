package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.While;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.WhileStatement;

public class KActorsWhile extends KActorsStatement implements While {
	
	public KActorsWhile(WhileStatement whileStatement, KActorCodeStatement parent) {
		super(whileStatement, parent, Type.WHILE_STATEMENT);
	}

	@Override
	public IKActorsValue getCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKActorsStatement getBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
