package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Definition;
import org.integratedmodelling.kim.api.IKimAnnotation;

public class KActorsAction extends KActorCodeStatement implements IKActorsAction {

	private IKActorsBehavior behavior;
	private String name;
	KActorsCodeBlock code;

	public KActorsAction(Definition definition, KActorsBehavior parent) {
		super(definition, parent);
		this.behavior = parent;
		this.name = definition.getName();
		this.code = new KActorsCodeBlock(definition.getBody().getLists(), this);
	}

	@Override
	public String getName() {
		return name;
	}

}
