package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class MongoGroupExists {
	
	public MongoGroupExists(String name, MongoGroupRepository repository) {
		super();
		this.name = name;
		this.repository = repository;
	}

	private String name;
	private MongoGroupRepository repository;
	
	public boolean execute() {
		return repository.findByNameIgnoreCase(name).isPresent();
	}

}
