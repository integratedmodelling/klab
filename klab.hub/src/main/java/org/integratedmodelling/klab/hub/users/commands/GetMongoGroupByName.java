package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.integratedmodelling.klab.hub.users.exceptions.GroupDoesNotExistException;

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
