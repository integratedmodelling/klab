package org.integratedmodelling.klab.hub.users.commands;

import java.util.Arrays;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.User.AccountStatus;

public class CreatePendingUser implements UserCommand {

	private UserRepository userRepository;
	private User user;
	
	public CreatePendingUser(UserRepository userRepository, User user) {
		super();
		this.userRepository = userRepository;
		this.user = user;
	}

	@Override
	public User execute() {
		user.setAccountStatus(AccountStatus.pendingActivation);
		user.setRoles(Arrays.asList(Role.ROLE_USER));
		user.setRegistrationDate();
		userRepository.save(user);
		return user;
	}

}
