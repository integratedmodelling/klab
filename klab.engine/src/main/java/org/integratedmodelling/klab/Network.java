package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.services.INetworkService;

public enum Network implements INetworkService {
	
	INSTANCE;

	@Override
	public IUserIdentity authenticate(ICertificate certificate) {
		// TODO Auto-generated method stub
		return null;
	}
}
