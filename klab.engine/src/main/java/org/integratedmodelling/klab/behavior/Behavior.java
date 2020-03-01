package org.integratedmodelling.klab.behavior;

import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.klab.api.IStatement;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IMessageHandler;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;

public class Behavior implements IBehavior {

	IKActorsBehavior statement;

	public Behavior(IKActorsBehavior statement) {
		this.statement = statement;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStatement getStatement() {
		return this.statement;
	}

	@Override
	public List<IKimObject> getChildren() {
		// TODO Auto-generated method stub
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

	@Override
	public List<IMessageHandler> getMessageHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMetadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

}
