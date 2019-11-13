package org.integratedmodelling.klab.hub.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.naming.NameNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;
import org.integratedmodelling.klab.hub.models.DeletedUser;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.integratedmodelling.klab.hub.repository.KlabGroupRepository;
import org.integratedmodelling.klab.hub.service.UserService;
import org.integratedmodelling.klab.hub.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KlabUserManager implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	LdapService ldapService;
	
	@Autowired
	DeletedUserRepository deletedUserRepository;
	
    @Autowired
    protected ObjectMapper objectMapper;
    
    @Autowired
    TokenClickbackConfig tokenClickbackConfig;
    
	@Autowired
	KlabGroupRepository groupRepository;
	
	@Override
	public User loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userService.getUserFromMongo(usernameOrEmail)
				.orElse(null);
		if(user == null) {
			return user;
		}
		Set<Role> roles = new HashSet<>();
		if(ldapService.userExists(user.getUsername(), user.getEmail())) {
			ldapService.getUserByCn(usernameOrEmail)
				.map(UserDetails::getAuthorities)
				.ifPresent(authorities -> 
					authorities.forEach(role -> roles.add(Role.valueOf(role.getAuthority()))));
	
			ldapService.getUserByCn(usernameOrEmail)
				.map(UserDetails::getPassword)
				.ifPresent(password -> user.setPasswordHash(password));
		}
	
		if(!roles.isEmpty()) {
			user.setRoles(roles);	
		}
		
		return user;
	}
	
	public Authentication getLoggedInAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public String getLoggedInUsername() {
		String result = "-unauthenticated user-";
		try {
			Object principal = getLoggedInAuthentication().getPrincipal();
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
		User user = loadUserByUsername(username);
		return user;
	}

	public ProfileResource getLoggedInUserProfile() {
		User user = getLoggedInUser();
		ProfileResource profile = getUserProfile(user.getUsername());
		return profile;
	}
	
	
	public ProfileResource updateUserProfile(ProfileResource profileResource) {
		User loggedInUser = getLoggedInUser();
		if(loggedInUser.getUsername().equals(profileResource.getUsername())) {
			profileResource.accountStatus = loggedInUser.getAccountStatus();
			}
		loggedInUser.updateFromProfileResource(profileResource);
		updateKlabUser(loggedInUser);
		ProfileResource profile = getLoggedInUserProfile();
		return profile;
	}
	
	public ProfileResource updateUserRoles(String userId, Collection<Role> roles) {
		User user = getUser(userId);
		user.setRoles(roles);
		updateKlabUser(user);
		ProfileResource profile = getUserProfile(user.getUsername());
		return profile;
	}

	public void updateLastLogin(String username) {
		User user = loadUserByUsername(username);
		user.setLastLogin();
		userService.updateUser(user);
	}

	public void updateLastEngineConnection(String username) {
		User user = loadUserByUsername(username);
		user.setLastEngineConnection();
		userService.updateUser(user);
	}
	
	public void deleteUser(String username) {
		User user = userService.deleteUser(username);
        DeletedUser deletedUser = new DeletedUser();
        deletedUser.setFromUser(user);
        deletedUserRepository.save(deletedUser);
		ldapService.deleteUser(username);
	}
	
	public UserDetails convertUsertoLdapUser(User user) {
		InetOrgPerson.Essence person = new InetOrgPerson.Essence();
		person.setCn(new String[] { user.getUsername() });
		person.setDn(ldapService.buildDn(user.getUsername()).toString());
		person.setUid(user.getUsername());
		person.setUsername(user.getUsername());
		person.setPassword(user.getPasswordHash());
		person.setMail(user.getEmail());
		person.setDisplayName(user.getDisplayName());
		if (StringUtils.isEmpty(user.getLastName())) {
			person.setSn("<unknown>");
		} else {
			person.setSn(user.getLastName());
		}
		person.setAuthorities(user.getAuthorities());
		LdapUserDetails userDetails = person.createUserDetails();
		return userDetails;
	}
	
	public User updateKlabUser(User user) {
		userService.updateUser(user);
		if (user.getProviderId() == null) {
			ldapService.updateUser(convertUsertoLdapUser(user));
			Logging.INSTANCE.info("Updated Ldap User:" + user.toString());
		}
		return user;
	}
	
	
	public User createPendingKlabUser(User user) {
		user = userService.createMongoUser(user);
		return user;
	}
	
	public User createKlabUser(User user, AccountStatus accountStatus) {
		user = userService.createMongoUser(user, accountStatus);
		ldapService.createUser(convertUsertoLdapUser(user));
		return user;
	}

	public void addUsersGroups(Set<String> usernames, Set<String> groupnames) {
		for (String username : usernames) {
			userService.addUserGroups(username, groupnames);
		}
	}

	public void removeUsersGroups(Set<String> usernames, Set<String> groupnames) {
		for (String username : usernames) {
			userService.removeUserGroups(username, groupnames);
		}
	}

	public void setUsersGroups(Set<String> usernames, Set<String> groupnames) {
		for (String username : usernames) {
			userService.setUserGroups(username, groupnames);
		}
	}

	public void activateUser(String loggedInUsername) {
		userService.activateUser(loggedInUsername);
	}
	
	public void createLdapUser(User user) {
		ldapService.createUser(convertUsertoLdapUser(user));
	}

	public User createOAuthKlabUser(User user) {
		userService.createMongoUser(user, AccountStatus.active);
		return user;
	}
	
	public Map<String, ProfileResource> getAllProfiles() {
		List<User> users = userService.getAllMongoUsers();
        
		Map<String, ProfileResource> result = new HashMap<>();
        for (User user : users) {
            decorate(user);
            result.put(user.getUsername(), objectMapper.convertValue(user, ProfileResource.class));
        }
        return result;
	}
	
    public void decorate(User user) {
        if (user.getServerUrl() == null) {
            user.setServerUrl(tokenClickbackConfig.getEngineUrl().toExternalForm());
        }
    }
    
    public ProfileResource getUserProfile(String username) {
    	User user = loadUserByUsername(username);
    	ProfileResource profielResource = objectMapper.convertValue(user, ProfileResource.class);
    	List<KlabGroup> kGroups = new ArrayList<>();
    	for (KlabGroup group : profielResource.getGroups()) {
    		group = groupRepository.findById(group.getId());
    		kGroups.add(group);
    	}
    	profielResource.setGroups(kGroups);
    	return profielResource;
    }

	public boolean ldapUserExists(String username, String email) {
		return ldapService.userExists(username, email);
	}

}
