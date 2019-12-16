package org.integratedmodelling.klab.hub.users.controllers;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/users")
public class UserGroupEntryController {
	
	private UserGroupEntryService userService;
	
	@Autowired
	UserGroupEntryController(UserGroupEntryService userService) {
		this.userService = userService;
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = "", produces = "application/json", params="addUsersGroups")
	public ResponseEntity<?> addUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		userService.addUsersGroupsFromNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = "", produces = "application/json", params="setUsersGroups")
	public ResponseEntity<?> setUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		userService.setUsersGroupsFromNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
}
