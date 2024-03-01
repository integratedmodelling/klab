package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class DeleteMongoNode {
	
	private String name;
	private MongoNodeRepository repository;
	
	public DeleteMongoNode(String name, MongoNodeRepository repository) {
		super();
		this.name = name;
		this.repository = repository;
	}
	
	public void execute() {
		repository.findByNameIgnoreCase(name).ifPresent(node -> {
			repository.delete(node);
		});
	}

}
