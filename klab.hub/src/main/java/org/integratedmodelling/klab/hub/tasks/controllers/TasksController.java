package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskStatus;
import org.integratedmodelling.klab.hub.api.TaskType;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
	
    @GetMapping(value = API.HUB.TASK_BASE, produces = "application/json")
    @RolesAllowed({"ROLE_ADMINISTRATOR", "ROLE_SYSTEM"})
    public ResponseEntity< ? > getTasks(
            @RequestParam(name = "type") Optional<TaskType> type,
            @RequestParam(name = "status") Optional<TaskStatus> status,
            @RequestParam(name = API.HUB.PARAMETERS.PAGE, required = false) Optional<Integer> page,
            @RequestParam(name = API.HUB.PARAMETERS.RECORDS, required = false) Optional<Integer> records) {
        List<Task> tasks = hasValidPaginationParameters(page, records) ?
                getRequestedTasks(type, status, page, records) :
                getRequestedTasks(type, status);
        ResponseEntity< ? > resp = new ResponseEntity<>(tasks, HttpStatus.OK);
        return resp;
    }

    private boolean hasValidPaginationParameters(Optional<Integer> page, Optional<Integer> records) {
        return page.isPresent() && records.isPresent();
    }

    private List<Task> getRequestedTasks(Optional<TaskType> type, Optional<TaskStatus> status) {
        if (type.isPresent()) {
            return status.isPresent() ? 
                service.getTasks(type.get().getClazz(), status.get()) :
                service.getTasks(type.get().getClazz());
        }
        return status.isPresent() ?
                service.getTasks(status.get()) :
                service.getTasks();
    }

    private List<Task> getRequestedTasks(Optional<TaskType> type, Optional<TaskStatus> status, Optional<Integer> page, Optional<Integer> records) {
        if (type.isPresent()) {
            return status.isPresent() ?
                service.getTasksPaginated(type.get().getClazz(), status.get(), PageRequest.of(page.get(), records.get())) :
                service.getTasksPaginated(type.get().getClazz(), PageRequest.of(page.get(), records.get()));
        }
        return status.isPresent() ?
                service.getTasksPaginated(status.get(), PageRequest.of(page.get(), records.get())) :
                service.getTasksPaginated(PageRequest.of(page.get(), records.get()));
    }

}
