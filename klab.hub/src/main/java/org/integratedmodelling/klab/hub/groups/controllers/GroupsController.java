package org.integratedmodelling.klab.hub.groups.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.services.GroupService;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class GroupsController {

    private GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = API.HUB.GROUPS_BASE)
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity< ? > getGroups() {
        JSONObject resp = new JSONObject();
        resp.appendField("groups", groupService.getAll());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value = API.HUB.GROUPS_BASE, params = API.HUB.PARAMETERS.GROUP_NAMES)
    @PreAuthorize("hasRole('ROLE_ENGINE') or hasRole('ROLE_USER') or hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getGroupNames() {
        JSONObject resp = new JSONObject();
        resp.appendField("groups", groupService.getGroupNames());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @GetMapping(value = API.HUB.GROUPS_BASE, params = API.HUB.PARAMETERS.GROUP_SUMMARY)
    @PreAuthorize("hasRole('ROLE_ENGINE') or hasRole('ROLE_USER') or hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getGroupsSummary() {
        JSONObject resp = new JSONObject();
        resp.appendField("groups", groupService.getGroupsSummary());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping(value = API.HUB.GROUPS_BASE_ID)
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> updateGroup(@PathVariable("id") String id, @RequestBody MongoGroup group) {
        if (id.equals(group.getName())) {
            groupService.update(group);
        } else {
            throw new BadRequestException("Group Id does not match url Id");
        }
        return new ResponseEntity<>("The group has been updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = API.HUB.GROUPS_BASE_ID)
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        groupService.delete(id);
        return new ResponseEntity<>("The Groups has been deleted successsfully", HttpStatus.OK);
    }

    @GetMapping(value = API.HUB.GROUPS_BASE_ID)
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getGroup(@PathVariable("id") String id) {
        JSONObject resp = new JSONObject();
        resp.appendField("group", groupService.getByName(id));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = API.HUB.GROUPS_BASE)
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> createGroup(@RequestBody MongoGroup group) {
        group = groupService.create(group);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

}
