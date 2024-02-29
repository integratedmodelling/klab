package org.integratedmodelling.klab.hub.nodes.commands;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;

public class GetNodesMongoGroups {
	
	public GetNodesMongoGroups(MongoNode node, MongoGroupRepository repository) {
		super();
		this.node = node;
		this.repository = repository;
	}

	private MongoNode node;
	private MongoGroupRepository repository;
	
	public Set<MongoGroup> execute(){
		Set<MongoGroup> groups = new HashSet<>();
		node.getGroups()
			.forEach(e -> repository
					.findByNameIgnoreCase(e.getName())
					.ifPresent(group -> groups.add(group)));
		return groups;
	}
}
