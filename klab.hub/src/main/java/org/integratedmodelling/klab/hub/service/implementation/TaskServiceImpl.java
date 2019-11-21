package org.integratedmodelling.klab.hub.service.implementation;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;

import org.integratedmodelling.klab.hub.models.Task;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.repository.AdminTaskRepository;
import org.integratedmodelling.klab.hub.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	AdminTaskRepository adminTaskRepository;

	@Override
	public void createTask(String requestee, ClickbackToken token) {
		Task task = new Task();
		task.setRequestee(requestee);
		task.setToken(token);
		task.setIssued();
		task.setStatus(TaskStatus.pending);
		task.setExpirationDate(token.getExpiration());
		task.setRoleRequirement(Role.ROLE_ADMINISTRATOR);
		adminTaskRepository.save(task);
	}
	
	@Override
	public void createTask(String requestee, ClickbackToken token, Role roleRequirement) {
		Task task = new Task();
		task.setRequestee(requestee);
		task.setToken(token);
		task.setIssued();
		task.setStatus(TaskStatus.pending);
		task.setExpirationDate(token.getExpiration());
		task.setRoleRequirement(roleRequirement);
		adminTaskRepository.save(task);
	}

	@Override
	public Task changeTaskStatus(String id, TaskStatus status) {
		Optional<Task> task = adminTaskRepository.findById(id);
		if(task.isPresent()) {
			Task updatedTask = task.get();
			updatedTask.setStatus(status);
			updatedTask.setClosed();
			adminTaskRepository.save(updatedTask);
			return updatedTask;
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}

	@Override
	public void deleteTask(String id) {
		Optional<Task> task = adminTaskRepository.findById(id);
		if(task.isPresent()) {
			Task updatedTask = task.get();
			adminTaskRepository.delete(updatedTask);
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}

	@Override
	public List<Task> getTasks() {
		return adminTaskRepository.findAll();
	}

	@Override
	public Task getTask(String id) {
		Optional<Task> task = adminTaskRepository.findById(id);
		if(task.isPresent()) {
			return task.get();
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}

	@Override
	public List<Task> getPendingTasks() {
		return adminTaskRepository.findByStatus(TaskStatus.pending);
	}

	@Override
	public Task getTaskByToken(ClickbackToken token) {
		Optional<Task> task = adminTaskRepository.findByToken(token);
		if(task.isPresent()) {
			return task.get();
		} else {
			throw new BadRequestException(String.format("Task by that token does not exist"));
		}
	}

}
