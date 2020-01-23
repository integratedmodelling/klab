package org.integratedmodelling.klab.hub.groups.commands;

import org.integratedmodelling.klab.hub.users.User;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;

public class GetUsersMongoGroups {
	
	public GetUsersMongoGroups(User user, MongoGroupRepository repository) {
		super();
		this.user = user;
		this.repository = repository;
	}

	private User user;
	private MongoGroupRepository repository;
	
	public Set<MongoGroup> execute(){
		Set<MongoGroup> groups = new HashSet<>();
		user.getGroupEntries()
			.forEach(e -> repository
					.findByGroupNameIgnoreCase(e.getGroupName())
					.ifPresent(group -> groups.add(group)));
		return groups;
	}
}
