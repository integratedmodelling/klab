package org.integratedmodelling.klab.engine.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.engine.services.HubUserService;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLogin {
	
	@Autowired
	private HubUserService remoteUserService;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = API.HUB.AUTHENTICATE_USER, consumes="application/json",headers = "content-type=application/x-www-form-urlencoded;charset=UTF-8")
	public ResponseEntity<?> loginResponse(@CookieValue(value="Authentication", required=false) String token, @RequestBody UserAuthenticationRequest request) throws JSONException {
		if(token == null) {
			return remoteUserService.login(request);
		} else {
			return remoteUserService.login(token);
		}
		
	}

}
