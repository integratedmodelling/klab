package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;

public class BehaviorAction implements IBehavior.Action {
	
	/**
	 * Each action is a sequence of asynchronous call groups.
	 */
	private IKActorsAction statement;
	private Behavior behavior;
	private CallSequence calls;

	public BehaviorAction(IKActorsAction action, Behavior behavior) {
		this.statement = action;
		this.behavior = behavior;
	}

	@Override
	public String getId() {
		return statement.getName();
	}

	@Override
	public String getName() {
		return behavior.getName() + "." + getName();
	}

	@Override
	public IKActorsAction getStatement() {
		return statement;
	}

	@Override
	public List<IKimObject> getChildren() {
		return null;
	}

	@Override
	public List<IAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeprecated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrors() {
		// TODO Auto-generated method stub
		return false;
	}

}
