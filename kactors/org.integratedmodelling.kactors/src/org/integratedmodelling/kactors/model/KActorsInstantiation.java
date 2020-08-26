package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.Instantiation;
import org.integratedmodelling.kactors.kactors.ActorInstantiation;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.utils.NameGenerator;

public class KActorsInstantiation extends KActorsStatement implements Instantiation {

	String behavior;
	KActorsArguments arguments = null;

	private String actorBaseName = "a" + NameGenerator.shortUUID();

	public KActorsInstantiation(ActorInstantiation statement, KActorCodeStatement parent) {
		super(statement, parent, Type.INSTANTIATION);
		this.behavior = statement.getBehavior();
		if (statement.getParameters() != null) {
			this.arguments = new KActorsArguments(statement.getParameters());
			if (this.arguments.getData().containsKey("tag")) {
				this.actorBaseName = this.arguments.getData().get("tag").toString();
			}
		}
	}

	@Override
	public String getBehavior() {
		return behavior;
	}

	@Override
	public IParameters<String> getArguments() {
		return arguments;
	}

	@Override
	public String getActorBaseName() {
		return actorBaseName;
	}

	public void setActorBaseName(String actorBaseName) {
		this.actorBaseName = actorBaseName;
	}

}
