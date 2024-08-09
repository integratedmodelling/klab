package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.UserEmailExistsException;
import org.integratedmodelling.klab.hub.users.exceptions.UserExistsException;
import org.integratedmodelling.klab.hub.users.payload.SignupRequest;
import org.integratedmodelling.klab.hub.users.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = API.HUB.USER_BASE, produces = "application/json")
    public ResponseEntity< ? > newUserRegistration(@RequestBody SignupRequest request)
            throws UserExistsException, UserEmailExistsException {
        User user = userService.registerNewUser(request.getUsername(), request.getEmail());
        user = userService.createAndAddAgreement(user, request.getAgreementType(), request.getAgreementLevel());
        return new ResponseEntity<JSONObject>(HttpStatus.CREATED);
    }
}
