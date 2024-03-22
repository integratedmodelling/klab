package org.integratedmodelling.klab.hub.tags.controllers;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.tags.services.TagNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class TagNotificationController {

    @Autowired
    private TagNotificationService tagNotificationService;

    /**
     * Get all tag notifications for this username, also included the generic tag notifications
     * @param username
     * @return
     */
    @GetMapping(value = API.HUB.TAG_NOTIFICATIONS, produces = "application/json", params = API.HUB.PARAMETERS.USER_TAGS)
    public ResponseEntity< ? > getUserTagNotifications(@RequestParam(API.HUB.PARAMETERS.USER_TAGS) String username) {
        JSONObject resp = new JSONObject();
        try {
            List<TagNotification> tagNotifications = tagNotificationService.getUserTagNotifications(username);
            return ResponseEntity.status(HttpStatus.OK).body(tagNotifications);
        } catch (Exception e) {
//            resp.appendField("Message", String.format("Role %s is not valid", role));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Delete tag notification
     * @param id
     * @return
     */
    @DeleteMapping(value = API.HUB.TAG_NOTIFICATION_ID)
    public ResponseEntity< ? > deletetagNotification(@PathVariable("id") String id) {
        JSONObject resp = new JSONObject();
        try {
            TagNotification tagNotification = tagNotificationService.deleteTagNotification(id);
            return ResponseEntity.status(HttpStatus.OK).body(tagNotification);
        } catch (Exception e) {
//            resp.appendField("Message", String.format("Role %s is not valid", role));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
