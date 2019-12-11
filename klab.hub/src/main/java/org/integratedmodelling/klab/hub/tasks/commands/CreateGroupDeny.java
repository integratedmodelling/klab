package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;

public class CreateGroupDeny implements TaskCommand {
	
	private final CreateGroupTask task;
	
	public CreateGroupDeny(CreateGroupTask task) {
		this.task = task;
	}

	@Override
	public CreateGroupTask execute() {
		task.setStatus(TaskStatus.denied);
		return task;
	}
}
