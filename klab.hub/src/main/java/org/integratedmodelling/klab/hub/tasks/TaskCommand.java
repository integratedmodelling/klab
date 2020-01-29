package org.integratedmodelling.klab.hub.tasks;

public interface TaskCommand {
	
	public void executeAccept(Task task);
	public void executeDeny(Task task);
	
}
