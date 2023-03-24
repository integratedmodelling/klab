package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.hub.api.TagNotification;
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
    public ResponseEntity< ? > getAllTags() {
        List<MongoTag> tags;
        try {
            tags = userTagService.getAllTags();
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

    @PutMapping(value = API.HUB.TAGS_OF_USER, consumes = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createNewTag(
            @PathVariable("id") String username,
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

    @GetMapping(value = API.HUB.TAGS_OF_USER, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagsOfUser(
            @PathVariable("id") String username,
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<TagEntry> tags;
        try {
            tags = userTagService.getTagsOfUser(username);
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
            @PathVariable("username") String username,
            @RequestParam(required = false, value = API.HUB.PARAMETERS.TYPE_OF_TAG) Optional<HubNotificationMessage.Type> type) {
        List<TagEntry> tags;
        try {
            tags = userTagService.getUnsentTagsOfUser(username);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tags);
    }
    
    @GetMapping(value = API.HUB.TAG_ID)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagByName(
            @PathVariable("name") String name) {
        Optional<MongoTag> tag = userTagService.getTagByName(name);
        if(tag.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Tag is not present.");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tag);
    }

    @PostMapping(value = API.HUB.TAG_ID)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > bindANotificationToATag(
            @PathVariable("name") String name,
            @RequestBody TagNotification tagNotification) {
        Optional<MongoTag> tag = userTagService.getTagByName(name);
        if(tag.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Tag is not present.");
        }

        tagNotification.setTag(tag.get());
        userTagService.insertOrUpdateTagNotification(tagNotification);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(tag);
    }

    @GetMapping(value = API.HUB.TAG_NOTIFICATIONS)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getAllTagNotifications() {
        List<TagNotification> tagNotifications = userTagService.getAllTagNotifications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagNotifications);
    }

    @GetMapping(value = API.HUB.TAG_NOTIFICATION_OF_TAG)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagNotificationByTagName(
            @PathVariable("name") String name) {
        Optional<MongoTag> tag = userTagService.getTagByName(name);
        if(tag.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Tag is not present.");
        }

        Optional<TagNotification> tagNotification = userTagService.getTagNotificationsByTag(tag.get());
        if(tagNotification.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No notification for the requested tag.");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagNotification);
    }

    @GetMapping(value = API.HUB.TAG_NOTIFICATION_OF_USER)
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagNotificationByUserName(
            @PathVariable("id") String username) {
        List<TagNotification> tagNotifications = userTagService.getTagNotificationsByUser(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagNotifications);
    }

}
