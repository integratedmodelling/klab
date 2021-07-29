package org.integratedmodelling.klab.hub.commands;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.api.adapters.MongoGroupAdapter;
import org.integratedmodelling.klab.rest.Group;

public class GetNodesGroups {
	
	private MongoNode node;
	
	public GetNodesGroups(MongoNode node) {
		super();
		this.node = node;
	}

	public Set<Group> execute() {
		Set<MongoGroup> mongoGroups = node.getGroups();
		Set<Group> groups = new HashSet<>();
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(new MongoGroupAdapter(mongoGroup).convertGroup()));
		
		return groups;
	}

}
