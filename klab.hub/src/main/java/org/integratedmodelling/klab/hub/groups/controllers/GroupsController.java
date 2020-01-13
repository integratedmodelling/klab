package org.integratedmodelling.klab.hub.groups.controllers;

import org.integratedmodelling.klab.hub.groups.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/groups")
public class GroupsController {
	
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize(value = "")
	public ResponseEntity<?> getGroups() {
		return new ResponseEntity<>(groupService.getGroups(), HttpStatus.OK);
	}
	
	
	
}
