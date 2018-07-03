package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.klab.api.auth.INodeIdentity;

/**
 * All functions related to k.LAB network authentication, authorization and
 * usage.
 * 
 * @author Ferd
 *
 */
public interface INetworkService {

	/**
	 * Return all the nodes we can access after authentication. Should only
	 * return nodes that are online and transparently quarantine those that
	 * are not. Therefore the results may differ between calls.
	 * 
	 * @return the list of available and online nodes. 
	 * @throw IllegalStateException if called before authentication
	 */
	Collection<INodeIdentity> getNodes();

}
