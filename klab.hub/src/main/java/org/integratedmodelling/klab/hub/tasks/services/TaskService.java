package org.integratedmodelling.klab.hub.tasks.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskParameters;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.integratedmodelling.klab.hub.tokens.dto.TokenClickback;

public interface TaskService {
	/**
	 * Create a new task based on the given class
	 * @param clazz the class of task
	 * @param request HttpSevletRequest is needed to check the role in case of accept
	 * @param parameters
	 * @return
	 */
	public abstract List<Task> createTasks(Class<? extends Task> clazz, TaskParameters parameters);
	public abstract void saveTask(Task task);
	public abstract void saveAllTasks(Iterable<Task> tasks);
	public abstract Task closeTask(String id, TaskStatus status);
	public abstract void closeTask(Task task, TaskStatus status);
	public abstract void deleteTask(String id);
	/**
	 * Accept a task by id
	 * @param id the id of task
	 * @param request the request
	 * @return the task accepted
	 */
	public abstract Task acceptTask(String id, HttpServletRequest request);
	/**
	 * Accept a task. The task is modified directly
	 * @param task the task
	 * @param request the request
	 */
	public abstract void acceptTask(Task task, HttpServletRequest request);
	/**
	 * Deny a task by id
	 * @param id the id of task
	 * @param request the request
	 * @param deniedMessage the optional message for denied action
	 * @return the task denied
	 */
	public abstract Task denyTask(String id, HttpServletRequest request, String deniedMessage);
	/**
	 * Deny a task. The task is modified directly
	 * @param task the task to deny
	 * @param request the request
	 * @param deniedMessage the optional message for denied action
	 */
	public abstract void denyTask(Task task, HttpServletRequest request, String deniedMessage);
	public abstract Optional<Task> getTask(String id);
	public abstract List<Task> getTasks();
	public abstract List<Task> getTasks(Class<? extends Task> clazz);
	public abstract List<Task> getTasks(TaskStatus status);
	public abstract List<Task> getTasks(Class<? extends Task> clazz, TaskStatus status);
	public abstract Optional<Task> getTaskByToken(Class<? extends Task> type, TokenClickback token);
}
