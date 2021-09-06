package org.integratedmodelling.klab.ide.navigator.model;

import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;

public class EActorAction extends EKimObject implements IKActorsAction {

	private IKActorsAction action;

	EActorAction(IKActorsAction behavior, ENavigatorItem parent) {
		super(behavior.getName(), behavior, parent);
		this.action = behavior;
	}

	private static final long serialVersionUID = 6120904235254835394L;

	@Override
	public ENavigatorItem[] getEChildren() {
		return new ENavigatorItem[] {};
	}

	@Override
	public boolean hasEChildren() {
		return false;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return action.getName();
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKActorsStatement getCode() {
		// TODO Auto-generated method stub
		return action.getCode();
	}

	@Override
	public String getTag() {
		return action.getTag();
	}

	@Override
	public List<String> getArgumentNames() {
		return action.getArgumentNames();
	}

	@Override
	public boolean isFunction() {
		return action.isFunction();
	}


}
