package org.integratedmodelling.klab.hub.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	
	public abstract User createMongoUser(User user);
	public abstract User createMongoUser(User user, AccountStatus accountStatus);
	public abstract User updateUser(User user);
	public abstract void activateUser(String username);
	public abstract Optional<User> getUserFromMongo(String username);
	public abstract User deleteUser(String username);
    public abstract User addUserGroups(String username, Set<String> groupnames);
    public abstract User setUserGroups(String username, Set<String> groupnames);
    public abstract User removeUserGroups(String username, Set<String> groupnames);
    public abstract List<User> getAllMongoUsers();
	public abstract boolean userExists(String username, String email);
}
