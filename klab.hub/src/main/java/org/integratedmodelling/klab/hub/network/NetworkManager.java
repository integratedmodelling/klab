package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class NetworkManager {


	private Set<INodeIdentity> onlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Set<INodeIdentity> offlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Map<String, NodeReference> allNodes = new HashMap<>();

	public Collection<NodeReference> getNodes(Set<Group> groups) {
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
		
		Partner partner = Authentication.INSTANCE.getAuthenticatedIdentity(Partner.class);
		IdentityReference partnerIdentity = new IdentityReference();
		partnerIdentity.setId(partner.getId());
		partnerIdentity.setEmail(partner.getEmailAddress());
		partnerIdentity.setLastLogin(DateTime.now().toString());
		
		ret.setId(node.getName());
		ret.setOnline(isOnline);
		ret.getUrls().addAll(node.getUrls());
		ret.setPartner(partnerIdentity);

		// TODO more

		return ret;
	}

	public void notifyAuthorizedNode(INodeIdentity ret, boolean online) {
		onlineNodes.add(ret);
		allNodes.put(ret.getName(), createNodeReference(ret, online));
	}

	public NodeReference getNode(String nodeName) {
		return allNodes.get(nodeName);
	}

}
