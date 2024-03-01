package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;

public class DeleteMongoGroup {

	private MongoGroup group;
	private MongoGroupRepository repository;
	
	public DeleteMongoGroup(MongoGroup group, MongoGroupRepository repository) {
		super();
		this.group = group;
		this.repository = repository;
	}
	
	public MongoGroup execute() {
		repository.delete(group);
		return group;
	}
}
