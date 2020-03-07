package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.Value;

public class KActorsFire extends KActorsStatement {
	
	public KActorsFire(Value value, KActorCodeStatement parent) {
		super(value, parent, Type.FIRE_VALUE);
	}

}
