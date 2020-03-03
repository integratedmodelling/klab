package org.integratedmodelling.klab.hub.users.services;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.commands.UpdateUser;
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
		User user = userRepository.findByUsernameIgnoreCase(profile.getUsername())
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
			throw new BadRequestException("No user define");
		}
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile.getSafeProfile();
	}

	@Override
	public ProfileResource getUserProfile(String username) {
		User user = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() ->  
					new BadRequestException("User does not exist"));
		return getUserSafeProfile(user);
	}

	@Override
	public ProfileResource getCurrentUserProfile() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() ->  
					new BadRequestException("User does not exist"));
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
		User user = userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() ->  
					new BadRequestException("User does not exist"));
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		return profile;
	}

}
