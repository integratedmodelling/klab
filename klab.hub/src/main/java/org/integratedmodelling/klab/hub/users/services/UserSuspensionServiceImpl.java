package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSuspensionServiceImpl implements UserSuspensionService {

	private UserRepository userRepository;
	
	public UserSuspensionServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public void suspendUser(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				// TODO how to manage the suspension of a suspended user
				//.filter(u -> u.getAccountStatus() != AccountStatus.suspended)
				.orElseThrow(() -> new BadRequestException("User is not present or already suspended"));
		
		user.setAccountStatus(AccountStatus.suspended);
		userRepository.save(user);
	}

	@Override
	public List<User> getSuspendedUsers() {
		return userRepository.getUsersByAccountStatus(AccountStatus.suspended);
	}

	@Override
	public List<User> getPendingUsers() {
		return userRepository.getUsersByAccountStatus(AccountStatus.pendingActivation);
	}

}
