package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.integratedmodelling.klab.hub.users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/v2/tasks")
@RestController
public class CreateGroupsController {
	
	@Autowired
	TaskService service;
	
	@PostMapping(value= "/{id}", produces = "application/json", params="create-group")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> createGroupsResponse(
			@PathVariable("id") String requestee,
			@RequestBody MongoGroup group,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		List<Task> tasks = service.createTasks(
				CreateGroupTask.class,
				new CreateGroupTask.Parameters(requestee, Role.ROLE_ADMINISTRATOR, request, group));
	    
	    return new ResponseEntity<>(tasks.get(0), HttpStatus.CREATED);
	}
	
	@PostMapping(value= "/{id}", produces = "application/json", params= {"create-group", "accept"})
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> createGroupsDecision(
			@PathVariable("id") String id,
			@RequestParam("accept") Boolean decision,
			HttpServletRequest request,
			UriComponentsBuilder b) {
			
		if (decision) {
	    	service.acceptTask(id, request);
	    } else {
	    	service.denyTask(id, request);
	    }
	    
		UriComponents uriComponents = b.path("/api/tasks/{id}").buildAndExpand(id);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED); 	 
	}
	
	@GetMapping(value="", produces = "application/json", params="create-group")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> createGroupList() {
		HashMap<String, List<Task> > tasks = new HashMap<>();
		tasks.put("Create Group Tasks", service.getTasks(CreateGroupTask.class));
		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
		return resp;
	}
	
	@GetMapping(value="", produces = "application/json", params = {"create-group", "status"})
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> createGroupByStatus(@RequestParam("status") TaskStatus status) {
		HashMap<String, List<Task> > tasks = new HashMap<>();
		tasks.put("Pending Group Request Tasks", service.getTasks(CreateGroupTask.class, status));
		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
		return resp;
	}
}
