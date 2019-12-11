package org.integratedmodelling.klab.hub.tasks.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.models.GroupEntry;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.repository.KlabGroupRepository;
import org.integratedmodelling.klab.hub.repository.TaskRepository;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.service.UserService;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.TaskType;
import org.integratedmodelling.klab.hub.tasks.commands.GroupRequestAccept;
import org.integratedmodelling.klab.hub.tasks.commands.GroupRequestDeny;
import org.springframework.stereotype.Service;

@Service
public class GroupRequestServiceImpl implements GroupRequestService {
	
	private UserService userService;
	
	private TokenRepository tokenRepository;
	
	private KlabGroupRepository groupRepository;
	
	private TaskRepository taskRepository;
	
	public GroupRequestServiceImpl(UserService userService,
			TokenRepository tokenRepository,
			KlabGroupRepository groupRepository,
			TaskRepository taskRepository) {
		this.userService = userService;
		this.tokenRepository = tokenRepository;
		this.groupRepository = groupRepository;
		this.taskRepository = taskRepository;
	}

	@Override
	public GroupRequestTask acceptTask(String id, HttpServletRequest request) {
		GroupRequestTask task = getPendingTask(id);
		if (request.isUserInRole(task.getRoleRequirement())) {
			GroupRequestAccept action = new GroupRequestAccept(task, userService, tokenRepository);
			task = action.execute();
			taskRepository.save(task);
			return task;
		} else {
			return task;
		}
	}

	@Override
	public GroupRequestTask denyTask(String id, HttpServletRequest request) {
		GroupRequestTask task = getPendingTask(id);
		if (request.isUserInRole(task.getRoleRequirement())) {
			GroupRequestDeny action = new GroupRequestDeny(task, tokenRepository);
			task = action.execute();
			taskRepository.save(task);
			return task;
		} else {
			return task;
		}
		
	}

	@Override
	public GroupRequestTask createTask(String requestee, List<String> groupNames, HttpServletRequest request) {
		
		Set<GroupEntry> optIn = new HashSet<>();
		Set<GroupEntry> requestGroups = new HashSet<GroupEntry>();
		Set<GroupEntry> userGroupEntries = userService.getUserFromMongo(requestee)
				.map(User::getGroupEntries)
				.orElse(new HashSet<>());
		
		List<String> currentGroups = new ArrayList<String>();
		userGroupEntries.forEach(entry -> {
			currentGroups.add(entry.getGroupName());	
		});
		
		if(currentGroups.containsAll(groupNames)) {
			throw new BadRequestException("Requested Groups already available to user.");
		}
		
		List<Boolean> exists = groupNames.stream()
				.map(groupRepository::existsByGroupNameIgnoreCase)
				.collect(Collectors.toList());
		
		if(exists.contains(false) | groupNames.size() == 0) {
			throw new BadRequestException("A requested Group does not exist or no groups requested");
		}
		
		for (String groupName : groupNames) {
			groupRepository
				.findByGroupNameIgnoreCase(groupName)
				.filter(group -> request.isUserInRole(group.getRoleRequirement().toString()))
				.ifPresent(group -> optIn.add(new GroupEntry(group)));
			
			groupRepository.findByGroupNameIgnoreCase(groupName)
				.filter(group -> !request.isUserInRole(group.getRoleRequirement().toString()))
				.ifPresent(group -> requestGroups.add(new GroupEntry(group)));
		}
		
		userService.addUserGroupEntries(requestee, optIn);
		
		if(requestGroups.isEmpty()) {
			GroupRequestTask task = new GroupRequestTask(requestee);
			task.setStatus(TaskStatus.acceptedTask);
			taskRepository.save(task);
			return task;
		} else {
			return createTask(requestee, requestGroups);
		}
		
	}
	
	private GroupRequestTask createTask(String requestee, Set<GroupEntry> requestGroups) {
		GroupRequestTask task = new GroupRequestTask(requestee);
		GroupsClickbackToken token = new GroupsClickbackToken(requestee);
		token.setGroups(requestGroups.stream().collect(Collectors.toList()));
		tokenRepository.save(token);
		task.setToken(token);
		task.setRoleRequirement(Role.ROLE_ADMINISTRATOR);
		task.setStatus(TaskStatus.pending);
		taskRepository.save(task);
		return task;
	}
	
	private GroupRequestTask getPendingTask(String id) {
		GroupRequestTask task = taskRepository.findById(id)
				.map(GroupRequestTask.class::cast)
				.filter(t -> t.getStatus() == TaskStatus.pending)
				.orElseThrow(() -> new BadRequestException("Task already handled or does not exist."));
		return task;
	}

	@Override
	public List<Task> getTasks(TaskType type) {
		return taskRepository.findByType(type);
	}

	@Override
	public List<Task> getTasksByStatus(TaskType type, TaskStatus status) {
		return taskRepository.findTaskByClassAndStatus(type, status);
	}
	
}
