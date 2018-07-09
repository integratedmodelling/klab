package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.function.Function;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * All functions related to k.LAB network authentication, authorization and
 * usage.
 * 
 * @author Ferd
 *
 */
public interface INetworkService {

	/**
	 * Return all the nodes we can access after authentication. Should only return
	 * nodes that are online and transparently quarantine those that are not.
	 * Therefore the results may differ between calls.
	 * 
	 * @return the list of available and online nodes.
	 * @throw IllegalStateException if called before authentication
	 */
	Collection<INodeIdentity> getNodes();

	/**
	 * Submit a GET request to all nodes in parallel and merge the results when all
	 * have returned, failed or timed out.
	 * 
	 * @param individualResponseType
	 * @param merger
	 * @param monitor
	 * @param urlVariables
	 * @return the result of merging through the merger
	 */
	<T, K> T broadcastGet(Class<? extends K> individualResponseType, Function<Collection<K>, T> merger,
			IMonitor monitor, Object... urlVariables);

	/**
	 * Submit a POST request to all nodes in parallel and merge the results when all
	 * have returned, failed or timed out.
	 * 
	 * @param request
	 * @param individualResponseType
	 * @param merger
	 * @param monitor
	 * @return the result of merging through the merger
	 */
	<T, K, V> T broadcastPost(V request, Class<? extends K> individualResponseType, Function<Collection<K>, T> merger,
			IMonitor monitor);

}
