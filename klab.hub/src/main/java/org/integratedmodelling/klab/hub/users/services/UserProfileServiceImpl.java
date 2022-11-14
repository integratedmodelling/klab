package org.integratedmodelling.klab.hub.users.services;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.exception.UserByEmailDoesNotExistException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	private UserRepository userRepository;
	
	private ObjectMapper objectMapper;
	
	public UserProfileServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
		super();
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public ProfileResource updateUserByProfile(ProfileResource profile) {
		User user = userRepository.findByNameIgnoreCase(profile.getUsername())
				.filter(u -> u.getUsername().equals(profile.getUsername()))
				.orElseThrow(() ->  
					new KlabException("Current User context does match updated profile username"));
		user.updateFromProfileResource(profile);
		User updatedUser = new UpdateUser(user, userRepository).execute();
		ProfileResource updatedProfile = objectMapper.convertValue(updatedUser, ProfileResource.class);
		return updatedProfile.getSafeProfile();
	}
	
	@Override
	public ProfileResource getUserSafeProfile(User user) {
		if (user == null) {
			throw new UserDoesNotExistException();
		}
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile.getSafeProfile();
	}

	@Override
	public ProfileResource getUserProfile(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		return getUserSafeProfile(user);
	}

	@Override
	public ProfileResource getCurrentUserProfile() {
	    KeycloakAuthenticationToken authentication = 
	            (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		
		 Principal principal = (Principal) authentication.getPrincipal();

		    String preferredUsername = "";

		    if (principal instanceof KeycloakPrincipal) {
		        KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
		        AccessToken token = kPrincipal.getKeycloakSecurityContext().getToken();
		        preferredUsername = token.getPreferredUsername();
		    }
		User user = userRepository.findByNameIgnoreCase(preferredUsername)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile.getSafeProfile();
	}

	@Override
	public Set<ProfileResource> getAllUserProfiles() {
		Set<ProfileResource> profiles = new HashSet<>();
		userRepository.findAll().forEach(user -> profiles.add(
				objectMapper.convertValue(user, ProfileResource.class).getSafeProfile()));
		return profiles;
	}

	@Override
	public ProfileResource getRawUserProfile(String username) {
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile;
	}

	@Override
	public ProfileResource getUserProfileByEmail(String email) {
		User user = userRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() ->  
					new UserByEmailDoesNotExistException(email));
		return getUserSafeProfile(user);
	}

}
