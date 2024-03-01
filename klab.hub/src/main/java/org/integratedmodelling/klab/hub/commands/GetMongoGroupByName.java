package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.exception.GroupDoesNotExistException;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class GetMongoGroupByName {

	private String name;
	private MongoGroupRepository repository;
	
	public GetMongoGroupByName(String name, MongoGroupRepository repository) {
		super();
		this.name = name;
		this.repository = repository;
	}
	
	public MongoGroup execute() {
		return repository.findByNameIgnoreCase(name)
				.orElseThrow(() -> new GroupDoesNotExistException(name + " was not found in the MongoGroupDatabase"));
	}

}
