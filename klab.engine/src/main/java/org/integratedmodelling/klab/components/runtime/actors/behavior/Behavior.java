package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.klab.api.IStatement;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.data.Metadata;

public class Behavior implements IBehavior {

	IKActorsBehavior statement;
	// action IDs in order of declaration
	List<String> actionIds = new ArrayList<>();
	Map<String, BehaviorAction> actions = new HashMap<>();
	IMetadata metadata = new Metadata();
	
	public Behavior(IKActorsBehavior statement) {
		this.statement = statement;
	}

	@Override
	public String getId() {
		return this.statement.getName();
	}

	@Override
	public String getName() {
		return statement.getName();
	}

	@Override
	public IStatement getStatement() {
		return this.statement;
	}

	@Override
	public List<IKimObject> getChildren() {
		List<IKimObject> ret = new ArrayList<>();
		for (String id : actionIds) {
			ret.add(actions.get(id));
		}
		return ret;
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
	public IMetadata getMetadata() {
		return metadata;
	}

	@Override
	public Type getDestination() {
		return statement.getType();
	}

	@Override
	public List<Action> getActions(String... match) {
		List<Action> ret = new ArrayList<>();
		return ret;
	}

}
