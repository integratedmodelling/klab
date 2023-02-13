package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.users.services.UserRoleEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserRoleEntryController {

    private UserRoleEntryService userService;

    @Autowired
    UserRoleEntryController(UserRoleEntryService userService) {
        this.userService = userService;
    }

    @GetMapping(value = API.HUB.USER_BASE, produces = "application/json", params = API.HUB.PARAMETERS.HAS_ROLES)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > usersWithRole(@RequestParam(API.HUB.PARAMETERS.HAS_ROLES) String role) {
        JSONObject resp = new JSONObject();
        try {
            List<String> usersWithRole = userService.getUsersWithRole(Role.valueOf(role));
            return ResponseEntity.status(HttpStatus.OK).body(usersWithRole);
        } catch (IllegalArgumentException e) {
            resp.appendField("Message", String.format("Role %s is not valid", role));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
    }
}
