package org.integratedmodelling.klab.hub.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;

import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.models.GroupEntry;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.models.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.models.tasks.Task;
import org.integratedmodelling.klab.hub.models.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.integratedmodelling.klab.hub.service.TaskService;
import org.integratedmodelling.klab.hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskManager {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	KlabGroupService klabGroupService;
	
	
	public List<Task> getTasks() {
		return taskService.getTasks();
	}

	public Task taskDecision(String taskId, Boolean decision, HttpServletRequest request) {
		if(decision) {
			return acceptTask(taskId, request);
		} else {
			return denyTask(taskId, request);
		}
	}

	private Task acceptTask(String taskId, HttpServletRequest request) {
		Task task = taskService.getTask(taskId);
		if(request.isUserInRole(task.getRoleRequirement())) {
			if(task.getClass().equals(GroupRequestTask.class)) {
				GroupRequestTask taskRequest =  (GroupRequestTask) task;
				ClickbackToken token = taskRequest.getToken();
				return acceptGroupsRequest((GroupsClickbackToken) token, task);
			} 
			if(task.getClass().equals(CreateGroupTask.class)) {
				CreateGroupTask taskRequest = (CreateGroupTask) task;
				return acceptCreateGroupRequest(taskRequest);
			}
			else {
				return task;
			}
		} else {
			throw new AuthenticationFailedException("Task above paygrade");
		}
	}

	private Task acceptGroupsRequest(GroupsClickbackToken token, Task task) {
		Set<GroupEntry> groups = token.getGroups().stream().collect(Collectors.toSet());
		userService.addUserGroupEntries(token.getName(), groups);
		tokenRepository.delete(token);
		return taskService.changeTaskStatus(task.getId(), TaskStatus.acceptedTask);
	}
	
	private Task denyTask(String taskId, HttpServletRequest request) {
		Task task = taskService.getTask(taskId);
		if(request.isUserInRole(task.getRoleRequirement())) {
			if(task.getClass().equals(GroupRequestTask.class)) {
				GroupRequestTask taskRequest =  (GroupRequestTask) task;
				ClickbackToken token = taskRequest.getToken();
				tokenRepository.delete(token);
				return taskService.changeTaskStatus(task.getId(), TaskStatus.denied);
			} else {
				return task;
			}
		} else {
			throw new BadRequestException("Task above paygrade");
		}
	}

	public Task getTask(String taskId) {
		return taskService.getTask(taskId);
	}

	public CreateGroupTask createGroupTask(String username, KlabGroup group) {
		if (!klabGroupService.getGroupNames().contains(group.getId())) {
			CreateGroupTask task = (CreateGroupTask) taskService.createTask(username, CreateGroupTask.class, Role.ROLE_SYSTEM);
			task.setGroup(group);
			taskService.saveTask(task);
			return task;
		} else {
			throw new BadRequestException("Requested group already in groups");
		}
	}
	
	private Task acceptCreateGroupRequest(CreateGroupTask task) {
		klabGroupService.createGroup(task.getGroup().getId(), task.getGroup());
		return taskService.changeTaskStatus(task.getId(), TaskStatus.acceptedTask);
	}

	public void userRequestGroupsTask(String username, List<String> groupNames, HttpServletRequest request) {
		Set<GroupEntry> optIn = new HashSet<>();
		Set<GroupEntry> requestGroups = new HashSet<GroupEntry>();
		if(!klabGroupService.groupsExists(groupNames)) {
			throw new BadRequestException("A requested Group does not exist.");
		}
		
		for (String groupName : groupNames) {
			klabGroupService
				.getGroup(groupName)
				.filter(group -> request.isUserInRole(group.getRoleRequirement().toString()))
				.ifPresent(group -> optIn.add(new GroupEntry(group)));
			
			klabGroupService.getGroup(groupName)
				.filter(group -> !request.isUserInRole(group.getRoleRequirement().toString()))
				.ifPresent(group -> requestGroups.add(new GroupEntry(group)));
		}
		userService.addUserGroupEntries(username, optIn);
		//TODO
	}

}
