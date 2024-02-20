package org.integratedmodelling.klab.hub.users.listeners;

import org.springframework.context.ApplicationEvent;

public class RemoveGroup extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2137361508052117106L;
	
	private String groupName;

	public RemoveGroup(Object source, String groupName) {
		super(source);
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

}
