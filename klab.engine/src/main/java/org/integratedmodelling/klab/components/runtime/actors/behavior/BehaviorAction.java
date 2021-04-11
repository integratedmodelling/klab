package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
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
	private List<IAnnotation> annotations = new ArrayList<>();
	private String viewId;

	public BehaviorAction(IKActorsAction action, Behavior behavior) {
		this.statement = action;
		this.behavior = behavior;
		for (IKimAnnotation annotation : action.getAnnotations()) {
			Annotation a = new Annotation(annotation);
			// translate KActorsValue into actual values
			for (String key : a.keySet()) {
				Object value = a.get(key);
				if (value instanceof KActorsValue) {
					a.put(key, ((KActorsValue)value).getStatedValue());
				}
			}
			this.annotations.add(a);
			
			if (Behavior.viewAnnotations.contains(a.getName())) {
				this.viewId = a.containsKey("id") ? a.get("id", String.class) : action.getName();
			}
			
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

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	@Override
	public IBehavior getBehavior() {
		return behavior;
	}

}
