package org.integratedmodelling.klab.hub.manager;

import java.util.Collection;

import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.service.KlabUserDetailsService;
import org.integratedmodelling.klab.hub.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KlabUserManager {
	
	@Autowired
	private KlabUserDetailsService klabUserDetailsService;
	
	@Autowired
	private ProfileService profileService;
	
	public String getLoggedInUsername() {
		String result = "-unauthenticated user-";
		try {
			Object principal = klabUserDetailsService.getLoggedInAuthentication().getPrincipal();
			result = principal.toString();
		} catch (Throwable e) {
		}
		return result;
	}

	public User getLoggedInUser() {
		String username = getLoggedInUsername();
		User user = getUser(username);
		return user;
	}

	public User getUser(String username) {
		User user = klabUserDetailsService.loadUserByUsername(username);
		return user;
	}

	public ProfileResource getLoggedInUserProfile() {
		User user = getLoggedInUser();
		ProfileResource profile = profileService.getUserProfile(user.getUsername());
		return profile;
	}
	
	public ProfileResource getUserProfile(String username) {
		ProfileResource profile = profileService.getUserProfile(username);
		return profile;
	}
	
	public ProfileResource updateUserProfile(ProfileResource profileResource) {
		User loggedInUser = getLoggedInUser();
		if(loggedInUser.getUsername().equals(profileResource.getUsername())) {
			profileResource.accountStatus = loggedInUser.getAccountStatus();
			}
		loggedInUser.updateFromProfileResource(profileResource);
		klabUserDetailsService.updateUser(loggedInUser);
		ProfileResource profile = getLoggedInUserProfile();
		return profile;
	}
	
	public ProfileResource updateUserRoles(String userId, Collection<Role> roles) {
		User user = getUser(userId);
		user.setRoles(roles);
		klabUserDetailsService.updateUser(user);
		ProfileResource profile = profileService.getUserProfile(user.getUsername());
		return profile;
	}

	public void updateLastLogin(String username) {
		User user = klabUserDetailsService.loadUserByUsername(username);
		user.setLastLogin();
		klabUserDetailsService.updateUser(user);
	}

	public void updateLastEngineConnection(String username) {
		User user = klabUserDetailsService.loadUserByUsername(username);
		user.setLastEngineConnection();
		klabUserDetailsService.updateUser(user);
	}
	
	public void deleteUser(String username) {
		klabUserDetailsService.deleteUser(username);
	}

}
