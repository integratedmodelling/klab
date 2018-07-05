package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.rest.NodeReference;
import org.springframework.stereotype.Component;

@Component
public class NetworkManager {

	private Set<NodeReference> onlineNodes = Collections.synchronizedSet(new HashSet<>());
	private Set<NodeReference> offlineNodes = Collections.synchronizedSet(new HashSet<>());
	
	public Collection<NodeReference> getNodes(Set<String> groups) {
		Set<NodeReference> ret = new HashSet<>(onlineNodes);
		ret.addAll(offlineNodes);
		return ret;
	}

}
