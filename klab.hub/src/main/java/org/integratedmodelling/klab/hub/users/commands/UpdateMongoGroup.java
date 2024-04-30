package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class UpdateMongoGroup {
	
	private MongoGroup group;
	private MongoGroupRepository repository;
	
	public UpdateMongoGroup(MongoGroup group, MongoGroupRepository repository) {
		super();
		this.group = group;
		this.repository = repository;
	}
	
	public MongoGroup execute() {
		return repository.save(group);
	}

}
