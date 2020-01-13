package org.integratedmodelling.klab.hub.tasks.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.repository.TaskRepository;
import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.TaskType;
import org.integratedmodelling.klab.hub.tasks.commands.CreateGroupAccept;
import org.integratedmodelling.klab.hub.tasks.commands.CreateGroupDeny;
import org.integratedmodelling.klab.hub.users.Role;
import org.springframework.stereotype.Service;

@Service
public class CreateGroupServiceImpl implements CreateGroupService {

	private MongoGroupRepository groupRepository;
	
	private TaskRepository taskRepository;
	
	public CreateGroupServiceImpl(MongoGroupRepository groupRepository,
			TaskRepository taskRepository) {
		this.groupRepository = groupRepository;
		this.taskRepository = taskRepository;
	}
	
	@Override
	public Task createTask(String requestee, MongoGroup group, HttpServletRequest request) {
		
		Boolean exists = groupRepository
				.findByGroupNameIgnoreCase(group.getGroupName())
				.isPresent();
		
		if(exists) {
			throw new BadRequestException("Group by that name already present.");
		}
		
		if (request.isUserInRole(Role.SYSTEM)) {
			CreateGroupTask task = new CreateGroupTask(requestee);
			task.setGroup(group);
			taskRepository.save(task);
			task = acceptTask(task.getId(), request);
			return task;
		} else {
			CreateGroupTask task = new CreateGroupTask(requestee);
			task.setGroup(group);
			taskRepository.save(task);
			return task;
		}
	}
	
	@Override
	public CreateGroupTask acceptTask(String id, HttpServletRequest request) {
		CreateGroupTask task = getPendingTask(id);
		if (request.isUserInRole(task.getRoleRequirement())) {
			CreateGroupAccept action = new CreateGroupAccept(task, groupRepository);
			task = action.execute();
			taskRepository.save(task);
			return task;
		} else {
			return task;
		}
	}

	@Override
	public Task denyTask(String id, HttpServletRequest request) {
		CreateGroupTask task = getPendingTask(id);
		if (request.isUserInRole(task.getRoleRequirement())) {
			CreateGroupDeny action = new CreateGroupDeny(task);
			task = action.execute();
			taskRepository.save(task);
			return task;
		} else {
			return task;
		}
	}

	@Override
	public List<Task> getTasks(TaskType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getTasksByStatus(TaskType type, TaskStatus status) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CreateGroupTask getPendingTask(String id) {
		CreateGroupTask task = taskRepository.findById(id)
				.map(CreateGroupTask.class::cast)
				.filter(t -> t.getStatus() == TaskStatus.pending)
				.orElseThrow(() -> new BadRequestException("Task already handled or does not exist."));
		return task;
	}

}
