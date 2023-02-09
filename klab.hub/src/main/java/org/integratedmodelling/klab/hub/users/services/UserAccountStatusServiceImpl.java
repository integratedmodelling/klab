package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAccountStatusServiceImpl implements UserAccountStatusService {

	private UserRepository userRepository;
	
	public UserAccountStatusServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getUsersByStatus(AccountStatus accountStatus) {
		return userRepository.getUsersByAccountStatus(accountStatus);
	}

}
