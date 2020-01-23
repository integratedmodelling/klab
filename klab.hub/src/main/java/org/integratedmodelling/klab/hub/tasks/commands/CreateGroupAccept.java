package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;

public class CreateGroupAccept implements TaskCommand {
	
	private final CreateGroupTask task;
	
	private final MongoGroupRepository groupRepository;
	
	public CreateGroupAccept(CreateGroupTask task, 
			MongoGroupRepository groupRepository) {
		this.task = task;
		this.groupRepository = groupRepository;
	}

	@Override
	public CreateGroupTask execute() {
		MongoGroup group = task.getGroup();
		groupRepository.save(group);
		task.setStatus(TaskStatus.acceptedTask);
		return task;
	}

}
