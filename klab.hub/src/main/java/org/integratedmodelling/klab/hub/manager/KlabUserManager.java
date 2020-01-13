package org.integratedmodelling.klab.hub.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.integratedmodelling.klab.hub.service.UserService;
import org.integratedmodelling.klab.hub.users.DeletedUser;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.User.AccountStatus;
import org.integratedmodelling.klab.rest.Group;
import org.joda.time.DateTime;
import org.integratedmodelling.klab.hub.service.MongoGroupService;
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
    LinkConfig linkConfig;
    
	@Autowired
	MongoGroupService klabGroupService;
	
	@Override
	public User loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		//UserDetailsService has this method and we leverage that.  Our function will also search
		//the mongo database for any record that matches case ignored instances of the username
		//or the email.
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
			getLoggedInAuthentication().getDetails();
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

	public void updateLastLogin(User user) {
		user.setLastLogin();
		userService.updateUser(user);
	}

	public void updateLastEngineConnection(User user) {
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

	public void addUsersGroups(Set<String> usernames, Set<GroupEntry> groupEntries) {
		for (String username : usernames) {
			userService.addUserGroupEntries(username, groupEntries);
		}
	}

	public void removeUsersGroups(UpdateUsersGroups updateRequest) {
		for (String username : updateRequest.getUsernames()) {
			userService.removeUserGroupEntries(username, updateRequest.getGroupnames());
		}
	}

	public void setUsersGroups(Set<String> usernames, Set<GroupEntry> groupEntries) {
		for (String username : usernames) {
			userService.setUserGroupEntries(username, groupEntries);
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
            user.setServerUrl(linkConfig.getEngineUrl().toExternalForm());
        }
    }
    
    public ProfileResource getUserProfile(String username) {
    	User user = loadUserByUsername(username);
    	ProfileResource profielResource = objectMapper.convertValue(user, ProfileResource.class);
//    	List<KlabGroup> kGroups = new ArrayList<>();
//    	for (KlabGroup group : profielResource.getGroups()) {
//    		klabGroupService
//    			.getGroup(group.getId())
//    			.ifPresent(g -> kGroups.add(g));
//    	}
//    	profielResource.setGroups(kGroups);
    	return profielResource;
    }

	public boolean ldapUserExists(String username, String email) {
		return ldapService.userExists(username, email);
	}
	
	public boolean mongUserExists(String username, String email) {
		return userService.userExists(username, email);
	}

	public List<User> findAllMongoUsers() {
		return userService.getAllMongoUsers();
	}
	
	public List<Group> getUsersGroupsList(User user) {
		List<Group> listOfGroups = new ArrayList<>();
		for (GroupEntry groupEntry : user.getGroupEntries()) {
			if(groupEntry != null && !groupEntry.isExpired()) {
				Group group = new Group();
				klabGroupService
					.getGroup(groupEntry.getGroupName())
					.ifPresent(groupFromDB -> {
						group.setId(groupFromDB.getId());
						group.setProjectUrls(groupFromDB.getProjectUrls());
						group.setSshKey(groupFromDB.getSshKey());
						group.setObservables(groupFromDB.getObservableReferences());
						group.setWorldview(groupFromDB.getWorldview());
						group.setIconUrl(groupFromDB.getIconUrl());
						listOfGroups.add(group);
					});
			}
		}
		return listOfGroups;
	}

	public void setUsersGroupsFromNames(UpdateUsersGroups updateRequest) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String name : updateRequest.getGroupnames()) {
			klabGroupService
				.getGroup(name)
				.ifPresent(grp ->
					groupEntries.add(new GroupEntry(grp, updateRequest.getExperation()))
					);
		}
		setUsersGroups(updateRequest.getUsernames(), groupEntries);
	}
	
	public void setUserGroupsFromNames(String username, List<String> groupnames, DateTime expiration) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String name : groupnames) {
			klabGroupService
				.getGroup(name)
				.ifPresent(grp -> groupEntries.add(new GroupEntry(grp, expiration)));
		}
		userService.setUserGroupEntries(username, groupEntries);
	}

	public void addUsersGroupsFromNames(UpdateUsersGroups updateRequest) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String name : updateRequest.getGroupnames()) {
			klabGroupService
				.getGroup(name)
				.ifPresent(grp -> groupEntries.add(new GroupEntry(grp, updateRequest.getExperation())));
		}
		addUsersGroups(updateRequest.getUsernames(), groupEntries);
	}
	
	public void addUserGroupsFromNames(String username, Set<String> groupnames, DateTime expiration) {
		Set<GroupEntry> groupEntries = new HashSet<>();
		for (String name : groupnames) {
			klabGroupService
				.getGroup(name)
				.ifPresent(grp -> groupEntries.add(new GroupEntry(grp, expiration)));
		}
		userService.addUserGroupEntries(username, groupEntries);
	}
}
