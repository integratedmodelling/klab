package org.integratedmodelling.klab.ide.navigator.model;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.utils.Pair;

public class EActorBehavior extends EKimObject implements IKActorsBehavior {

	EActorBehavior(String id, IKimStatement statement, ENavigatorItem parent) {
		super(id, statement, parent);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 6120904235254835394L;


	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEChildren() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
