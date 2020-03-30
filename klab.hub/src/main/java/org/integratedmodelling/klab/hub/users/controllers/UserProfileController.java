package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.payload.UpdateUserRequest;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/v2/users")
public class UserProfileController {
	
	private UserProfileService userService;
	
	@Autowired
	UserProfileController(UserProfileService userService) {
		this.userService = userService;
	}
	
	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getAllUserProfiles() {
		JSONObject profiles = new JSONObject().appendField("profiles", userService.getAllUserProfiles());
		return new ResponseEntity<>(profiles,HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	@PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getUserProfile(@PathVariable("username") String username) {
		ProfileResource profile = userService.getUserProfile(username);
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/me")
	// TODO this is call from single user, not need PreAuthorize
	// @PreAuthorize("authentication.getPrincipal() == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> getCurrentUserProfile() {
		ProfileResource profile = userService.getCurrentUserProfile();
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}

	@PutMapping("/{username}")
	@PreAuthorize("authentication.getPrincipal() == #username" )
	public ResponseEntity<?> updateUserProfile(@PathVariable("username") String username, @RequestBody UpdateUserRequest updateRequest) {
		ProfileResource profile = userService.updateUserByProfile(updateRequest.getProfile());
		return new ResponseEntity<>(profile,HttpStatus.ACCEPTED);
	}
	
}
