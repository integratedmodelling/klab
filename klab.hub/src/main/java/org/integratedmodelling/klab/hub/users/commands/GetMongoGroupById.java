package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;

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
