package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assignment;
import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;

public class KActorsAssignment extends KActorsStatement implements Assignment {

	private String recipient;
	private String variable;
	private KActorsValue value;
	private Scope scope;

	public KActorsAssignment(org.integratedmodelling.kactors.kactors.Assignment assignment,
			KActorCodeStatement parent) {
		super(assignment, parent, Type.ASSIGNMENT);
		if (assignment.getValue() != null) {
			// only happens when saving screwed-up syntax, which everyone will do.
			this.value = new KActorsValue(assignment.getValue(), this);
		}
		this.variable = assignment.getVariable();
		this.recipient = assignment.getRecipient();
		if (assignment.isGlobal()) {
			this.scope = Scope.ACTOR;
		} else if (assignment.isLocal()) {
			this.scope = Scope.ACTION;
		} else {
			this.scope = Scope.FRAME;
		}
	}

	@Override
	public String getVariable() {
		return this.variable;
	}

	@Override
	public IKActorsValue getValue() {
		return this.value;
	}

	@Override
	public String getRecipient() {
		return this.recipient;
	}

    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        visitor.visitValue(value, this, action);
        super.visit(action, visitor);
    }

	@Override
	public Scope getScope() {
		return this.scope;
	}
    

}
