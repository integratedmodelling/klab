package org.integratedmodelling.klab.hub.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
@RequestMapping(value = "/api/ldap")
public class LdapController {

	@Autowired
	private LdapService ldapService;

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public JSONObject getAllUserNames() {
		List<String> allUsers = ldapService.findAllUsers();
		JSONObject allUsersJson = new JSONObject();
		allUsersJson.put("Users", allUsers);
		return allUsersJson;
	};

	@RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json")
	public JSONObject getAllGroups() {
		List<JSONObject> groups = ldapService.findAllGroups();
		JSONObject groupsJson = new JSONObject();
		groupsJson.put("Groups", groups);
		return groupsJson;
	};

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
	public Optional<UserDetails> getUserById(@PathVariable("id") String id) {
		return ldapService.getUserByCn(id);
	}
}
