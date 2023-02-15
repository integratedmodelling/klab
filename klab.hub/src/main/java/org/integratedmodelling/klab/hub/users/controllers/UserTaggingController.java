package org.integratedmodelling.klab.hub.users.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.Tag;
import org.integratedmodelling.klab.hub.users.services.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class UserTaggingController {

    private UserTagService userTagService;

    @Autowired
    UserTaggingController(UserTagService userTagService) {
        this.userTagService = userTagService;
    }

    @PostMapping(value = API.HUB.TAG_SET_USER, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > createNewTag(@PathVariable String username, @RequestBody String body) {
        userTagService.createNewTag(username, body);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = API.HUB.TAG_SET_USER, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTagsOfUser(@PathVariable String username) {
        List<Tag> tags = userTagService.getTagsOfUser(username);
        
        JSONObject resp = new JSONObject();
        resp.appendField("Tags", tags);
        return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(resp);
    }
}
