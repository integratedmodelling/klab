package org.integratedmodelling.klab.hub.service.implementation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.tasks.Task;
import org.integratedmodelling.klab.hub.models.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.repository.TaskRepository;
import org.integratedmodelling.klab.hub.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	TaskRepository taskRepository;

	@Override
	public Task createTask(String requestee, Class<? extends Task> taskType) {
		Task task = null;
		try {
			task = taskType.getConstructor(String.class).newInstance(requestee);
		} catch (Exception e) {
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		task.setIssued();
		task.setStatus(TaskStatus.pending);
		task.setRoleRequirement(Role.ROLE_ADMINISTRATOR);
		return task;
	}
	
	@Override
	public Task createTask(String requestee, Class<? extends Task> taskType, Role roleRequirement) {
		Task task = null;
		try {
			task = taskType.getConstructor(String.class).newInstance(requestee);
		} catch (Exception e) {
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		task.setRequestee(requestee);
		task.setIssued();
		task.setStatus(TaskStatus.pending);
		task.setRoleRequirement(roleRequirement);
		return task;
	}

	@Override
	public Task changeTaskStatus(String id, TaskStatus status) {
		Optional<Task> task = taskRepository.findById(id);
		if(task.isPresent()) {
			Task updatedTask = task.get();
			updatedTask.setStatus(status);
			updatedTask.setClosed();
			taskRepository.save(updatedTask);
			return updatedTask;
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}

	@Override
	public void deleteTask(String id) {
		Optional<Task> task = taskRepository.findById(id);
		if(task.isPresent()) {
			Task updatedTask = task.get();
			taskRepository.delete(updatedTask);
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}

	@Override
	public List<Task> getTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Task getTask(String id) {
		Optional<Task> task = taskRepository.findById(id);
		if(task.isPresent()) {
			return task.get();
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}

	@Override
	public List<Task> getPendingTasks() {
		return taskRepository.findByStatus(TaskStatus.pending);
	}

	@Override
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task getGroupRequestTaskByToken(ClickbackToken token) {
		Optional<Task> task = taskRepository.findGroupRequestByToken(token);
		if (task.isPresent()) {
			return task.get();
		} else {
			throw new BadRequestException(String.format("Group Reqest Task with this token does not exist"));
		}
	}

}
