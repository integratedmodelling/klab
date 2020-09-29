package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.joda.time.DateTime;

public enum NetworkManager {

	INSTANCE;
	
	private Set<INodeIdentity> onlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Set<INodeIdentity> offlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Map<String, NodeReference> allNodes = new HashMap<>();

	public Collection<NodeReference> getNodes(Set<Group> groups) {
		Set<NodeReference> ret = new HashSet<>();
		for (INodeIdentity node : onlineNodes) {
			node.getClient();
			ret.add(createNodeReference(node, true));
		}
		for (INodeIdentity node : offlineNodes) {
			node.getClient();
			ret.add(createNodeReference(node, false));
		}
		return ret;
	}

	private NodeReference createNodeReference(INodeIdentity node, boolean isOnline) {
		
		NodeReference ret = new NodeReference(node);
		HubReference hub = new GenerateHubReference().execute();
		ret.setOnline(isOnline);
		ret.setPartner(hub.getPartner());

		// TODO more

		return ret;
	}

	public void notifyAuthorizedNode(INodeIdentity ret, boolean online) {
		if(allNodes.containsKey(ret.getName()) && online == true) {
			if (offlineNodes.contains(ret)) {
				offlineNodes.remove(ret);
			}
			if (!onlineNodes.contains(ret)) {
				onlineNodes.add(ret);
			}
		} else {
			if(online) {
				allNodes.put(ret.getName(), createNodeReference(ret, online));
				onlineNodes.add(ret);
			} else {
				allNodes.put(ret.getName(), createNodeReference(ret, online));
				offlineNodes.add(ret);
			}
		}
	}

	public NodeReference getNode(String nodeName) {
		return allNodes.get(nodeName);
	}

}
