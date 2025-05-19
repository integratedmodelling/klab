package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.auth.IIdentity;
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
     * Return all the v.0.1. services we can access after authentication. Should only return
     * services that are online and transparently quarantine those that are not.
     * Therefore the results may differ between calls.
     * 
     * @return the list of available and online services.
     * @throw IllegalStateException if called before authentication
     */
    Collection<INodeIdentity> getServices();

    /**
     * Return all the v.0.1. services we can access after authentication with a 
     * certain type. Should only return
     * services that are online and transparently quarantine those that are not.
     * Therefore the results may differ between calls.
     * 
     * @return the list of available and online services.
     * @throw IllegalStateException if called before authentication
     */
    Collection<INodeIdentity> getServices(IIdentity.Type type);

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
    <T, K> T broadcastGet(String endpoint, Class< ? extends K> individualResponseType, Function<Collection<K>, T> merger,
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
    <T, K, V> T broadcastPost(String endpoint, V request, Class< ? extends K> individualResponseType,
            Function<Collection<K>, T> merger, IMonitor monitor);

    /**
     * Get the (online) node identified by name, or null if offline or unknown.
     * 
     * @param name
     * @return the node or null
     */
    INodeIdentity getNode(String name);

    /**
     * Get the (online) v.1.0 service identified by name, or null if offline or unknown.
     * 
     * @param name
     * @return the node or null
     */
    INodeIdentity getService(String name);

    /**
     * Choose the best online node at the time of calling to provide data from the
     * passed URN. This is only called if the URN cannot be handled locally. Must
     * handle "universal" URNs starting with klab: and having the adapter as
     * catalog, and non-local URNs; each may be served by 0+ nodes, and the choice
     * should be based on the current load factors, versions (which may be added to
     * the URN) and mirroring options.
     * 
     * TODO substitute with a sorted list of nodes, closest/least-loaded first, so
     * that we can try the others if the best choice fails.
     * 
     * @param urn
     * @return
     */
    INodeIdentity getNodeForResource(Urn urn);

    /**
     * Get a list of nodes that provide the passed authority, closest/least-loaded
     * first.
     * 
     * @param authority
     * @return
     */
    List<INodeIdentity> getNodesForAuthority(String authority);

}
