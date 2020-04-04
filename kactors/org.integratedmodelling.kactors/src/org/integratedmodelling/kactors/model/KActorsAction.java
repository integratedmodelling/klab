package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.kactors.Annotation;
import org.integratedmodelling.kactors.kactors.Definition;

public class KActorsAction extends KActorCodeStatement implements IKActorsAction {

	private IKActorsBehavior behavior;
	private String name;
	KActorsConcurrentGroup code;

	public KActorsAction(Definition definition, KActorsBehavior parent) {
		super(definition, parent);
		this.behavior = parent;
		this.name = definition.getName();
		if (definition.getAnnotations() != null) {
			for (Annotation annotation : definition.getAnnotations()) {
				this.getAnnotations().add(new KActorsAnnotation(annotation));
			}
		}
		this.code = new KActorsConcurrentGroup(definition.getBody().getLists(), this);
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IKActorsStatement getCode() {
		return code;
	}

}
