package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.kactors.Annotation;
import org.integratedmodelling.kactors.kactors.Definition;

public class KActorsAction extends KActorCodeStatement implements IKActorsAction {

	private String name;
	private KActorsConcurrentGroup code;
	private List<String> argumentNames = new ArrayList<>();
	
	public KActorsAction(Definition definition, KActorsBehavior parent) {
		super(definition, parent);
		this.name = definition.getName();
		if (definition.getAnnotations() != null) {
			for (Annotation annotation : definition.getAnnotations()) {
				this.getAnnotations().add(new KActorsAnnotation(annotation));
			}
		}
		this.code = new KActorsConcurrentGroup(definition.getBody().getLists(), this);
		if (definition.getArguments() != null) {
			argumentNames.addAll(definition.getArguments().getIds());
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
	public List<String> getArgumentNames() {
		return argumentNames;
	}

}
