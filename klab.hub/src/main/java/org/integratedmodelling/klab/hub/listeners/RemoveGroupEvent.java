package org.integratedmodelling.klab.hub.listeners;

import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.users.services.UserGroupEntryService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RemoveGroupEvent {
	
	private UserGroupEntryService userService;
	private NodeService nodeService;
	
	public RemoveGroupEvent(UserGroupEntryService userService,
			NodeService nodeService) {
		super();
		this.userService = userService;
		this.nodeService = nodeService;
	}
	
	@EventListener
	public void removeGroupFromUsers(RemoveGroup event) {
		userService.deleteGroupFromUsers(event.getGroupName());
		nodeService.removeGroupFromNodes(event.getGroupName());
	}
}
