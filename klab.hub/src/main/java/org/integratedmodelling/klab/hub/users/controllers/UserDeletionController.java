package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.hub.users.services.UserDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/v2/users")
public class UserDeletionController {
	
	private UserDeletionService userService;
	
	@Autowired
	UserDeletionController(UserDeletionService userService) {
		this.userService = userService;
	}
	
	@DeleteMapping(value= "/{id}", produces = "application/json")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String username) {
		userService.deleteUser(username);
    	JSONObject resp = new JSONObject();
    	resp.appendField("User", username);
    	resp.appendField("Message", String.format("The %s has been deleted", username));
    	return ResponseEntity
  			  .status(HttpStatus.OK)
  			  .body(resp);
	}

}
