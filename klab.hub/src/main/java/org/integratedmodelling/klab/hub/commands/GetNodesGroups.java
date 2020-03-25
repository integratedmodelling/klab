package org.integratedmodelling.klab.hub.commands;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.api.adapters.MongoGroupAdapter;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.rest.Group;

public class GetNodesGroups {
	
	private MongoNode node;
	private MongoGroupRepository repository;	
	
	public GetNodesGroups(MongoNode node, MongoGroupRepository repository) {
		super();
		this.node = node;
		this.repository = repository;
	}

	public Set<Group> execute() {
		Set<MongoGroup> mongoGroups = new GetNodesMongoGroups(node, repository).execute();
		Set<Group> groups = new HashSet<>();
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(new MongoGroupAdapter(mongoGroup).convertGroup()));
		
		return groups;
	}

}
