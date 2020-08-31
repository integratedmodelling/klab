package org.integratedmodelling.klab.hub.commands;

import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class GetAllMongoGroups {
	private MongoGroupRepository repository;
	
	public GetAllMongoGroups(MongoGroupRepository repository) {
		super();
		this.repository = repository;
	}
	
	public List<MongoGroup> execute() {
		return repository.findAll();		
	}
	
}
