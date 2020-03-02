package org.integratedmodelling.klab.hub.tasks;

public abstract class TaskCommand {
	
	public void executeAccept(Task task) {
		task.setStatus(TaskStatus.accepted);
	}
	
	public void executeDeny(Task task) {
		task.setStatus(TaskStatus.denied);
	}
	
}
