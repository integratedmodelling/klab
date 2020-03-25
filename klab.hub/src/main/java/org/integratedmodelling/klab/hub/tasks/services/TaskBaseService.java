package org.integratedmodelling.klab.hub.tasks.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskStatus;
import org.integratedmodelling.klab.hub.api.TaskType;

public interface TaskBaseService {
	public abstract Task acceptTask(String id, HttpServletRequest request);
	public abstract Task denyTask(String id, HttpServletRequest request);
	public abstract List<Task> getTasks(TaskType type);
	public abstract List<Task> getTasksByStatus(TaskType type, TaskStatus status);
}
