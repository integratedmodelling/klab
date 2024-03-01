package org.integratedmodelling.klab.hub.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class GetMongoNodes {
	private MongoNodeRepository repository;
	
	public GetMongoNodes(MongoNodeRepository repository) {
		super();
		this.repository = repository;
	}
	
	public List<MongoNode> execute() {
		return repository.findAll();
	}

}
