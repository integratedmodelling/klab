package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.hub.authentication.AuthenticationManager;
import org.integratedmodelling.klab.rest.NodeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetworkManager {

	@Autowired
	AuthenticationManager authenticationManager;
	
	private Set<INodeIdentity> onlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Set<INodeIdentity> offlineNodes = Collections.synchronizedSet(new HashSet<>());
	
	public Collection<NodeReference> getNodes(Set<String> groups) {
		Set<NodeReference> ret = new HashSet<>();
		for (INodeIdentity node : onlineNodes) {
			ret.add(createNodeReference(node, true));
		}
		for (INodeIdentity node : offlineNodes) {
			ret.add(createNodeReference(node, false));
		}
		return ret;
	}

	private NodeReference createNodeReference(INodeIdentity node, boolean isOnline) {
		NodeReference ret = new NodeReference();

		ret.setId(node.getId());
		ret.setOnline(isOnline);
		ret.getUrls().addAll(node.getUrls());
		ret.setPartner(authenticationManager.getHubReference().getPartner());

		// TODO more
		
		return ret;
	}

	public void notifyAuthorizedNode(INodeIdentity ret) {
		onlineNodes.add(ret);
	}

}
