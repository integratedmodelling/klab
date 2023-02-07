package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.users.services.UserSuspensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserSuspensionController {

	private UserSuspensionService userService;
	
	@Autowired
	UserSuspensionController(UserSuspensionService userService) {
		this.userService = userService;
	}
	
	@PostMapping(value= API.HUB.SUSPEND_USER, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> suspendUser(@PathVariable("id") String username) {
		userService.suspendUser(username);
    	JSONObject resp = new JSONObject();
    	resp.appendField("User", username);
    	resp.appendField("Message", String.format("%s is suspended", username));
    	return ResponseEntity
  			  .status(HttpStatus.ACCEPTED)
  			  .body(resp);
	}

	@GetMapping(value= API.HUB.SUSPENDED_USERS, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getSuspendedUsers() {
		List<User> suspendedUsers = userService.getSuspendedUsers();
		JSONObject resp = new JSONObject();
		resp.appendField("Suspended Users", suspendedUsers);
    	return ResponseEntity
    			  .status(HttpStatus.OK)
    			  .body(resp);
	}
	
}
