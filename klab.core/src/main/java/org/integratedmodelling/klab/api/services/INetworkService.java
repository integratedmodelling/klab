package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IUserIdentity;

/**
 * All functions related to k.LAB network authentication, authorization and
 * usage.
 * 
 * @author Ferd
 *
 */
public interface INetworkService {

	/**
	 * Connect to the k.LAB network through the passed certificate. If the
	 * certificate is invalid, throw an authorization exception. If the network is
	 * available, the returned user identity will have a network session (< node <
	 * partner) as parent. If the network is unavailable, return a user identity
	 * that does not descend from a network session (but should still descend from a
	 * partner).
	 * 
	 * @param certificate
	 * @return user identity
	 */
	IUserIdentity authenticate(ICertificate certificate);

}
