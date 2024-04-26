package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.tasks.enums.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskCommand {
	
	protected static final Logger logger = LoggerFactory.getLogger(TaskCommand.class);
	
	public void executeAccept(Task task) {
		task.setStatus(TaskStatus.accepted);
	}
	
	/**
	 * Deny the task
	 * @param task the task
	 * @return a DeniedMessage object or null if there is nothing to say
	 */
	public void executeDeny(Task task, String deniedMessage) {
		task.addToLog("Denied message: "+deniedMessage);
		task.setStatus(TaskStatus.denied);
	}
	
}
