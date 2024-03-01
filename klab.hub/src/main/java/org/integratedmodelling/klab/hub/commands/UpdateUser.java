package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.UserRepository;

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
