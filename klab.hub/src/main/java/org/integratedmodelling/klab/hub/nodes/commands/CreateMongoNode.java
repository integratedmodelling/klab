package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class CreateMongoNode {
	
	private MongoNode node;
	private MongoNodeRepository repository;
	
	public CreateMongoNode(MongoNode node, MongoNodeRepository repository) {
		super();
		this.node = node;
		this.repository = repository;
	}
	
	public MongoNode execute() {
		return repository.insert(node);
	}

}
