package org.integratedmodelling.kactors.model;

import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.For;
import org.integratedmodelling.kactors.kactors.ForStatement;

public class KActorsFor extends KActorsStatement implements For {
	
	public KActorsFor(ForStatement forStatement, KActorCodeStatement parent) {
		super(forStatement, parent, Type.FOR_STATEMENT);
	}

	@Override
	public IKActorsStatement getBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
