package org.integratedmodelling.klab.hub.controllers;

import java.util.Set;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.hub.config.LoggingConfig;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.integratedmodelling.klab.hub.manager.TokenManager;
import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {
	
	@Autowired
	TokenManager tokenManager;
	
	@Autowired
	KlabUserManager klabUserManager;
	
	@Autowired
	LoggingConfig loggingConfig;
	
	@PostMapping("/signin")
	public ResponseEntity<?> loginResponse(@RequestBody UserAuthenticationRequest request) {
		LoginResponse response = tokenManager.authenticate(request.getUsername(), request.getPassword());
		return response.getResponse();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupResponse(@RequestBody SignupRequest request) throws TokenGenerationException, MessagingException {
			tokenManager.createNewUser(request.getUsername(), request.getEmail());
			return new ResponseEntity<String>("Please Check your email for account verification email.", HttpStatus.CREATED);
	}
	
	@PostMapping(value="/signup", produces = "application/json", params= {"groups","addGroups"})
	public ResponseEntity<?> signupGroupsResponse(
			@RequestBody SignupRequest request,
			@RequestParam("groups") String tokenString,
			@RequestParam("addGroups") Set<String> groups) {
		tokenManager.createNewUserWithGroups(request.getUsername(), request.getEmail(), tokenString, groups);
		return new ResponseEntity<String>("Please Check your email for account verification email.", HttpStatus.CREATED);
	}
	
	@PutMapping(value="/signup", produces = "application/json", params= {"token", "groups", "addGroups"})
	public ResponseEntity<?> signupGroupsAuthResponse(
			@RequestParam("token") String tokenString,
			@RequestParam("groups") String groupToken,
			@RequestParam("addGroups") Set<String> groups) {
		tokenManager.updateOAuthUserWithGroups(tokenString, groupToken, groups);
		return new ResponseEntity<String>("Added groups to user", HttpStatus.CREATED);
	}
	
}
