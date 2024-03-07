package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class CreateMongoGroup {
	
	private MongoGroup group;
	private MongoGroupRepository repository;
	
	public CreateMongoGroup(MongoGroup group, MongoGroupRepository repository) {
		super();
		this.group = group;
		this.repository = repository;
	}
	
	public MongoGroup execute() {
		return repository.insert(group);
	}

}
