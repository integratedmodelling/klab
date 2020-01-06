package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.function.Function;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.rest.NodeReference.Permission;

/**
 * All functions related to k.LAB nodes. The network service is built at
 * authentication and refreshed periodically.
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
	 * Get all the nodes with the specified permission.
	 * 
	 * @param permission
	 * @param onlineOnly if true, only check nodes that are known to be online.
	 * @return
	 */
	Collection<INodeIdentity> getNodes(Permission permission, boolean onlineOnly);
	
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

	/**
	 * Get the (online) node identified by name, or null if offline or unknown.
	 * 
	 * @param name
	 * @return the node or null
	 */
	INodeIdentity getNode(String name);


}
