package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.api.User;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserSuspensionService {

	abstract void suspendUser(String username);

	abstract List<User> getSuspendedUsers();
	
}
