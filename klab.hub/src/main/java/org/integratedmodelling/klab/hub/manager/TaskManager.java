package org.integratedmodelling.klab.hub.manager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;

import org.integratedmodelling.klab.hub.models.Task;
import org.integratedmodelling.klab.hub.models.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.service.TaskService;
import org.integratedmodelling.klab.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskManager {
	
	@Autowired
	TaskService adminTaskService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	UserService userService;
	
	public List<Task> getTasks() {
		return adminTaskService.getTasks();
	}

	public Task taskDecision(String taskId, Boolean decision, HttpServletRequest request) {
		if(decision) {
			return acceptTask(taskId, request);
		} else {
			return denyTask(taskId, request);
		}
	}

	private Task acceptTask(String taskId, HttpServletRequest request) {
		Task task = adminTaskService.getTask(taskId);
		if(request.isUserInRole(task.getRoleRequirement())) {
			ClickbackToken token = task.getToken();
			if (token.getClass().equals(GroupsClickbackToken.class)) {
				Task updatedTask = acceptGroupsRequest((GroupsClickbackToken) token, task);
				return updatedTask;
			} else {
				return task;
			}
		} else {
			throw new BadRequestException("Task above paygrade");
		}
	}

	private Task acceptGroupsRequest(GroupsClickbackToken token, Task task) {
		Set<String> groups = token.getGroups().stream().collect(Collectors.toSet());
		userService.addUserGroups(token.getName(), groups);
		tokenRepository.delete(token);
		return adminTaskService.changeTaskStatus(task.getId(), TaskStatus.acceptedTask);
	}
	
	private Task denyTask(String taskId, HttpServletRequest request) {
		Task task = adminTaskService.getTask(taskId);
		if(request.isUserInRole(task.getRoleRequirement())) {
			tokenRepository.delete(task.getToken());
			return adminTaskService.changeTaskStatus(task.getId(), TaskStatus.denied);
		} else {
			throw new BadRequestException("Task above paygrade");
		}
	}

	public Task getTask(String taskId) {
		return adminTaskService.getTask(taskId);
	}	

}
