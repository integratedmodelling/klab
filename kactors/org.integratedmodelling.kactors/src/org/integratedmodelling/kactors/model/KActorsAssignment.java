package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.Assignment;

public class KActorsAssignment extends KActorsStatement {
	
	public KActorsAssignment(Assignment assignment, KActorCodeStatement parent) {
		super(assignment, parent, Type.ASSIGNMENT);
	}

}
