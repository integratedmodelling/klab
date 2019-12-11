package org.integratedmodelling.klab.hub.tasks.commands;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.repository.KlabGroupRepository;
import org.integratedmodelling.klab.hub.tasks.CreateGroupTask;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;

public class CreateGroupAccept implements TaskCommand {
	
	private final CreateGroupTask task;
	
	private final KlabGroupRepository groupRepository;
	
	public CreateGroupAccept(CreateGroupTask task, 
			KlabGroupRepository groupRepository) {
		this.task = task;
		this.groupRepository = groupRepository;
	}

	@Override
	public CreateGroupTask execute() {
		KlabGroup group = task.getGroup();
		groupRepository.save(group);
		task.setStatus(TaskStatus.acceptedTask);
		return task;
	}

}
