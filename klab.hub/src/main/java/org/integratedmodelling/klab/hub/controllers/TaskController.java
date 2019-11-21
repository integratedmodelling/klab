package org.integratedmodelling.klab.hub.controllers;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.manager.TaskManager;
import org.integratedmodelling.klab.hub.models.tasks.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RequestMapping("/api/tasks")
@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
@RestController
public class TaskController {
	
	@Autowired
	TaskManager taskManager;
	
	@GetMapping(value = "")
	public ResponseEntity<Object> getTasks() {
		JSONObject resp = new JSONObject();
		resp.appendField("Tasks", taskManager.getTasks());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@GetMapping(value= "/{id}", produces = "application/json")
	public ResponseEntity<Object> getTask(@PathVariable("id") String taskId) {
		JSONObject resp = new JSONObject();
		resp.appendField("Tasks", taskManager.getTask(taskId));
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value= "/{id}", produces = "application/json", params="accpeted")
	public ResponseEntity<Object> taskApproval(@PathVariable("id") String taskId,
			@RequestParam("accpeted") Boolean decision,
			HttpServletRequest request) {
		Task task = taskManager.taskDecision(taskId, decision, request);
		JSONObject resp = new JSONObject();
		resp.appendField("Task", task);
		return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
	}
	
}
