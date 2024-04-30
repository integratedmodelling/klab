package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;
import org.integratedmodelling.klab.hub.users.services.UserAccountStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserAccountStatusController {

    private UserAccountStatusService userService;

    @Autowired
    UserAccountStatusController(UserAccountStatusService userService) {
        this.userService = userService;
    }

    @GetMapping(value = API.HUB.USER_BASE, produces = "application/json", params = API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > getUsersByAccountStatus(
            @RequestParam(API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS) String accountStatus) {
        JSONObject resp = new JSONObject();
        try {
            List<String> lockedUsers = userService.getUsersByStatus(AccountStatus.valueOf(accountStatus));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lockedUsers);
        } catch (IllegalArgumentException e) {
            resp.appendField("Message", String.format("Account status %s is not valid", accountStatus));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
    }
}
