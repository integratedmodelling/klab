package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.users.User;
import org.springframework.stereotype.Service;

@Service
public interface UserRegistrationService {
	public abstract User registerNewUser(String username, String email);
	public abstract User activateNewUser(String username);
	public abstract User setPassword(String username, String password, String confirm);

}
