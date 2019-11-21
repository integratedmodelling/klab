package org.integratedmodelling.klab.hub.service;

import java.util.List;
import org.integratedmodelling.klab.hub.models.Task;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.TaskStatus;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;

public interface TaskService {
	   public abstract void createTask(String requestee, ClickbackToken token);
	   public abstract void createTask(String requestee, ClickbackToken token, Role requiredRole);
	   public abstract Task getTaskByToken(ClickbackToken token);
	   public abstract Task changeTaskStatus(String id, TaskStatus status);
	   public abstract void deleteTask(String id);
	   public abstract List<Task> getTasks();
	   public abstract Task getTask(String id);
	   public abstract List<Task> getPendingTasks();
}
