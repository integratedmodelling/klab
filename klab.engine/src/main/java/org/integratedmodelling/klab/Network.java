package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.services.INetworkService;

public enum Network implements INetworkService {
	
	INSTANCE;

	@Override
	public IUserIdentity authenticate(ICertificate certificate) {
		// TODO find the hub in the certificate
		// TODO if not available and we have a list of mirrors, try the mirrors
		return null;
	}
	
	@Override
	public Collection<INodeIdentity> getNodes() {
		Set<INodeIdentity> ret = new HashSet<>();
		// TODO
		return ret;
	}
}
