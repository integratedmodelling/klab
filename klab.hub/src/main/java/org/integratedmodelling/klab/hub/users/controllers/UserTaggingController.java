package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.Tag;
import org.integratedmodelling.klab.hub.users.services.UserTagService;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTaggingController {

    private UserTagService userTagService;

    @Autowired
    UserTaggingController(UserTagService userTagService) {
        this.userTagService = userTagService;
    }

    @PutMapping(value = API.HUB.TAG_OF_USER, consumes = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createNewTag(
            @PathVariable String username,
            @RequestBody Tag tag) {
        userTagService.createNewTag(username, tag);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Tag sucessfully created.");
    }

    @GetMapping(value = API.HUB.TAG_OF_USER, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagsOfUser(
            @PathVariable String username,
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<Tag> tags = type.isEmpty()
                ? userTagService.getTagsOfUser(username)
                : userTagService.getTagsOfUserWithType(username, type.get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags);
    }
    
    @GetMapping(value = API.HUB.TAG_UNSENT_OF_USER, produces = "application/json", params = API.HUB.PARAMETERS.TYPE_OF_TAG)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getUnsentTagsOfUser(
            @PathVariable String username,
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<Tag> tags = type.isEmpty()
                ? userTagService.getUnsentTagsOfUser(username)
                : userTagService.getUnsentTagsOfUserWithType(username, type.get());
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags);
    }

}
