package org.integratedmodelling.klab.hub.commands;

import java.util.Collection;
import java.util.HashSet;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

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
