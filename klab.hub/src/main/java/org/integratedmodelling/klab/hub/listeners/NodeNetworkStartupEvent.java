package org.integratedmodelling.klab.hub.listeners;

import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NodeNetworkStartupEvent {
	
	private NodeService nodeService;
	
	public NodeNetworkStartupEvent(NodeService nodeService) {
		super();
		this.nodeService = nodeService;
	}
	
	@Async
	@EventListener
	public void startup(NodeNetworkStartupReady event) {
		NodeNetworkManager.INSTANCE.setNodeService(nodeService);
	}

}
