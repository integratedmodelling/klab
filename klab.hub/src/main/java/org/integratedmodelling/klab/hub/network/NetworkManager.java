package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.hub.authentication.AuthenticationManager;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetworkManager {

	@Autowired
	AuthenticationManager authenticationManager;

	private Set<INodeIdentity> onlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Set<INodeIdentity> offlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Map<String, NodeReference> allNodes = new HashMap<>();

	public Collection<NodeReference> getNodes(Set<Group> groups) {
		Set<NodeReference> ret = new HashSet<>();
		for (INodeIdentity node : onlineNodes) {
			ret.add(createNodeReference(node, authenticationManager.getHubReference(), true));
		}
		for (INodeIdentity node : offlineNodes) {
			ret.add(createNodeReference(node, authenticationManager.getHubReference(), false));
		}
		return ret;
	}

	private NodeReference createNodeReference(INodeIdentity node, HubReference hub, boolean isOnline) {
		
		NodeReference ret = new NodeReference();

		ret.setId(node.getId());
		ret.setOnline(isOnline);
		ret.getUrls().addAll(node.getUrls());
		ret.setPartner(authenticationManager.getHubReference().getPartner());

		// TODO more

		return ret;
	}

	public void notifyAuthorizedNode(INodeIdentity ret, HubReference authorizingHub, boolean online) {
		onlineNodes.add(ret);
		allNodes.put(ret.getName(), createNodeReference(ret, authorizingHub, online));
	}

	public NodeReference getNode(String nodeName) {
		return allNodes.get(nodeName);
	}

}
