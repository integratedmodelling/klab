package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assignment;
import org.integratedmodelling.kactors.api.IKActorsValue;

public class KActorsAssignment extends KActorsStatement implements Assignment {
	
	public KActorsAssignment(org.integratedmodelling.kactors.kactors.Assignment assignment, KActorCodeStatement parent) {
		super(assignment, parent, Type.ASSIGNMENT);
	}

	@Override
	public String getVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKActorsValue getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
