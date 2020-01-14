package org.integratedmodelling.klab.hub.groups.commands;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class GetMongoGroupByName {

	private String groupName;
	private MongoGroupRepository repository;
	
	public GetMongoGroupByName(String groupName, MongoGroupRepository repository) {
		super();
		this.groupName = groupName;
		this.repository = repository;
	}
	
	public MongoGroup execute() {
		return repository.findByGroupNameIgnoreCase(groupName)
				.orElseThrow(() -> new BadRequestException(groupName + "by that name was not found"));
	}

}
