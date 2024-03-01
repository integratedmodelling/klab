package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.users.dto.DeletedUser;
import org.springframework.stereotype.Service;

@Service
public abstract interface UserDeletionService {
	
	abstract void deleteUser(String username);
	
	abstract void deleteUserLdap(String username);

	abstract List<DeletedUser> getDeletedUsers();

	abstract DeletedUser getDeletedUser(String username);

}
