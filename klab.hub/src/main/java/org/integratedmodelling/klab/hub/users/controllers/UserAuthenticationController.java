package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2/users")
@RestController
public class UserAuthenticationController {
	
	private UserAuthTokenService userAuthService;
	
	@Autowired
	UserAuthenticationController(UserAuthTokenService userAuthService) {
		this.userAuthService = userAuthService;
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<?> loginResponse(@RequestBody UserAuthenticationRequest request) {
		LoginResponse response = userAuthService.getAuthResponse(request.getUsername(), request.getPassword());
		return response.getResponse();
	}
	
	@PostMapping("/log-out")
	public ResponseEntity<?> logoutResponse(@RequestHeader("Authentication") String token) {
		LogoutResponse response = userAuthService.getLogoutResponse(token);
		return response.getResponse();
	}

}
