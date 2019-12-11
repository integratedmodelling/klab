package org.integratedmodelling.klab.hub.tasks.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.TaskType;

public interface TaskBaseService {
	public abstract Task acceptTask(String id, HttpServletRequest request);
	public abstract Task denyTask(String id, HttpServletRequest request);
	public abstract List<Task> getTasks(TaskType type);
	public abstract List<Task> getTasksByStatus(TaskType type, TaskStatus status);
}
