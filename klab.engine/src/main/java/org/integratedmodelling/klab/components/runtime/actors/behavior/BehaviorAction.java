package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.utils.StringUtils;

public class BehaviorAction implements IBehavior.Action {

	/**
	 * Each action is a sequence of asynchronous call groups.
	 */
	private IKActorsAction statement;
	private Behavior behavior;
	private CallSequence calls;
	private List<IAnnotation> annotations = new ArrayList<>();

	public BehaviorAction(IKActorsAction action, Behavior behavior) {
		this.statement = action;
		this.behavior = behavior;
		for (IKimAnnotation annotation : action.getAnnotations()) {
			this.annotations.add(new Annotation(annotation));
		}
		
	}

	@Override
	public String getId() {
		return statement.getName();
	}

	@Override
	public String toString() {
		return "#(" + StringUtils.abbreviate(statement.getSourceCode(), 26) + ")";
	}
	
	@Override
	public String getName() {
		return behavior.getName() + "." + this.getId();
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
		return annotations;
	}

	@Override
	public boolean isDeprecated() {
		return this.statement.isDeprecated();
	}

	@Override
	public boolean isErrors() {
		return false; // this.statement.getErrors().size() > 0;
	}

}
