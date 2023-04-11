package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.users.controllers.criteria.UserProfileCriteria;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

	abstract ProfileResource updateUserByProfile(ProfileResource profile);
	abstract ProfileResource getUserProfile(String username);
	abstract ProfileResource getUserProfileByEmail(String emai);
	abstract ProfileResource getUserSafeProfile(User user);
	abstract ProfileResource getRawUserProfile(String username);
	abstract ProfileResource getCurrentUserProfile(boolean remote);
	abstract Set<ProfileResource> getAllUserProfiles();

	abstract List<ProfileResource> getAllUsersByCriteria(UserProfileCriteria criteria);
}
