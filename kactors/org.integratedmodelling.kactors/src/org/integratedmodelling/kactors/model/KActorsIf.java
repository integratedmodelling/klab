package org.integratedmodelling.kactors.model;

import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.If;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.IfStatement;
import org.integratedmodelling.klab.utils.Pair;

public class KActorsIf extends KActorsStatement implements If {
	
	public KActorsIf(IfStatement ifStatement, KActorCodeStatement parent) {
		super(ifStatement, parent, Type.IF_STATEMENT);
	}

	@Override
	public IKActorsValue getCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKActorsStatement getThen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pair<IKActorsValue, IKActorsStatement>> getElseIfs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKActorsStatement getElse() {
		// TODO Auto-generated method stub
		return null;
	}

}
