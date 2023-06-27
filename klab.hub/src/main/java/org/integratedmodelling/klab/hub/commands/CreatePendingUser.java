package org.integratedmodelling.klab.hub.commands;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.UserRepository;

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
		user.setRegistrationDate(LocalDateTime.now());
		userRepository.save(user);
		return user;
	}

}
