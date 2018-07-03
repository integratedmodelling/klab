package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.services.INetworkService;
import org.integratedmodelling.klab.rest.AuthenticationResponse;

public enum Network implements INetworkService {

	INSTANCE;

	Map<String, INodeIdentity> onlineNodes = Collections.synchronizedMap(new HashMap<>());
	Map<String, INodeIdentity> offlineNodes = Collections.synchronizedMap(new HashMap<>());

	@Override
	public Collection<INodeIdentity> getNodes() {
		return new HashSet<>(onlineNodes.values());
	}

	/**
	 * Build the network based on the result of authentication.
	 * 
	 * @param authentication
	 */
	public void buildNetwork(AuthenticationResponse authentication) {
		// TODO Auto-generated method stub

	}
}
