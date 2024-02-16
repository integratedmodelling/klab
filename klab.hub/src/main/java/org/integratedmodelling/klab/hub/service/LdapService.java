package org.integratedmodelling.klab.hub.service;

import java.util.List;
import java.util.Optional;

import javax.naming.Name;

import org.springframework.security.core.userdetails.UserDetails;
import net.minidev.json.JSONObject;

public interface LdapService {
	abstract List<JSONObject> findAllGroups();
	abstract List<String> findAllUsers();
	abstract Optional<UserDetails> getUserByCn(String cn);
	abstract void updateUser(UserDetails user);
	abstract void deleteUser(String username);
	abstract void createUser(UserDetails user);
	abstract Name buildDn(String username );
	abstract boolean userExists(String username, String email);
	void updateUserEmailAddress(String username, String newEmailAddress);
}