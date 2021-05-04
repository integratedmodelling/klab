package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Do;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.kactors.DoStatement;

public class KActorsDo extends KActorsStatement implements Do {
	
	public KActorsDo(DoStatement doStatement, KActorCodeStatement parent) {
		super(doStatement, parent, Type.DO_STATEMENT);
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

    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        // TODO Auto-generated method stub
        super.visit(action, visitor);
    }
    

}
