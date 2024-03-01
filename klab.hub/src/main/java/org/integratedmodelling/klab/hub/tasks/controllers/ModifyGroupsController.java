package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.GroupRequestTask;
import org.integratedmodelling.klab.hub.api.ModifyGroupsTask;
import org.integratedmodelling.klab.hub.api.RemoveGroupTask;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import net.minidev.json.JSONObject;


@RestController
public class ModifyGroupsController {
	
	@Autowired
	TaskService service;
	
	@PostMapping(value= API.HUB.TASK_BASE, produces = "application/json", params=API.HUB.PARAMETERS.USER_REQUEST_GROUPS)
	@PreAuthorize("authentication.principal == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> requestGroupsResponse(
			@RequestParam(API.HUB.PARAMETERS.USER_REQUEST_GROUPS) String username,
			@RequestBody List<String> groupNames,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		List<Task> tasks = service.createTasks(
				GroupRequestTask.class,
				new ModifyGroupsTask.Parameters(request, username, groupNames, GroupRequestTask.class));
	    return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value= API.HUB.TASK_BASE, produces = "application/json", params=API.HUB.PARAMETERS.USER_REMOVE_GROUPS)
	@PreAuthorize("authentication.principal == #username or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> removeGroupsResponse(
			@RequestParam(API.HUB.PARAMETERS.USER_REMOVE_GROUPS) String username,
			@RequestBody List<String> groupNames,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		List<Task> tasks = service.createTasks(
				RemoveGroupTask.class,
				new ModifyGroupsTask.Parameters(request, username, groupNames, RemoveGroupTask.class));
	    return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value= API.HUB.TASK_BASE_ID, produces = "application/json", params= API.HUB.PARAMETERS.ACCEPT)
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> requestGroupsDecision(
			@PathVariable("id") String id,
			@RequestParam("accept") Boolean decision,
			@RequestBody Map<String, String> body,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		Task task;
		if (decision) {
	    	task = service.acceptTask(id, request);
	    } else {
	    	String deniedMessage = body.get("deniedMessage");
	    	task = service.denyTask(id, request, deniedMessage);
	    }
	    
		UriComponents uriComponents = b.path(API.HUB.TASK_BASE_ID).buildAndExpand(id);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	    JSONObject resp = new JSONObject();
	    if (task.getLog().size() > 0) {
	    	resp.appendField("log", task.getLog());
	    }
	    resp.appendField("result", task.getStatus());
	    if (task.getStatus() == TaskStatus.error) {
	    	return new ResponseEntity<JSONObject>(resp, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	    } else {
	    	return new ResponseEntity<JSONObject>(resp, headers, HttpStatus.ACCEPTED);
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
