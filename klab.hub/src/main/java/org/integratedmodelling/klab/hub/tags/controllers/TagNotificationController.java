package org.integratedmodelling.klab.hub.tags.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.tags.enums.ITagElementEnum;
import org.integratedmodelling.klab.hub.tags.payload.TagRequest;
import org.integratedmodelling.klab.hub.tags.services.TagNotificationService;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagNotificationController {

    @Autowired
    private TagNotificationService tagNotificationService;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Get all tag notifications for this username, also included the generic tag notifications
     * @param username
     * @return
     */
    @GetMapping(value = API.HUB.TAG_NOTIFICATIONS, produces = "application/json", params = API.HUB.PARAMETERS.USER_TAGS)
    public ResponseEntity< ? > getUserTagNotifications(@RequestParam(API.HUB.PARAMETERS.USER_TAGS) String username) {
        try {
            User user = userProfileService.getUser(username);
            List<TagNotification> tagNotifications = tagNotificationService.getUserTagNotifications(user);
            return ResponseEntity.status(HttpStatus.OK).body(tagNotifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Create tag notification
     * @param username
     * @param TagRequest
     * @return
     */
    @PostMapping(value = API.HUB.TAG_NOTIFICATIONS, produces = "application/json")
    public ResponseEntity< ? > createUserTagNotification(@RequestBody TagRequest tagRequest) {
        try {

            TagNotification tagNotification = tagNotificationService.createTagNotification(
                    ITagElementEnum.valueOf(tagRequest.getiTagElement()), tagRequest.getiTagElementId(),
                    HubNotificationMessage.Type.valueOf(tagRequest.getType()), Boolean.valueOf(tagRequest.getVisible()),
                    tagRequest.getName(), tagRequest.getTitle(), tagRequest.getMessage(), tagRequest.getNavigateTo());

            return ResponseEntity.status(HttpStatus.OK).body(tagNotification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Delete tag notification
     * @param id
     * @return
     */
    @DeleteMapping(value = API.HUB.TAG_NOTIFICATION_ID)
    public ResponseEntity< ? > deletetagNotification(@PathVariable("id") String id) {

        try {
            TagNotification tagNotification = tagNotificationService.deleteTagNotification(id);
            return ResponseEntity.status(HttpStatus.OK).body(tagNotification);
        } catch (Exception e) {
//            resp.appendField("Message", String.format("Role %s is not valid", role));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
