package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.For;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.kactors.ForStatement;

public class KActorsFor extends KActorsStatement implements For {
	
	private IKActorsStatement body;
    private String variable;
    private IKActorsValue iterable;

    public KActorsFor(ForStatement forStatement, KActorCodeStatement parent) {
		super(forStatement, parent, Type.FOR_STATEMENT);
		this.variable = forStatement.getId() == null ? "$" : forStatement.getId();
		this.body = KActorsStatement.create(forStatement.getBody(), this);
		this.iterable = new KActorsValue(forStatement.getValue(), this);
	}

	@Override
	public IKActorsStatement getBody() {
		return this.body;
	}

    @Override
    public String getVariable() {
        return this.variable;
    }

    @Override
    public IKActorsValue getIterable() {
        return this.iterable;
    }

    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        visitValue(visitor, iterable, this, action);
        ((KActorsStatement)body).visit(action, visitor);
        super.visit(action, visitor);
    }
    
}
