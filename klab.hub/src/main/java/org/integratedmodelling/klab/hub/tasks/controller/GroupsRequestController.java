package org.integratedmodelling.klab.hub.tasks.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.service.GroupRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/tasks")
@RestController
public class GroupsRequestController {
	
	@Autowired
	GroupRequestService service;
	
	@PostMapping(value= "/{id}", produces = "application/json", params="requestGroups")
	@PreAuthorize("authentication.getPrincipal() == #requestee")
	public ResponseEntity<?> requestGroupsResponse(
			@PathVariable("id") String requestee,
			@RequestParam("requestGroups") List<String> groupNames,
			HttpServletRequest request,
			UriComponentsBuilder b) {
		
		GroupRequestTask task = service.createTask(requestee, groupNames, request);
	    
		UriComponents uriComponents = b.path("/api/tasks/{id}").buildAndExpand(task.getId());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	    
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping(value= "/{id}", produces = "application/json", params="accept")
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
	    
		UriComponents uriComponents = b.path("/api/tasks/{id}").buildAndExpand(id);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED); 	 
	}
	
	@GetMapping(value="/group-requests", produces = "application/json")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> requestGroupsList() {
		HashMap<String, List<Task> > tasks = new HashMap<>();
		tasks.put("Group Request Tasks", service.getTasks());
		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
		return resp;
	}
}
