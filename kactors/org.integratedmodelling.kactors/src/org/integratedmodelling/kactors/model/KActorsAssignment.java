package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assignment;
import org.integratedmodelling.kactors.api.IKActorsValue;

public class KActorsAssignment extends KActorsStatement implements Assignment {

	private String recipient;
	private String variable;
	private KActorsValue value;

	public KActorsAssignment(org.integratedmodelling.kactors.kactors.Assignment assignment,
			KActorCodeStatement parent) {
		super(assignment, parent, Type.ASSIGNMENT);
		if (value != null) {
			// only happens when saving screwed-up syntax, which everyone will do.
			this.value = new KActorsValue(assignment.getValue(), this);
		}
		this.variable = assignment.getVariable();
		this.recipient = assignment.getRecipient();
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

}
