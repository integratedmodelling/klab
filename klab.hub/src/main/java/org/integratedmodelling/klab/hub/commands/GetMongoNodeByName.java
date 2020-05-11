package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class GetMongoNodeByName {
	
	private String name;
	private MongoNodeRepository repository;
	
	public GetMongoNodeByName(String name, MongoNodeRepository repository) {
		super();
		this.name = name;
		this.repository = repository;
	}
	
	public MongoNode execute() {
		return repository.findByNameIgnoreCase(name)
				.orElseThrow(() -> new BadRequestException("Node by that name does not exist"));
	}
	
}
