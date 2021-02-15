package org.integratedmodelling.klab.hub.listeners;

import java.util.Collection;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.commands.GetINodeIdentity;
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
		Collection<MongoNode> nodes = nodeService.getAll();
		for  (MongoNode node: nodes) {
			INodeIdentity nodeIdentity = new GetINodeIdentity(node).execute();
			NodeNetworkManager.INSTANCE.notifyAuthorizedNode(nodeIdentity, false);
		}
	}

}
