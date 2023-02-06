package org.integratedmodelling.klab.hub.users.services;

import org.springframework.stereotype.Service;

@Service
public interface UserSuspensionService {

	abstract void suspendUser(String username);

}
