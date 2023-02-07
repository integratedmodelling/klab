package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.api.User;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserLockingService {

	abstract void lockUser(String username);

	abstract List<User> getLockedUsers();
	
}
