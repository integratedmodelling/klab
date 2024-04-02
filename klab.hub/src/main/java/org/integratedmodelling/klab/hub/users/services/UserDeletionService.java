package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.stereotype.Service;

@Service
public abstract interface UserDeletionService {
	
	abstract void deleteUser(String username);
	
	abstract void deleteUserLdap(String username);
	
	abstract void deleteUserLdap(User user);
}
