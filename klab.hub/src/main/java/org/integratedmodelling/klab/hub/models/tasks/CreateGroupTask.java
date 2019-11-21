package org.integratedmodelling.klab.hub.models.tasks;

import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.hub.models.KlabGroup;

public class CreateGroupTask extends Task{

	@NotEmpty
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