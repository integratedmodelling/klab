package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
	
	abstract ProfileResource updateUserByProfile(ProfileResource profile);
	abstract ProfileResource getUserProfile(String username);
	abstract ProfileResource getCurrentUserProfile();

}
