package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class GroupsRequestController {
	
	@Autowired
	TaskService service;
	
	@PostMapping(value= "/{id}", produces = "application/json", params="request-groups")
	@PreAuthorize("authentication.getPrincipal() == #requestee or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> requestGroupsResponse(
			@PathVariable("id") String requestee,
			@RequestBody List<String> groupNames,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		List<Task> tasks = service.createTasks(
				GroupRequestTask.class,
				new GroupRequestTask.Parameters(requestee, request, groupNames));
	    return new ResponseEntity<>(tasks, HttpStatus.CREATED);
	}
	
	@PostMapping(value= "/{id}", produces = "application/json", params= {"request-groups", "accept"})
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> requestGroupsDecision(
			@PathVariable("id") String id,
			@RequestParam("accept") Boolean decision,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		if (decision) {
	    	service.acceptTask(id, request);
	    } else {
	    	service.denyTask(id, request);
	    }
	    
		UriComponents uriComponents = b.path("/api/v2/tasks/{id}").buildAndExpand(id);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED); 	 
	}
	/*
	@GetMapping(value="", produces = "application/json", params="request-groups")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> groupRequestList() {
		HashMap<String, List<Task> > tasks = new HashMap<>();
		tasks.put("Group Request Tasks", service.getTasks(GroupRequestTask.class));
		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
		return resp;
	}
	
	@GetMapping(value="", produces = "application/json", params = {"request-groups", "status"})
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> groupsRequestsByStatus(TaskStatus status) {
		HashMap<String, List<Task> > tasks = new HashMap<>();
		tasks.put("Pending Group Request Tasks with status "+status, service.getTasks(GroupRequestTask.class, status));
		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
		return resp;
	}
	*/
}
