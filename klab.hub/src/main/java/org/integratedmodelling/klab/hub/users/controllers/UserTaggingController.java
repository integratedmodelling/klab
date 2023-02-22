package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.users.services.UserTagService;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoWriteException;

@RestController
public class UserTaggingController {

    private UserTagService userTagService;

    @Autowired
    UserTaggingController(UserTagService userTagService) {
        this.userTagService = userTagService;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity< ? > IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity< ? > MongoWriteExceptionHandler(MongoWriteException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Duplicated key. Element already exists.");
    }

    @GetMapping(value = API.HUB.TAG_BASE, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getAllTags(
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<MongoTag> tags;
        try {
            tags = type.isEmpty()
                    ? userTagService.getAllTags()
                    : userTagService.getAllTagsWithType(type.get());
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags);
    }

    @PostMapping(value = API.HUB.TAG_BASE, consumes = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createNewTag(
            @RequestBody MongoTag tag) {
        userTagService.insertTag(tag);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Tag sucessfully created.");
    }

    @PutMapping(value = API.HUB.TAG_BASE, consumes = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createOrUpdateTag(
            @RequestBody MongoTag tag) {
        userTagService.insertOrUpdateTag(tag);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Tag sucessfully created.");
    }

    @PutMapping(value = API.HUB.TAG_OF_USER, consumes = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createNewTag(
            @PathVariable String username,
            @RequestBody MongoTag tag) {
        final boolean isTagAdded;
        try {
            isTagAdded = userTagService.assignTagToUser(username, tag);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        final String body = isTagAdded ? "Tag sucessfully created." : "Tag already exists for the user.";
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(body);
    }

    @GetMapping(value = API.HUB.TAG_OF_USER, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagsOfUser(
            @PathVariable String username,
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<TagEntry> tags;
        try {
            tags = type.isEmpty()
                    ? userTagService.getTagsOfUser(username)
                    : userTagService.getTagsOfUserWithType(username, type.get());
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags);
    }

    @GetMapping(value = API.HUB.TAG_UNSENT_OF_USER, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getUnsentTagsOfUser(
            @PathVariable String username,
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<TagEntry> tags;
        try {
            tags = type.isEmpty()
                    ? userTagService.getUnsentTagsOfUser(username)
                    : userTagService.getUnsentTagsOfUserWithType(username, type.get());
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags);
    }

}
