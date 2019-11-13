package org.integratedmodelling.klab.hub.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;

import org.integratedmodelling.klab.hub.config.LoggingConfig;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.integratedmodelling.klab.hub.manager.TokenManager;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.payload.InviteRequest;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

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
		AuthenticationToken token = tokenManager.authenticate(request.getUsername(),request.getPassword());
		ProfileResource profile = klabUserManager.getLoggedInUserProfile();		
		JSONObject resp = new JSONObject();
		resp.appendField("Profile", profile.getSafeProfile());
		resp.appendField("Authentication", token);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authentication", token.getTokenString());
		return new ResponseEntity<JSONObject>(resp, headers, HttpStatus.OK);
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
			@RequestParam("addGroups") List<String> groups) {
		tokenManager.createNewUserWithGroups(request.getUsername(), request.getEmail(), tokenString, groups);
		return new ResponseEntity<String>("Please Check your email for account verification email.", HttpStatus.CREATED);
	}
	
	@PutMapping(value="/signup", produces = "application/json", params= {"token", "groups", "addGroups"})
	public ResponseEntity<?> signupGroupsAuthResponse(
			@RequestParam("token") String tokenString,
			@RequestParam("groups") String groupToken,
			@RequestParam("addGroups") List<String> groups) {
		tokenManager.updateOAuthUserWithGroups(tokenString, groupToken, groups);
		return new ResponseEntity<String>("Added groups to user", HttpStatus.CREATED);
	}
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@PostMapping(value="/invite", produces = "application/json")
	public ResponseEntity<?> inviteUserResponse(@RequestBody InviteRequest request) throws MessagingException {
		tokenManager.inviteNewUserWithGroups(request.getEmail(), request.getGroups());
		return new ResponseEntity<String>("Invite Sent.", HttpStatus.CREATED);
	}
	
}
