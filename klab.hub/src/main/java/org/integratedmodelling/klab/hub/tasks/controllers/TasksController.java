package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.enums.TaskType;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TasksController {
	
	@Autowired
	TaskService service;
	
	@GetMapping(value=API.HUB.TASK_BASE_ID, produces = "application/json")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> getTask(@PathVariable("id") String id) {
		Optional<Task> task = service.getTask(id);
		ResponseEntity<?> resp;
		if (task.isPresent()) {
			resp = new ResponseEntity<>(task.get(), HttpStatus.OK);	
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"); // TODO we need compatibility in return codes
		}
		return resp;
	}
	
	@GetMapping(value=API.HUB.TASK_BASE, produces = "application/json")
	@RolesAllowed({ "ROLE_ADMINISTRATOR", "ROLE_SYSTEM" })
	public ResponseEntity<?> getTasks(@RequestParam("type") Optional<TaskType> type,
			@RequestParam("status") Optional<TaskStatus> status) {
		List<Task> tasks = new ArrayList<>();
		if (type.isPresent()) {
			if (status.isPresent()) {
				tasks.addAll(service.getTasks(type.get().getClazz(), status.get()));
			} else {
				tasks.addAll(service.getTasks(type.get().getClazz()));
			}
		} else if (status.isPresent()){
			tasks.addAll(service.getTasks(status.get()));
		} else {
			tasks.addAll(service.getTasks());
		}
		ResponseEntity<?> resp = new ResponseEntity<>(tasks, HttpStatus.OK);
		return resp;
	}
}
