package org.integratedmodelling.klab.hub.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.models.GroupEntry;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	public Optional<User> getUserFromMongo(String usernameOrEmail) {
		Optional<User> user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail);
		return user;
	}


	public User createMongoUser(User user) throws KlabException {
		return createMongoUser(user, AccountStatus.pendingActivation);
	}

	public User createMongoUser(User user, AccountStatus accountStatus) throws KlabException {
		if (usernameExists(user.getUsername())) {
			throw new UserExistsException(user.getUsername());
		}
		if (emailExists(user.getEmail())) {
			throw new UserEmailExistsException(user.getEmail());
		}
		if(user.getAccountStatus().equals(null)) {
			user.setAccountStatus(accountStatus);	
		}
		user = userRepository.save(user);
		Logging.INSTANCE.info("Created Mongo User: " + user.toString());
		return user;
	}

	public boolean usernameExists(String username) {
		Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
		return user.isPresent();
	}

	public boolean emailExists(String email) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(email);
		return user.isPresent();
	}
	
	public void activateUser(String username) {
		User user = getUserFromMongo(username).get();
		if (AccountStatus.active.equals(user.getAccountStatus())) {
			// if already active, return silently (don't want a repeat-click to overwrite a
			// changed password)
		} else if (AccountStatus.pendingActivation.equals(user.getAccountStatus())) {
			// update the account status and create the LDAP user
			user.setAccountStatus(AccountStatus.active);
			userRepository.save(user);
		} else {
			throw new BadRequestException("Account was not in a valid state for activation.");
		}
	}
	
	public User addUserGroupEntries(String username, Set<GroupEntry> newGroupEntries) {
		User user = getUserFromMongo(username).get();
		
		Set<GroupEntry> groupEntries = user.getGroupEntries();
		
		Set<String> groupsList = getGroupEntryNames(groupEntries);
		
		Set<String> newGroupsList = getGroupEntryNames(newGroupEntries);
		
		for (String groupName : newGroupsList) {
			if(groupsList.contains(groupName)) {
				
				GroupEntry userGrpEntry = groupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.get();
				
				GroupEntry newGrpEntry = newGroupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.get();
				
				if(userGrpEntry.getExperation() !=null) {
					if (!userGrpEntry.getExperation().isAfter(newGrpEntry.getExperation())) {
						groupEntries.remove(userGrpEntry);
						groupEntries.add(newGrpEntry);
					}
				}
				
			} else {
				GroupEntry newGrpEntry = newGroupEntries.stream()
						.filter(e -> e.getGroupName().equals(groupName))
						.findFirst()
						.get();
				groupEntries.add(newGrpEntry);
			}
		}
		user.setGroupEntries(groupEntries);
		updateUser(user);
		return user;
	}

	public User setUserGroupEntries(String username, Set<GroupEntry> groupEntries) {
		User user = getUserFromMongo(username).get();
		user.setGroupEntries(groupEntries);
		updateUser(user);
		return user;
	}
	
	public User removeUserGroupEntries(String username, Set<String> groupnames) {
		User user = getUserFromMongo(username).get();
		Set<GroupEntry> groupEntries = user.getGroupEntries();
		Set<String> groupsList = getGroupEntryNames(groupEntries);
		for(String name : groupnames) {
			if(groupsList.contains(name)) {
				GroupEntry userGrpEntry = groupEntries.stream()
						.filter(e -> e.getGroupName().equals(name))
						.findFirst()
						.get();
				
				groupEntries.remove(userGrpEntry);
			}
		}
		user.setGroupEntries(groupEntries);
		updateUser(user);
		return user;
	}

	public User updateUser(User user) {
		getUserFromMongo(user.getUsername())
			.map(User::getId)
			.ifPresent(id -> user.setId(id));
		
		userRepository.save(user);
		return user;
	}

	
    public User deleteUser(String username) {
        User user = getUserFromMongo(username)
        				.filter(foundUser -> (
        						foundUser.getAccountStatus().equals(AccountStatus.active) |
        						foundUser.getAccountStatus().equals(AccountStatus.pendingActivation)))
        				.orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
        return user;
    }
    
    public List<User> getAllMongoUsers() {
    	return userRepository.findAll();
    }

	public boolean userExists(String username, String email) {
		boolean usernameExist =  usernameExists(username);
		boolean emailExist =  emailExists(email);
		if (usernameExist | emailExist) {
			return true;
		} else {
			return false;
		}
	}
	
	public Set<String> getGroupEntryNames(Set<GroupEntry> entries) {
		return entries.stream()
				.map(grpEntry -> grpEntry.getGroupName())
				.collect(Collectors.toSet());
	}

}
