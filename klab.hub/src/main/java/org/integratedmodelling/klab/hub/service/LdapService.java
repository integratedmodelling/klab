package org.integratedmodelling.klab.hub.service;

import java.util.List;
import java.util.Optional;

import javax.naming.Name;

import org.springframework.security.core.userdetails.UserDetails;
import net.minidev.json.JSONObject;

public interface LdapService {
	public abstract List<JSONObject> findAllGroups();
	public abstract List<String> findAllUsers();
	public abstract Optional<UserDetails> getUserByCn(String cn);
	public abstract void updateUser(UserDetails user);
	public abstract void deleteUser(String username);
	public abstract void createUser(UserDetails user);
	public abstract Name buildDn(String username );
}