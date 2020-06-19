package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.kactors.Annotation;
import org.integratedmodelling.kactors.kactors.Definition;

public class KActorsAction extends KActorCodeStatement implements IKActorsAction {

	private String name;
	private KActorsConcurrentGroup code;
	private Type type = Type.ACTION;

	public KActorsAction(Definition definition, KActorsBehavior parent) {
		super(definition, parent);
		this.name = definition.getName();
		if (definition.getAnnotations() != null) {
			for (Annotation annotation : definition.getAnnotations()) {
				this.getAnnotations().add(new KActorsAnnotation(annotation));
			}
		}
		this.code = new KActorsConcurrentGroup(definition.getBody().getLists(), this);
		if (definition.isActor()) {
			this.type = Type.ACTOR;
		} else if (definition.isComponent()) {
			this.type = Type.COMPONENT;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IKActorsStatement getCode() {
		return code;
	}

	@Override
	public Type getType() {
		return type;
	}

}
