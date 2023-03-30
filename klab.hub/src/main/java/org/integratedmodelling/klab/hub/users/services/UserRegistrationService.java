package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.springframework.stereotype.Service;

@Service
public interface UserRegistrationService {
	public abstract User registerNewUser(String username, String email, Agreement agreement)
			throws UserExistsException, UserEmailExistsException;
	public abstract User verifyNewUser(String username);
	public abstract User setPassword(String username, String password, String confirm);
	public abstract User registerUser(User user);
}
