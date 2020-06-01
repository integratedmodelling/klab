package org.integratedmodelling.kactors.model;

import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.FireValue;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Value;

public class KActorsFire extends KActorsStatement implements FireValue {
	
	private KActorsValue value;

	public KActorsFire(Value value, KActorCodeStatement parent) {
		super(value, parent, Type.FIRE_VALUE);
		this.value = new KActorsValue(value, parent);
	}

	@Override
	public IKActorsValue getValue() {
		return value;
	}

}
