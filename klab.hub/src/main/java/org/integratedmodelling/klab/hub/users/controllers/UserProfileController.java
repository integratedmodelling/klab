package org.integratedmodelling.klab.hub.users.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.payload.UpdateUserRequest;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserProfileController {

    private UserProfileService userService;

    @Autowired
    UserProfileController(UserProfileService userService) {
        this.userService = userService;
    }

    @GetMapping(API.HUB.USER_BASE)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > getAllUserProfiles() {
        JSONObject profiles = new JSONObject().appendField("profiles", userService.getAllUserProfiles());
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping(API.HUB.USER_BASE_ID)
    @PreAuthorize("authentication.getPrincipal() == #id or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity< ? > getUserProfile(@PathVariable String id) {
        ProfileResource profile = userService.getUserProfile(id);
        return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
    }

    @GetMapping(API.HUB.CURRENT_PROFILE)
    // TODO this is call from single user, not need PreAuthorize
    // correct the auth should be caught on the token filter side.
    public ResponseEntity< ? > getCurrentUserProfile() {
        ProfileResource profile = userService.getCurrentUserProfile();
        return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
    }

    @PutMapping(API.HUB.USER_BASE_ID)
    @PreAuthorize("authentication.getPrincipal() == #id")
    public ResponseEntity< ? > updateUserProfile(@PathVariable String id, @RequestBody UpdateUserRequest updateRequest) {
        ProfileResource profile = userService.updateUserByProfile(updateRequest.getProfile());
        return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = API.HUB.USER_BASE_ID, params = "remote-login")
    @PreAuthorize("authentication.getPrincipal() == #id")
    public ResponseEntity< ? > getFullUserProfile(@PathVariable String id) {
        ProfileResource profile = userService.getRawUserProfile(id);
        return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
    }

}
