package org.integratedmodelling.klab.api.auth;

import org.integratedmodelling.klab.api.knowledge.IWorldview;

public interface ICertificate {

	/**
	 * Create the worldview workspace for this identity and return it (unloaded and
	 * not initialized).
	 * 
	 * @return
	 */
	IWorldview getWorldview();

	/**
	 * A certificate defines a 'root' identity: a IPartnerIdentity, INodeIdentity or
	 * IUserIdentity. Servers and engines start by reading a certificate.
	 * 
	 * @return
	 */
	IIdentity getIdentity();
}
