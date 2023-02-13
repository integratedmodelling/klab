package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.RemoveRoleTask;
import org.integratedmodelling.klab.hub.api.SetRoleTask;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.minidev.json.JSONObject;

@RestController
public class ModifyRolesController {

	@Autowired
	TaskService service;
	
	@PostMapping(value= API.HUB.TASK_BASE_ID, produces = "application/json", params=API.HUB.PARAMETERS.SET_ROLES)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> setRolesResponse (
			@PathVariable("id") String username,
			@RequestParam(API.HUB.PARAMETERS.SET_ROLES) List<String> roleNames,
			HttpServletRequest request) {
		
		JSONObject resp = new JSONObject();
		Set<Role> roles = new HashSet<Role>();
		for (String roleName : roleNames) {
			try {
				roles.add(Role.valueOf(roleName));
			} catch (IllegalArgumentException e) {
				resp.appendField("Message", String.format("Role %s is not valid", roleName));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
			}
		}
		
		List<Task> tasks = service.createTasks(
				SetRoleTask.class,
				new SetRoleTask.Parameters(request, username, roles, SetRoleTask.class));
		return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value= API.HUB.TASK_BASE_ID, produces = "application/json", params=API.HUB.PARAMETERS.REMOVE_ROLES)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> removeRolesResponse (
			@PathVariable("id") String username,
			@RequestParam(API.HUB.PARAMETERS.REMOVE_ROLES) List<String> roleNames,
			HttpServletRequest request) {
		
		JSONObject resp = new JSONObject();
		Set<Role> roles = new HashSet<Role>();
		for (String roleName : roleNames) {
			try {
				roles.add(Role.valueOf(roleName));
			} catch (IllegalArgumentException e) {
				resp.appendField("Message", String.format("Role %s is not valid", roleName));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
			}
		}
		
		List<Task> tasks = service.createTasks(
				RemoveRoleTask.class,
				new RemoveRoleTask.Parameters(request, username, roles, RemoveRoleTask.class));
		return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}

}
