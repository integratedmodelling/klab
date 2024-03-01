package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class GetMongoGroupById {

	MongoGroupRepository repo;
	String id;
	
	public GetMongoGroupById(String id, MongoGroupRepository repository) {
		this.repo = repository;
		this.id = id;
	}

	public MongoGroup execute() {
		return repo.findById(id)
				.orElseThrow(() -> new NullPointerException(id + " was not found in the MongoGroup Collection"));
	}

}
