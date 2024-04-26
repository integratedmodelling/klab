package org.integratedmodelling.klab.hub.tasks.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.api.GroupRequestTask;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskParameters;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TaskRepository;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.support.TaskBuilder;
import org.integratedmodelling.klab.hub.tokens.dto.TokenClickback;
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
	@Autowired
	EmailManager emailManager;

	@Override
	public List<Task> createTasks(Class<? extends Task> clazz, TaskParameters parameters) {
		List<Task> tasks = null;
		TaskBuilder builder = TaskFactory.getBuilder(clazz);
		if (builder == null) {
			throw new KlabAuthorizationException("Unable to get builder for class "+clazz);
		}
		tasks = builder.build(parameters);
		saveAllTasks(tasks);
		for(Task task: tasks) {
			if (task.getParentStatus() != TaskStatus.pending && task.isAutoAccepted()) {
				acceptTask(task, parameters.getRequest());
			} else if (task instanceof GroupRequestTask) {
			    GroupRequestTask grt = (GroupRequestTask)task;
			    emailManager.sendNewGroupRequest(grt.getUser(), grt.getGroupsName());
			}
		}
		return tasks;
	}
	
	@Override
	public void acceptTask(Task task, HttpServletRequest request) {
		doAction(task, request, Actions.ACCEPT);
	}
	
	@Override
	public Task acceptTask(String id, HttpServletRequest request) {
		return doAction(findTask(id), request, Actions.ACCEPT);
	}
	
	@Override
	public void denyTask(Task task, HttpServletRequest request, String deniedMessage) {
		doAction(task, request, Actions.DENY, deniedMessage);
	}

	@Override
	public Task denyTask(String id, HttpServletRequest request, String deniedMessage) {
		return doAction(findTask(id), request, Actions.DENY, deniedMessage);
	}
	
	private Task findTask(String id) {
		Optional<Task> optTask = getTask(id);
		if (optTask.isPresent()) {
			return optTask.get();
		} else {
			throw new BadRequestException("Task already handled or does not exist.");
		}
	}
	
	private Task doAction(Task task, HttpServletRequest request, Actions action) {
		return doAction(task, request, action, null);
	}
	
	private Task doAction(Task task, HttpServletRequest request, Actions action, String deniedMessage) {
		if (task.getStatus() == TaskStatus.pending) {
			if (request.isUserInRole(task.getRoleRequirement())) {
				if (action == Actions.ACCEPT) {
					task.acceptTaskAction(request);
				} else if (action == Actions.DENY) {
					task.denyTaskAction(request, deniedMessage);
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
								acceptTask(t, request);
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
	}
			
	@Override
	public void closeTask(Task task, TaskStatus status) {
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
	

	@Override
	public List<Task> getTasks() {
		return taskRepository.findAll();
	}

	@Override
	public void saveTask(Task task) {
		taskRepository.save(task);
	}
	
	@Override
	public void saveAllTasks(Iterable<Task> tasks) {
		taskRepository.saveAll(tasks);
	}


	@Override
	public Optional<Task> getTask(String id) {
		return taskRepository.findById(id);
	}

	@Override
	public List<Task> getTasks(Class<? extends Task> clazz) {
		return taskRepository.findByClass(clazz.getSimpleName());
	}

	@Override
	public List<Task> getTasks(TaskStatus status) {
		return taskRepository.findByStatus(status);
	}

	@Override
	public List<Task> getTasks(Class<? extends Task> clazz, TaskStatus status) {
		return taskRepository.findByClassAndStatus(clazz.getSimpleName(), status);
	}
	
	public Optional<Task> getTaskByToken(Class<? extends Task> clazz, TokenClickback token) {
		return taskRepository.findByToken(clazz.getSimpleName(), token);
	}
}
