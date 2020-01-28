package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.tasks.Task;

public interface TaskCommand {
	
	public void executeAccept(Task task);
	public void executeDeny(Task task);
	
}
