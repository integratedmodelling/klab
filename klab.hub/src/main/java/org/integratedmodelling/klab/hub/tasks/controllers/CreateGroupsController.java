package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.integratedmodelling.klab.hub.users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/v2/tasks")
@RestController
public class CreateGroupsController {
	
	@Autowired
	TaskService service;
	
	@PostMapping(value="", produces = "application/json", params="create-group")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> createGroupsResponse(
			@RequestBody MongoGroup group,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		List<Task> tasks = service.createTasks(
				CreateGroupTask.class,
				new CreateGroupTask.Parameters(Role.ROLE_ADMINISTRATOR, request, group));
	    
	    return new ResponseEntity<>(tasks.get(0), HttpStatus.CREATED);
	}
}
