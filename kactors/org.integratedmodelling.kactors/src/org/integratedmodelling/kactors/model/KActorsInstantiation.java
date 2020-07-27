package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.Instantiation;
import org.integratedmodelling.kactors.kactors.ActorInstantiation;
import org.integratedmodelling.kim.api.IParameters;

public class KActorsInstantiation extends KActorsStatement implements Instantiation {
	
	String behavior;
	KActorsArguments arguments = null;
	
	public KActorsInstantiation(ActorInstantiation statement, KActorCodeStatement parent) {
		super(statement, parent, Type.INSTANTIATION);
		this.behavior = statement.getBehavior();
		if (statement.getParameters() != null) {
			this.arguments = new KActorsArguments(statement.getParameters());
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


}
