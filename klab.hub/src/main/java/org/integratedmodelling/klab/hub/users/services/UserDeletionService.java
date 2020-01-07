package org.integratedmodelling.klab.hub.users.services;

import org.springframework.stereotype.Service;

@Service
public abstract interface UserDeletionService {
	
	abstract void deleteUser(String username);

}
