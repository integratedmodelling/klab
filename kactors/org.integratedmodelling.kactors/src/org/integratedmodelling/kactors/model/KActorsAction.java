package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.kactors.Definition;

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
	
	@Override
	public IKActorsStatement getCode() {
		return code;
	}

}
