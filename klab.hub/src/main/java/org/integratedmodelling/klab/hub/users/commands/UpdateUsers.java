package org.integratedmodelling.klab.hub.users.commands;

import java.util.Set;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;

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
