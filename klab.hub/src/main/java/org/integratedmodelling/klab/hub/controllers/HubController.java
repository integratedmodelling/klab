package org.integratedmodelling.klab.hub.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	@GetMapping(value="/logs", params = {"lines"})
	public ResponseEntity<?> getHubLogResponse(@RequestParam("lines") int lines) throws IOException {
		Path path = Paths.get(loggingConfig.getLOGGING_FILE());
		File file = path.toFile();
		FileInputStream input = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		CircularFifoBuffer fifoLog = new CircularFifoBuffer();
		if (lines == -1) {
			int lineCount = (int) Files.lines(path).count();
			fifoLog = new CircularFifoBuffer(lineCount);
		} else {
			fifoLog = new CircularFifoBuffer(lines);
		}
		
		for(String tmp; (tmp = reader.readLine()) != null;)
			fifoLog.add(tmp);
		reader.close();
		JSONObject resp = new JSONObject();
		resp.appendField("Log", fifoLog.toArray());
		return new ResponseEntity<JSONObject>(resp, HttpStatus.OK);
	}
	
}
