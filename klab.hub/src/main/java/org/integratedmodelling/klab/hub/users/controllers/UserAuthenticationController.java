package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
import org.integratedmodelling.klab.hub.users.payload.LoginResponse;
import org.integratedmodelling.klab.hub.users.payload.LogoutResponse;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthenticationController {
	
	private UserAuthTokenService userAuthService;
	
	@Autowired
	UserAuthenticationController(UserAuthTokenService userAuthService) {
		this.userAuthService = userAuthService;
	}
	
	@PostMapping(API.HUB.AUTHENTICATE_USER)
	public ResponseEntity<?> loginResponse(@RequestBody UserAuthenticationRequest request) {
		LoginResponse response = userAuthService.getAuthResponse(request.getUsername(), request.getPassword(), request.isRemote());
		return response.getResponse();
	}
	
	@PostMapping(API.HUB.DEAUTHENTICATE_USER)
	public ResponseEntity<?> logoutResponse(@RequestHeader("Authentication") String token) {
		LogoutResponse response = userAuthService.getLogoutResponse(token);
		return response.getResponse();
	}

}
