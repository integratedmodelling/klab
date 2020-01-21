package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.nodes.MongoNode;

public class GetINodeIdentity {

	private MongoNode node;
	
	
	public GetINodeIdentity(MongoNode node) {
		super();
		this.node = node;
	}

	public INodeIdentity execute() {
		return new Node(HubAuthenticationManager.INSTANCE.getHubName() + "." + node.getNode(), HubAuthenticationManager.INSTANCE.getRootIdentity());
	}

}
