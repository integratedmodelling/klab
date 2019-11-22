package org.integratedmodelling.klab.hub.service;

import java.util.List;

import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.tasks.Task;
import org.integratedmodelling.klab.hub.models.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;

public interface TaskService {
	public abstract Task createTask(String requestee, Class<? extends Task> taskType);
	public abstract Task createTask(String requestee, Class<? extends Task> taskType, Role requiredRole);
	public abstract Task saveTask(Task task);
	public abstract Task changeTaskStatus(String id, TaskStatus status);
	public abstract void deleteTask(String id);
	public abstract List<Task> getTasks();
	public abstract Task getTask(String id);
	public abstract List<Task> getPendingTasks();
	public abstract Task getGroupRequestTaskByToken(ClickbackToken token);
}
