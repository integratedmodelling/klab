package org.integratedmodelling.klab.hub.commands;

import java.util.Set;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.UserRepository;

public class UpdateUsers {
	
	private Set<User> users;
	private UserRepository userRepository;

	
	public UpdateUsers(Set<User> users, UserRepository userRepository) {
		super();
		this.users = users;
		this.userRepository = userRepository;
	}

	public void execute() {
		for(User user: users) {
			userRepository.save(user);
		}
	}

}
