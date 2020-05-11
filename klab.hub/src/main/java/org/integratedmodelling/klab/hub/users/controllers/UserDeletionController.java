package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.DeletedUser;
import org.integratedmodelling.klab.hub.users.services.UserDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserDeletionController {
	
	private UserDeletionService userService;
	
	@Autowired
	UserDeletionController(UserDeletionService userService) {
		this.userService = userService;
	}
	
	@DeleteMapping(value= API.HUB.USER_BASE_ID, produces = "application/json")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String username) {
		userService.deleteUser(username);
    	JSONObject resp = new JSONObject();
    	resp.appendField("User", username);
    	resp.appendField("Message", String.format("The %s has been deleted", username));
    	return ResponseEntity
  			  .status(HttpStatus.CREATED)
  			  .body(resp);
	}
	
	@GetMapping(value= API.HUB.DELETED_USERS, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getDeletedUsers(){
		List<DeletedUser> users = userService.getDeletedUsers();
		JSONObject resp = new JSONObject();
		resp.appendField("Deleted Users", users);
    	return ResponseEntity
    			  .status(HttpStatus.OK)
    			  .body(resp);
	}
	
	@GetMapping(value= API.HUB.DELETED_USER_ID, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getDeletedUsers(@PathVariable("id") String username){
		DeletedUser users = userService.getDeletedUser(username);
		JSONObject resp = new JSONObject();
		resp.appendField("Deleted Users", users);
    	return ResponseEntity
    			  .status(HttpStatus.OK)
    			  .body(resp);
	}
	
	@DeleteMapping(value= API.HUB.DELETED_USER_ID, produces = "application/json", params = "delete-ldap")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> deleteLdap(@PathVariable("id") String username){
		userService.deleteUserLdap(username);
		JSONObject resp = new JSONObject();
		resp.appendField("User", username);
		resp.appendField("message", "Deleted the ldap for deleted user");
    	return ResponseEntity
    			  .status(HttpStatus.CREATED)
    			  .body(resp);
	}

}
