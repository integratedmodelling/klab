package org.integratedmodelling.klab.hub.commands;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.api.User;
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
		user.getAgreements().stream().findFirst().get().getGroupEntries()
			.forEach(e -> repository
					.findByNameIgnoreCase(e.getGroupName())
					.ifPresent(group -> groups.add(group)));
		return groups;
	}
}
