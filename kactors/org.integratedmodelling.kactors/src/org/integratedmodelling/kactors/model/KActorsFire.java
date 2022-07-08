package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.FireValue;
import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.kactors.Value;

public class KActorsFire extends KActorsStatement implements FireValue {
	
	private KActorsValue value;

	public KActorsFire(KActorCodeStatement parent) {
		super(null, parent, Type.FIRE_VALUE);
		this.value = KActorsValue.anytrue();
	}
	
	public KActorsFire(Value value, KActorCodeStatement parent) {
		super(value, parent, Type.FIRE_VALUE);
		this.value = new KActorsValue(value, parent);
	}

	@Override
	public IKActorsValue getValue() {
		return value;
	}


    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        if (this.value != null) {
            visitValue(visitor, value, this, action);
        }
        super.visit(action, visitor);
    }
    
}
