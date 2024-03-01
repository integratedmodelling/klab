package org.integratedmodelling.klab.hub.config.dev;

import org.integratedmodelling.klab.hub.api.MongoLever;
import org.integratedmodelling.klab.hub.repository.MongoLeverRepository;

public class CreateMongoLever {
	
	private MongoLever lever;
	private MongoLeverRepository repo;
	
	public CreateMongoLever(MongoLever lever, MongoLeverRepository repo) {
		super();
		this.lever = lever;
		this.repo = repo;
	}
	
	public MongoLever execute() {
		return repo.insert(lever);
	}
}
