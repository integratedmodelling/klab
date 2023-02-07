package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.users.services.UserLockingService;
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
public class UserLockingController {

	private UserLockingService userService;
	
	@Autowired
	UserLockingController(UserLockingService userService) {
		this.userService = userService;
	}
	
	@PostMapping(value= API.HUB.LOCK_USER, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> lockedUser(@PathVariable("id") String username) {
		userService.lockUser(username);
    	JSONObject resp = new JSONObject();
    	resp.appendField("User", username);
    	resp.appendField("Message", String.format("%s is locked", username));
    	return ResponseEntity
  			  .status(HttpStatus.ACCEPTED)
  			  .body(resp);
	}

	@GetMapping(value= API.HUB.LOCKED_USERS, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getLockedUsers() {
		List<User> lockedUsers = userService.getLockedUsers();
		JSONObject resp = new JSONObject();
		resp.appendField("Locked Users", lockedUsers);
    	return ResponseEntity
    			  .status(HttpStatus.OK)
    			  .body(resp);
	}
	
}
