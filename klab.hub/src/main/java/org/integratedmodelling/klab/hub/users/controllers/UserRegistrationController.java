package org.integratedmodelling.klab.hub.users.controllers;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.oauth.KeycloakTokenUtils;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.UserEmailExistsException;
import org.integratedmodelling.klab.hub.users.exceptions.UserExistsException;
import org.integratedmodelling.klab.hub.users.payload.SignupRequest;
import org.integratedmodelling.klab.hub.users.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserRegistrationController {
	
	private UserRegistrationService userService;
	
	@Autowired
    UserRegistrationController(UserRegistrationService userService) {
		this.userService = userService;
	}
	
	@PostMapping(value= API.HUB.USER_BASE, produces = "application/json")
	@Transactional
    public ResponseEntity< ? > newUserRegistration(@RequestBody SignupRequest request, HttpServletRequest httpRequest)
            throws UserExistsException, UserEmailExistsException {
	    
		User user = userService.registerNewUser(request.getUsername(), request.getEmail());
		
		String identity_provider = KeycloakTokenUtils.getClaim(httpRequest, "identity_provider");
		
		user = userService.createAndAddAgreement(user, request.getAgreementType(), request.getAgreementLevel(), identity_provider);
        return new ResponseEntity<JSONObject>(HttpStatus.CREATED);
	}
}
