package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.FireValue;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Value;

public class KActorsFire extends KActorsStatement implements FireValue {
	
	public KActorsFire(Value value, KActorCodeStatement parent) {
		super(value, parent, Type.FIRE_VALUE);
	}

	@Override
	public IKActorsValue getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
