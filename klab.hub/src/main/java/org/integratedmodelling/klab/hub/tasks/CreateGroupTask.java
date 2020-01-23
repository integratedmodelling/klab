package org.integratedmodelling.klab.hub.tasks;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("CreateGroupTask")
public class CreateGroupTask extends Task{

	@Reference
	MongoGroup group;
	
	public CreateGroupTask(String requestee) {
		super(requestee, TaskType.createGroup);
	}

	public MongoGroup getGroup() {
		return group;
	}

	public void setGroup(MongoGroup group) {
		this.group = group;
	}

}