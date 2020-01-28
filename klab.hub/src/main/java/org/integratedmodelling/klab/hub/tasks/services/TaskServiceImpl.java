package org.integratedmodelling.klab.hub.tasks.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TaskRepository;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskBuilder;
import org.integratedmodelling.klab.hub.tasks.TaskFactory;
import org.integratedmodelling.klab.hub.tasks.TaskParameters;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService{
	
	private enum Actions {
		ACCEPT,
		DENY;
	}
	
	@Autowired
	TaskRepository taskRepository;

	@Override
	public List<Task> createTasks(Class<? extends Task> clazz, HttpServletRequest request, TaskParameters parameters) {
		List<Task> tasks = null;
		TaskBuilder builder = TaskFactory.getBuilder(clazz);
		if (builder == null) {
			throw new KlabAuthorizationException("Unable to get builder for class "+clazz);
		}
		tasks = builder.build(parameters);
		for(Task task: tasks) {
			saveTask(task);
			if (task.isAutoAccepted()) {
				acceptTask(task.getId(), request);
			}
		}
		return tasks;
	}
	
	@Override
	public Task acceptTask(String id, HttpServletRequest request) {
		return doAction(id, request, Actions.ACCEPT);
	}

	@Override
	public Task denyTask(String id, HttpServletRequest request) {
		return doAction(id, request, Actions.DENY);
	}
	
	private Task doAction(String id, HttpServletRequest request, Actions action) {
		Optional<Task> optTask = getTask(id);
		if (optTask.isPresent()) {
			Task task = optTask.get();
			if (task.getStatus() == TaskStatus.pending) {
				if (request.isUserInRole(task.getRoleRequirement())) {
					if (action == Actions.ACCEPT) {
						task.acceptTaskAction(request);
					} else if (action == Actions.DENY) {
						task.denyTaskAction(request);
					}  
					closeTask(task, task.getStatus());
					List<Task> next = task.getNext();
					if (next.size() > 0) {
						if (TaskStatus.denied.equals(task.getStatus()) || TaskStatus.error.equals(task.getStatus())) {
							cascadeErrorOrDeny(task);
						} else {
							for(Task t: next) {
								t.setParentStatus(task.getStatus());
								if (t.isAutoAccepted()) {
									acceptTask(t.getId(), request);
								}
							}
						}
					}
					return task;
				} else {
					return task;
				}
			} else {
				throw new BadRequestException("Task already handled");
			}
		} else {
			throw new BadRequestException("Task already handled or does not exist.");
		}
	}
			
	@Override
	public Task closeTask(String id, TaskStatus status) {
		Optional<Task> task = taskRepository.findById(id);
		if(task.isPresent()) {
			Task updatedTask = task.get();
			closeTask(updatedTask, status);
			return updatedTask;
		} else {
			throw new BadRequestException(String.format("Task by %s id, does not exist", id));
		}
	}
	
	/**
	 * Close task using sent status and save to the repository
	 * @param task
	 * @param status
	 */
	private void closeTask(Task task, TaskStatus status) {
		task.setStatus(status);
		task.setClosed();
		saveTask(task);
	}
	
	/**
	 * Close the task tree in case of error or deny
	 * @param task
	 */
	private void cascadeErrorOrDeny(Task task) {
		List<Task> next = task.getNext();
		for (Task t: next) {
			t.setParentStatus(task.getStatus());
			closeTask(task, task.getStatus());
			if (t.getNext().size() > 0) {
				cascadeErrorOrDeny(t);
			}
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
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Optional<Task> getTask(String id) {
		return taskRepository.findById(id);
	}

	@Override
	public List<Task> getTasks(Class<? extends Task> clazz) {
		return taskRepository.findByClass(clazz);
	}

	@Override
	public List<Task> getTasks(TaskStatus status) {
		return taskRepository.findByStatus(status);
	}

	@Override
	public List<Task> getTasks(Class<? extends Task> clazz, TaskStatus status) {
		return taskRepository.findByClassAndStatus(clazz, status);
	}
	
	public Optional<Task> getTaskByToken(Class<? extends Task> clazz, ClickbackToken token) {
		return taskRepository.findByToken(clazz, token);
	}

}
