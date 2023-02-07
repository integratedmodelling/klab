package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserLockingServiceImpl implements UserLockingService {

	private UserRepository userRepository;
	
	public UserLockingServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public void lockUser(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				// TODO how to manage locking a locked user
				.filter(u -> u.getAccountStatus() != AccountStatus.locked)
				.orElseThrow(() -> new BadRequestException("User is not present or already locked"));
		
		user.setAccountStatus(AccountStatus.locked);
		userRepository.save(user);
	}

	@Override
	public List<User> getLockedUsers() {
		return userRepository.getUsersByAccountStatus(AccountStatus.locked);
	}

}
