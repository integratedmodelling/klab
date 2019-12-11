package org.integratedmodelling.klab.hub.tasks.controller;

import org.integratedmodelling.klab.hub.tasks.service.CreateGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/tasks")
@RestController
public class CreateGroupsController {
	
	@Autowired
	CreateGroupService service;
	
//	@PostMapping(value= "/{id}", produces = "application/json", params="requestGroups")
//	@PreAuthorize("authentication.getPrincipal() == #requestee")
//	public ResponseEntity<?> requestGroupsResponse(
//			@PathVariable("id") String requestee,
//			@RequestParam("requestGroups") List<String> groupNames,
//			HttpServletRequest request,
//			UriComponentsBuilder b) {
//		
//		Task task = service.createTask(requestee, groupNames, request);
//	    
//		UriComponents uriComponents = b.path("/api/tasks/{id}").buildAndExpand(task.getId());
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setLocation(uriComponents.toUri());
//	    
//	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//	}
//	
//	@PostMapping(value= "/{id}", produces = "application/json", params="accept")
//	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
//	public ResponseEntity<?> requestGroupsDecision(
//			@PathVariable("id") String id,
//			@RequestParam("accept") Boolean decision,
//			HttpServletRequest request,
//			UriComponentsBuilder b) {
//			
//		if (decision) {
//	    	service.acceptTask(id, request);
//	    } else {
//	    	service.denyTask(id, request);
//	    }
//	    
//		UriComponents uriComponents = b.path("/api/tasks/{id}").buildAndExpand(id);
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setLocation(uriComponents.toUri());
//	
//	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED); 	 
//	}
//	
//	@GetMapping(value="/group-requests", produces = "application/json")
//	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
//	public ResponseEntity<?> groupRequestList() {
//		HashMap<String, List<Task> > tasks = new HashMap<>();
//		tasks.put("Group Request Tasks", service.getTasks(TaskType.groupRequest));
//		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
//		return resp;
//	}
//	
//	@GetMapping(value="/group-requests", produces = "application/json", params = "status")
//	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
//	public ResponseEntity<?> groupsRequestsByStatus(TaskStatus status) {
//		HashMap<String, List<Task> > tasks = new HashMap<>();
//		tasks.put("Pending Group Request Tasks", service.getTasksByStatus(TaskType.groupRequest, status));
//		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
//		return resp;
//	}
}
