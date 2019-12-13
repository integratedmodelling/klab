package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.hub.exception.ActivationTokenFailedException;
import org.integratedmodelling.klab.hub.manager.EmailManager;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.hub.tokens.ActivateAccountClickbackToken;
import org.integratedmodelling.klab.hub.tokens.NewUserClickbackToken;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RequestMapping("/api/v2/users")
@RestController
public class UserRegistrationController {
	
	private UserRegistrationService userService;
	private RegistrationTokenService tokenService;
	private EmailManager emailManager;
	
	@Autowired
	UserRegistrationController(UserRegistrationService userService,
	RegistrationTokenService tokenService,
	EmailManager emailManager) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.emailManager = emailManager;
	}
	
	@PostMapping(value= "", produces = "application/json")
	public ResponseEntity<?> newUserRegistration(@RequestBody SignupRequest request) {
		User user = userService.registerNewUser(request.getUsername(), request.getEmail());
		ActivateAccountClickbackToken token = (ActivateAccountClickbackToken)
				tokenService.createToken(user.getUsername()
						, ActivateAccountClickbackToken.class);
		emailManager.sendNewUser(user.getEmail(), user.getEmail(), token.getCallbackUrl());
		return new ResponseEntity<String>("Please Check your email for account verification email.", HttpStatus.CREATED);
	}
	
	@PostMapping(value="/{username}", params = "activate")
	public ResponseEntity<?> newUserRegistration(@PathVariable String username, @RequestParam String activate) {
		if (!tokenService.verifyToken(username, activate)) {
			throw new ActivationTokenFailedException("User Activation token failed");
		}
		User user = userService.activateNewUser(username);
		NewUserClickbackToken token = 
				(NewUserClickbackToken) tokenService
					.createChildToken(username, activate, NewUserClickbackToken.class);
		
		JSONObject resp = new JSONObject();
		resp.appendField("User", user).appendField("clickback", token.getTokenString());
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}

}
