package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.ModifyGroupsTask;
import org.integratedmodelling.klab.hub.tasks.RemoveGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class ModifyGroupsController {
	
	@Autowired
	TaskService service;
	
	@PostMapping(value= "", produces = "application/json", params="request-groups")
	@PreAuthorize("authentication.principal == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> requestGroupsResponse(
			@RequestParam("request-groups") String username,
			@RequestBody List<String> groupNames,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		List<Task> tasks = service.createTasks(
				GroupRequestTask.class,
				new ModifyGroupsTask.Parameters(request, username, groupNames, GroupRequestTask.class));
	    return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value= "", produces = "application/json", params="remove-groups")
	@PreAuthorize("authentication.principal == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> removeGroupsResponse(
			@RequestParam("remove-groups") String username,
			@RequestBody List<String> groupNames,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		List<Task> tasks = service.createTasks(
				RemoveGroupTask.class,
				new ModifyGroupsTask.Parameters(request, username, groupNames, RemoveGroupTask.class));
	    return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value= "/{id}", produces = "application/json", params= {"accept", "deniedMessage"})
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> requestGroupsDecision(
			@PathVariable("id") String id,
			@RequestParam("accept") Boolean decision,
			@RequestParam(value="deniedMessage", required=false) Optional<String> deniedMessage,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		Task task;
		if (decision) {
	    	task = service.acceptTask(id, request);
	    } else {
	    	task = service.denyTask(id, request, deniedMessage.isPresent() ? deniedMessage.get() : null);
	    }
	    
		UriComponents uriComponents = b.path("/api/v2/tasks/{id}").buildAndExpand(id);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	    if (task.getStatus() == TaskStatus.accepted) {
	    	return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);
	    } else {
	    	return new ResponseEntity<String>(task.getDeniedMessage() == null ? "Task denied" : task.getDeniedMessage(), headers, HttpStatus.ACCEPTED);
	    }
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
