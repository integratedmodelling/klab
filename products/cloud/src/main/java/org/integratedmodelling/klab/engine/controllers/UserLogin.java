package org.integratedmodelling.klab.engine.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.engine.services.HubUserService;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLogin {
	
	@Autowired
	private HubUserService remoteUserService;
	
	
	@PostMapping(value = API.HUB.AUTHENTICATE_USER, consumes="application/json",headers = "content-type=application/x-www-form-urlencoded;charset=UTF-8")
	public ResponseEntity<?> loginResponse(@RequestBody UserAuthenticationRequest request) {
		return remoteUserService.login(request);
	}
	
	@GetMapping(API.HUB.AUTHENTICATE_USER)
	public ResponseEntity<?> loginResponse() {
		return remoteUserService.login(new UserAuthenticationRequest());
	}
}
