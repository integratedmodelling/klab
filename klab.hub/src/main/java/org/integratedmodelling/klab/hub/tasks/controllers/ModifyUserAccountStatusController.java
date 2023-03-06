package org.integratedmodelling.klab.hub.tasks.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.ModifyUserAccountStatusTask;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
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
public class ModifyUserAccountStatusController {

	@Autowired
	TaskService service;

	@PostMapping(value=API.HUB.USER_BASE_ID, produces = "application/json", params = API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS)
	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
	public ResponseEntity<?> createGroupsResponse(
			@PathVariable("id") String username,
			@RequestParam(API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS) String accountStatus,
			HttpServletRequest request) {

	    JSONObject resp = new JSONObject();

		try {
			List<Task> tasks = service.createTasks(
				ModifyUserAccountStatusTask.class,
				new ModifyUserAccountStatusTask.Parameters(request, username, AccountStatus.valueOf(accountStatus), ModifyUserAccountStatusTask.class));
	    	return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
	    	resp.appendField("Message", String.format("Account status %s is not valid", accountStatus));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}
}
