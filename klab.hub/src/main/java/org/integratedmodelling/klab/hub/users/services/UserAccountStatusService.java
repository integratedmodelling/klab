package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;

public interface UserAccountStatusService {
		
	abstract List<User> getUsersByStatus(AccountStatus accountStatus);
}
