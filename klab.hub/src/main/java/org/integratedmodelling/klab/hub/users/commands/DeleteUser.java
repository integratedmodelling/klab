package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;

public class DeleteUser implements UserCommand{
	
	private User user;
	private UserRepository userRepository;
	

	
	public DeleteUser(User user, UserRepository userRepository) {
		super();
		this.user = user;
		this.userRepository = userRepository;		
	}


	@Override
	public User execute() {
		userRepository.delete(user);
		return user;
	}

}
