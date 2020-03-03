package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.ActivationTokenFailedException;
import org.integratedmodelling.klab.hub.payload.PasswordChangeRequest;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.hub.tokens.NewUserClickbackToken;
import org.integratedmodelling.klab.hub.tokens.TokenType;
import org.integratedmodelling.klab.hub.tokens.VerifyAccountClickbackToken;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
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
	private UserProfileService profileService;
	private RegistrationTokenService tokenService;
	private EmailManager emailManager;
	
	@Autowired
	UserRegistrationController(UserRegistrationService userService,
			UserProfileService profileService,
			RegistrationTokenService tokenService,
			EmailManager emailManager) {
		this.userService = userService;
		this.profileService = profileService;
		this.tokenService = tokenService;
		this.emailManager = emailManager;
	}
	
	@PostMapping(value= "", produces = "application/json")
	public ResponseEntity<?> newUserRegistration(@RequestBody SignupRequest request) {
		User user = userService.registerNewUser(request.getUsername(), request.getEmail());
		VerifyAccountClickbackToken token = (VerifyAccountClickbackToken)
				tokenService.createToken(user.getUsername()
						, TokenType.verify);
		emailManager.sendNewUser(user.getEmail(), user.getUsername(), token.getCallbackUrl());
		return new ResponseEntity<String>("Please Check your email for account verification email.", HttpStatus.CREATED);
	}
	
	@PostMapping(value="/{username}", params = "verify")
	public ResponseEntity<?> newUserVerification(@PathVariable String username, @RequestParam String verify) {
		if (!tokenService.verifyToken(username, verify, TokenType.verify)) {
			throw new ActivationTokenFailedException("User Verification token failed");
		}
		User user = userService.verifyNewUser(username);
		// user cannot be null, verifyNewUser throw exception if this
		ProfileResource profile = profileService.getUserSafeProfile(user);
		NewUserClickbackToken token = 
				(NewUserClickbackToken) tokenService
					.createChildToken(username, verify, TokenType.newUser);
		
		JSONObject resp = new JSONObject();
		resp.appendField("profile", profile).appendField("clickback", token.getTokenString());
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}
	
	@PostMapping(value="/{username}", params = "setPassword")
	public ResponseEntity<?> newUserPassword(@PathVariable String username, @RequestParam String setPassword,
			@RequestBody PasswordChangeRequest passwordRequest) {
		TokenType[] types = { TokenType.newUser, TokenType.password };
		if (!tokenService.verifyTokens(username, setPassword, types)) {
			throw new ActivationTokenFailedException("User Verification token failed");
		}
		User user = userService.setPassword(username, passwordRequest.getNewPassword(), passwordRequest.getConfirm());
		if(user != null) {
			tokenService.deleteToken(setPassword);
		}
		JSONObject resp = new JSONObject();
		resp.appendField("User", user);
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}

}
