package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;

public class UpdateUser implements UserCommand{
	
	private User user;
	private UserRepository userRepository;

	
	public UpdateUser(User user, UserRepository userRepository) {
		super();
		this.user = user;
		this.userRepository = userRepository;
	}


	@Override
	public User execute() {
		userRepository.save(user);
		return user;
	}

}
