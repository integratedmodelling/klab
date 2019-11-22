package org.integratedmodelling.klab.hub.models.tasks;

import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("CreateGroupTask")
public class CreateGroupTask extends Task{

	@Reference
	KlabGroup group;
	
	public CreateGroupTask(String requestee) {
		super(requestee, TaskType.createGroup);
	}

	public KlabGroup getGroup() {
		return group;
	}

	public void setGroup(KlabGroup group) {
		this.group = group;
	}

}