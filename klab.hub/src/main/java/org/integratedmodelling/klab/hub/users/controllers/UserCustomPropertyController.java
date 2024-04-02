package org.integratedmodelling.klab.hub.users.controllers;

import java.util.Collection;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.users.exceptions.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.users.requests.UserCustomPropertyRequest;
import org.integratedmodelling.klab.hub.users.services.UserCustomPropertyService;
import org.integratedmodelling.klab.rest.CustomProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserCustomPropertyController {

    private UserCustomPropertyService userCustomPropertyService;

    @Autowired
    public UserCustomPropertyController(UserCustomPropertyService userCustomPropertyService) {
        super();
        this.userCustomPropertyService = userCustomPropertyService;
    }

    @GetMapping(value = API.HUB.USER_ID_CUSTOM_PROPERTIES)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > getUserCustomProperties ( @PathVariable("id") String username) {
        JSONObject resp = new JSONObject();
        try {
            Collection<CustomProperty> customProperties = userCustomPropertyService.getAllUserCustomProperties(username);
            resp.appendField("Custom Properties", customProperties);
        } catch (UserDoesNotExistException e) {
            resp.appendField("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
    }

    @PostMapping(value = API.HUB.USER_CUSTOM_PROPERTIES, produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > setUserCustomProperties (@RequestBody UserCustomPropertyRequest request) {
        JSONObject resp = new JSONObject();
        try {
            userCustomPropertyService.addUserCustomProperties(request);
        } catch (UserDoesNotExistException | BadRequestException e) {
            resp.appendField("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        resp.appendField("Message", "Custom properties set.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
    }

    @DeleteMapping(value = API.HUB.USER_CUSTOM_PROPERTIES, produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > removeUserCustomProperties (@RequestBody UserCustomPropertyRequest request) {
        JSONObject resp = new JSONObject();
        try {
            userCustomPropertyService.deleteUserCustomCustomProperties(request);
        } catch (UserDoesNotExistException | BadRequestException e) {
            resp.appendField("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        resp.appendField("Message", "Custom properties removed.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
    }
}
