package org.integratedmodelling.klab.hub.users.commands;

import java.util.Collection;
import java.util.HashSet;

import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;

public class GetAllMongoGroupNames {

	private MongoGroupRepository repository;

	public GetAllMongoGroupNames(MongoGroupRepository repository) {
		this.repository = repository;
	}

	public Collection<String> execute() {
		Collection<MongoGroup> groups = new GetAllMongoGroups(repository).execute();
		Collection<String> groupNames = new HashSet<>();
		groups.forEach(group -> groupNames.add(group.getName()));
		return groupNames;
	}

}
