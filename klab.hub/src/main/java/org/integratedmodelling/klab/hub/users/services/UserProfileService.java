package org.integratedmodelling.klab.hub.users.services;

import java.util.Set;

import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
	
	abstract ProfileResource updateUserByProfile(ProfileResource profile);
	abstract ProfileResource getUserProfile(String username);
	abstract ProfileResource getUserSafeProfile(User user);
	abstract ProfileResource getRawUserProfile(String username);
	abstract ProfileResource getCurrentUserProfile();
	abstract Set<ProfileResource> getAllUserProfiles();

}
