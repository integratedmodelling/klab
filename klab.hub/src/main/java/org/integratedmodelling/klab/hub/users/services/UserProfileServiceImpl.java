package org.integratedmodelling.klab.hub.users.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.TokenType;
import org.integratedmodelling.klab.hub.api.TokenVerifyEmailClickback;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.UserByEmailDoesNotExistException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.service.implementation.LdapServiceImpl;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	private UserRepository userRepository;
	private LdapServiceImpl ldapServiceImpl;
	private LdapUserDetailsManager ldapUserDetailsManager;
	
	private ObjectMapper objectMapper;
	
	private EmailManager emailManager;	
	
	private RegistrationTokenService tokenService;
	
	
	public UserProfileServiceImpl(UserRepository userRepository, ObjectMapper objectMapper, EmailManager emailManager, RegistrationTokenService tokenService, LdapServiceImpl ldapServiceImpl,
			LdapUserDetailsManager ldapUserDetailsManager) {
		super();
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
		this.emailManager = emailManager;
		this.tokenService = tokenService;
		this.ldapServiceImpl = ldapServiceImpl;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
	}

	@Override
	public ProfileResource updateUserByProfile(ProfileResource profile) {
		User user = updateUserFromProfileResource(profile);
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
	public ProfileResource getCurrentUserProfile(boolean remote) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByNameIgnoreCase(username)
				.orElseThrow(() ->  
					new UserDoesNotExistException());
		ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
		if (remote) {
		    return profile;
		}
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
	
	/**
	 * Create TokenVerifyEmailClickback if email is changed. 
	 * If email changes send and email to the user to verified this action
	 * @param user
	 * @param email
	 * @throws MessagingException 
	 */
	@Override
	public ProfileResource createNewEmailRequest(String username, String email) throws MessagingException {
		ProfileResource profile = getUserProfile(username);
		if(profile.getEmail() != email) {
			TokenVerifyEmailClickback token = (TokenVerifyEmailClickback)
					tokenService.createToken(username, email, TokenType.verifyEmail);
			
			emailManager.sendVerifyEmailClickback(email, token.getCallbackUrl());			
		}
		return profile;

	}
	
	@Override
	public ProfileResource updateUserEmail(String username, String email) {
		User user = userRepository.findByNameIgnoreCase(username)				
				.orElseThrow(() ->  
					new KlabException("Current User doesn't exist"));
		user.setEmail(email);
		/* update mongo repository */
		User updatedUser = new UpdateUser(user, userRepository).execute();
		/* update ldap */
		//UserDetails ldapUser = ldapUserDetailsManager.loadUserByUsername(username);
		//ldapUser.s
		ldapServiceImpl.updateUserEmailAddress(username, email);
		//UserDetails ldapUser = ldapUserDetailsManager.loadUserByUsername(username);
		ProfileResource updatedProfile = objectMapper.convertValue(updatedUser, ProfileResource.class);
		return updatedProfile.getSafeProfile();
	}
	
	private User updateUserFromProfileResource(ProfileResource profile) {
		User user = userRepository.findByNameIgnoreCase(profile.getUsername())
				.filter(u -> u.getUsername().equals(profile.getUsername()))
				.orElseThrow(() ->  
					new KlabException("Current User context does match updated profile username"));
		user.updateFromProfileResource(profile);
		return user;
	}

    @Override
    public Page<User> getPage(Query query, Pageable pageable) {
        return userRepository.findAll(query, pageable);
    }
    
    @Override
    public List<User> getQuery(Query query) {
        return userRepository.findAll(query);
    }

}
