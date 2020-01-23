package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class DeleteMongoNode {
	
	private MongoNode node;
	private MongoNodeRepository repository;
	
	public DeleteMongoNode(MongoNode node, MongoNodeRepository repository) {
		super();
		this.node = node;
		this.repository = repository;
	}
	
	public void execute() {
		repository.delete(node);
	}

}
