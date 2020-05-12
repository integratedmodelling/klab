package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.hub.api.TokenNewUserClickback;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.TokenChangePasswordClickback;
import org.integratedmodelling.klab.hub.api.TokenType;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.TokenVerifyAccountClickback;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.ActivationTokenFailedException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.payload.PasswordChangeRequest;
import org.integratedmodelling.klab.hub.payload.SignupRequest;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.hub.users.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

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
	
	@PostMapping(value= API.HUB.USER_BASE, produces = "application/json")
	public ResponseEntity<?> newUserRegistration(@RequestBody SignupRequest request) throws UserExistsException, UserEmailExistsException {
		User user = userService.registerNewUser(request.getUsername(), request.getEmail());
		TokenVerifyAccountClickback token = (TokenVerifyAccountClickback)
				tokenService.createToken(user.getUsername()
						, TokenType.verify);
		emailManager.sendNewUser(user.getEmail(), user.getUsername(), token.getCallbackUrl());
		JSONObject resp = new JSONObject();
		resp.appendField("message", "Please Check your email for account verification email.");
		return new ResponseEntity<JSONObject>(resp, HttpStatus.CREATED);
	}
	
	@PostMapping(value=API.HUB.USER_BASE_ID, params = API.HUB.PARAMETERS.USER_VERIFICATION)
	public ResponseEntity<?> newUserVerification(@PathVariable String id, @RequestParam String verify) {
		if (!tokenService.verifyToken(id, verify, TokenType.verify)) {
			throw new ActivationTokenFailedException("User Verification token failed");
		}
		User user = userService.verifyNewUser(id);
		// user cannot be null, verifyNewUser throw exception if this
		ProfileResource profile = profileService.getUserSafeProfile(user);
		TokenNewUserClickback token = 
				(TokenNewUserClickback) tokenService
					.createChildToken(id, verify, TokenType.newUser);
		
		JSONObject resp = new JSONObject();
		resp.appendField("profile", profile).appendField("clickback", token.getTokenString());
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}
	
	@PostMapping(value=API.HUB.USER_BASE_ID, params = API.HUB.PARAMETERS.USER_SET_PASSWORD)
	public ResponseEntity<?> newUserPassword(@PathVariable String id, @RequestParam(API.HUB.PARAMETERS.USER_SET_PASSWORD) String setPassword,
			@RequestBody PasswordChangeRequest passwordRequest) {
		TokenType[] types = { TokenType.newUser, TokenType.password, TokenType.lostPassword };
		if (!tokenService.verifyTokens(id, setPassword, types)) {
			throw new ActivationTokenFailedException("User Verification token failed");
		}
		User user = userService.setPassword(id, passwordRequest.getNewPassword(), passwordRequest.getConfirm());
		if(user != null) {
			tokenService.deleteToken(setPassword);
		}
		JSONObject resp = new JSONObject();
		resp.appendField("Message", "User password updated");
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}
	
	@PostMapping(value=API.HUB.USER_BASE_ID, params = API.HUB.PARAMETERS.USER_REQUEST_PASSWORD)
	public ResponseEntity<?> authorizedPasswordChange(@PathVariable String username) {
		TokenChangePasswordClickback token = (TokenChangePasswordClickback)
				tokenService.createToken(username, TokenType.password);
		JSONObject resp = new JSONObject();
		resp.appendField("User", username).appendField("clickback", token.getTokenString());
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}
	
	
	@PostMapping(value=API.HUB.USER_BASE_ID, params = API.HUB.PARAMETERS.USER_LOST_PASSWORD)
	public ResponseEntity<?> requestLostPassword(@PathVariable String username) throws MessagingException {
		ProfileResource profile = profileService.getUserProfile(username);
		TokenChangePasswordClickback token = (TokenChangePasswordClickback)
				tokenService.createToken(username, TokenType.lostPassword);
		
		emailManager.sendLostPasswordEmail(profile.getEmail(), token.getCallbackUrl());
		
		JSONObject resp = new JSONObject();
		resp.appendField("message", "Reset password link sent to email address assoicated with user: " + username);
		return new ResponseEntity<JSONObject>(resp,HttpStatus.CREATED);
	}

}
