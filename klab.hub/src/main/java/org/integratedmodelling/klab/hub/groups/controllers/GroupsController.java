package org.integratedmodelling.klab.hub.groups.controllers;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.groups.services.GroupService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/v2/groups")
public class GroupsController {
	
	@Autowired
	GroupService groupService;
	
	@GetMapping(value = "")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<?> getGroups() {
		return new ResponseEntity<>(groupService.getGroups(), HttpStatus.OK);
	}
	
	@GetMapping(value = "", params="names")
	@PreAuthorize("hasRole('ROLE_ENGINE') or hasRole('ROLE_USER') or hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR')")
	public ResponseEntity<Object> getGroupNames() {
		JSONObject resp = new JSONObject();
		resp.appendField("Groups", groupService.getGroupNames());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{groupName}")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> updateGroup(@PathVariable("groupName") String groupName, @RequestBody MongoGroup group) {
		if(groupName.equals(group.getGroupName())) {
			groupService.updateGroup(group);	
		} else {
			throw new BadRequestException("Group Id does not match url Id");
		}
		return new ResponseEntity<>("The group has been updated successsfully", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{groupName}")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> delete(@PathVariable("groupName") String groupName,  @RequestBody MongoGroup group) {
		if(groupName.equals(group.getGroupName())) {
			groupService.deleteGroup(group);	
		} else {
			throw new BadRequestException("Group name does not match name");
		}
		return new ResponseEntity<>("The Groups has been deleted successsfully", HttpStatus.OK);
	}
	
	@GetMapping(value= "/{groupName}")
	@PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINSTRATOR')")
	public ResponseEntity<Object> getGroup(@PathVariable("groupName") String groupName) {
		MongoGroup group = groupService.getGroupByName(groupName);
		return new ResponseEntity<>(group, HttpStatus.OK);		
	}
	
	@PostMapping(value="")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public ResponseEntity<Object> createGroup(@RequestBody MongoGroup group) {
		group = groupService.createGroup(group);
		return new ResponseEntity<>(group, HttpStatus.CREATED);
	}
}
