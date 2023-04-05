package org.integratedmodelling.klab.hub.users.controllers;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.payload.UpdateUsersGroups;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserGroupEntryController {
	
	private UserGroupEntryService userService;
	
	@Autowired
	UserGroupEntryController(UserGroupEntryService userService) {
		this.userService = userService;
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = API.HUB.USER_BASE, produces = "application/json", params=API.HUB.PARAMETERS.REQUEST_GROUPS)
	public ResponseEntity<?> addUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		userService.addUsersGroupsByNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = API.HUB.USER_BASE, produces = "application/json", params=API.HUB.PARAMETERS.SET_GROUPS)
	public ResponseEntity<?> setUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		userService.setUsersGroupsByNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value = API.HUB.USER_BASE, produces = "application/json", params=API.HUB.PARAMETERS.REMOVE_GROUPS)
	public ResponseEntity<?> removeUsersGroups(@RequestBody UpdateUsersGroups updateUserGroups) {
		userService.removeUsersGroupsByNames(updateUserGroups);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Updated Succesful");
	}
}
