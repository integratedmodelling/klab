package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;

public class MongoNodeExists {

	private String name;
	private MongoNodeRepository repository;

	public MongoNodeExists(String name, MongoNodeRepository nodeRepository) {
		this.name = name;
		this.repository = nodeRepository;
	}

	public boolean execute() {
		return repository.existsByNameIgnoreCase(name);
	}

}
