package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INetworkService;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;

public enum Network implements INetworkService {

    INSTANCE;

    public static final int NETWORK_CHECK_INTERVAL_SECONDS = 180;
    private static final int MAX_AUTHENTICATION_ATTEMPTS = 3;

    private static int MAX_THREADS = 10;

    private HubReference hub;
    Map<String, INodeIdentity> onlineNodes = Collections.synchronizedMap(new HashMap<>());
    Map<String, INodeIdentity> offlineNodes = Collections.synchronizedMap(new HashMap<>());

    private Timer timer = new Timer("network checking");
    private int authenticationAttempts = 0;

    private Network() {
        Services.INSTANCE.registerService(this, INetworkService.class);
    }

    @Override
    public Collection<INodeIdentity> getNodes() {
        return new HashSet<>(onlineNodes.values());
    }

    @Override
    public INodeIdentity getNode(String name) {
        return onlineNodes.get(name);
    }

    @Override
    public Collection<INodeIdentity> getNodes(Permission permission, boolean onlineOnly) {
        List<INodeIdentity> ret = new ArrayList<>();
        for (String s : onlineNodes.keySet()) {
            if (onlineNodes.get(s).getPermissions().contains(permission)) {
                ret.add(onlineNodes.get(s));
            }
        }
        if (!onlineOnly) {
            for (String s : offlineNodes.keySet()) {
                if (offlineNodes.get(s).getPermissions().contains(permission)) {
                    ret.add(offlineNodes.get(s));
                }
            }
        }
        return ret;
    }

    /**
     * Build the network based on the result of authentication.
     * 
     * @param authorization
     */
    public void buildNetwork(EngineAuthenticationResponse authorization) {

        this.hub = authorization.getHub();

        for (NodeReference node : authorization.getNodes()) {
            Node identity = new Node(node, authorization.getUserData().getToken());
            try {
                mergeCapabilities(identity, identity.getClient().get(API.CAPABILITIES, NodeCapabilities.class));
                onlineNodes.put(identity.getName(), identity);
                if (Services.INSTANCE.getService(IResourceService.class) != null) {
                    Services.INSTANCE.getService(IResourceService.class).getPublicResourceCatalog().updateNode(identity);
                }
            } catch (Exception e) {
                offlineNodes.put(identity.getName(), identity);
            }
        }

        // schedule the network check service
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                checkNetwork();
            }
        }, NETWORK_CHECK_INTERVAL_SECONDS * 1000, NETWORK_CHECK_INTERVAL_SECONDS * 1000);

    }

    // these return descriptors to communicate to clients
    public HubReference getHub() {
        return this.hub;
    }

    private void mergeCapabilities(Node node, NodeCapabilities capabilities) {
        if (capabilities.isAcceptSubmission()) {
            node.getPermissions().add(Permission.PUBLISH);
        }
        if (capabilities.isAcceptQueries()) {
            node.getPermissions().add(Permission.QUERY);
        }
        for (ResourceAdapterReference adapter : capabilities.getResourceAdapters()) {
            node.getAdapters().add(adapter.getName());
        }
        for (AuthorityReference authority : capabilities.getAuthorities()) {
            node.getAuthorities().put(authority.getName(), authority);
        }
        node.getCatalogIds().addAll(capabilities.getResourceCatalogs());
        node.getNamespaceIds().addAll(capabilities.getResourceNamespaces());
        node.getResources().addAll(capabilities.getResourceUrns());
        node.setVersion(capabilities.getBuild());
        // FIXME use proper field
        node.setUptime(capabilities.getRefreshFrequencyMillis());
        node.setOnline(true);
    }

    @Override
    public <T, K> T broadcastGet(String endpoint, Class<? extends K> individualResponseType, Function<Collection<K>, T> merger,
            IMonitor monitor, Object... urlVariables) {

        Collection<Callable<K>> tasks = new ArrayList<>();
        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        for (INodeIdentity node : onlineNodes.values()) {
            tasks.add(new Callable<K>(){
                @Override
                public K call() throws Exception {
                    return node.getClient().onBehalfOf(session.getUser()).get(endpoint, individualResponseType, urlVariables);
                }
            });
        }

        ExecutorService executor = Executors.newFixedThreadPool((onlineNodes.size() + offlineNodes.size()) > MAX_THREADS
                ? MAX_THREADS
                : (onlineNodes.size() + offlineNodes.size()));

        int failures = 0;
        List<K> retvals = new ArrayList<>();
        List<Future<K>> results;
        try {
            results = executor.invokeAll(tasks);
            for (Future<K> result : results) {
                try {
                    retvals.add(result.get());
                } catch (Exception e) {
                    failures++;
                }
            }
        } catch (Exception e) {
            throw new KlabIOException(e);
        }

        if (failures > 0) {
            String message = "broadcasting to network resulted in " + failures + " failed calls out of " + onlineNodes.size();
            if (failures >= onlineNodes.size()) {
                monitor.error(message);
            } else {
                monitor.warn(message);
            }
        }

        executor.shutdown();

        return merger.apply(retvals);
    }

    @Override
    public <T, K, V> T broadcastPost(String endpoint, V request, Class<? extends K> individualResponseType,
            Function<Collection<K>, T> merger, IMonitor monitor) {

        Collection<Callable<K>> tasks = new ArrayList<>();
        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        for (INodeIdentity node : onlineNodes.values()) {
            tasks.add(new Callable<K>(){
                @Override
                public K call() throws Exception {
                    return node.getClient().onBehalfOf(session.getUser()).post(endpoint, request, individualResponseType);
                }
            });
        }

        ExecutorService executor = Executors.newFixedThreadPool((onlineNodes.size() + offlineNodes.size()) > MAX_THREADS
                ? MAX_THREADS
                : (onlineNodes.size() + offlineNodes.size()));

        int failures = 0;
        List<K> retvals = new ArrayList<>();
        List<Future<K>> results;
        try {
            results = executor.invokeAll(tasks);
            for (Future<K> result : results) {
                try {
                    retvals.add(result.get());
                } catch (Exception e) {
                    failures++;
                }
            }
        } catch (Exception e) {
            throw new KlabIOException(e);
        }

        if (failures > 0) {
            String message = "broadcasting to network resulted in " + failures + " failed calls out of " + onlineNodes.size();
            if (failures >= onlineNodes.size()) {
                monitor.error(message);
            } else {
                monitor.warn(message);
            }
        }

        executor.shutdown();

        return merger.apply(retvals);
    }

    protected String chooseUrl(Collection<String> urls) {
        // TODO periodically ping URLs and save the first that responds
        return urls.iterator().next();
    }

    Map<String, Long> nodeWarnings = Collections.synchronizedMap(new HashMap<>());

    protected synchronized void checkNetwork() {

        List<INodeIdentity> moveOnline = new ArrayList<>();
        List<INodeIdentity> moveOffline = new ArrayList<>();
        for (INodeIdentity node : onlineNodes.values()) {
            try {
                ((Node) node).mergeCapabilities(node.getClient().get(API.CAPABILITIES, NodeCapabilities.class));
            } catch (KlabAuthorizationException e) {
              reauthenticate();
            } catch (Exception e) {
                moveOffline.add(node);
                if (Services.INSTANCE.getService(IResourceService.class) != null) {
                    Services.INSTANCE.getService(IResourceService.class).getPublicResourceCatalog().removeNode(node);
                }
                Logging.INSTANCE.info("node " + node.getName() + " went offline");
            }
        }
        for (INodeIdentity node : offlineNodes.values()) {
            try {
                NodeCapabilities capabilities = node.getClient().get(API.CAPABILITIES, NodeCapabilities.class);
                ((Node) node).mergeCapabilities(capabilities);
                moveOnline.add(node);
                if (Services.INSTANCE.getService(IResourceService.class) != null) {
                    Services.INSTANCE.getService(IResourceService.class).getPublicResourceCatalog().updateNode(node);
                }
                Logging.INSTANCE.info("node " + node.getName() + " went online");
            } catch (KlabAuthorizationException e) {
              reauthenticate();
            } catch (Exception e) {
                Long l = nodeWarnings.get(node.getName());
                if (l == null || (System.currentTimeMillis() - l) > (1000 * 60 * 10)) {
                    Logging.INSTANCE.debug("Exception while checking offline node " + node.getName() + ": " + e.getMessage());
                    nodeWarnings.put(node.getName(), System.currentTimeMillis());
                }
            }
        }

        for (INodeIdentity node : moveOnline) {
            offlineNodes.remove(node.getName());
            onlineNodes.put(node.getName(), node);
            ((Node) node).setOnline(true);
        }
        for (INodeIdentity node : moveOffline) {
            onlineNodes.remove(node.getName());
            offlineNodes.put(node.getName(), node);
            ((Node) node).setOnline(false);
        }
    }

	private void reauthenticate() {
		if (authenticationAttempts > MAX_AUTHENTICATION_ATTEMPTS) {
			return;
		}

		Logging.INSTANCE.warn("Attempting re-authentication");
		authenticationAttempts++;

		if (Authentication.INSTANCE.reauthenticate() != null) {
			checkNetwork();
		}

	}

    @Override
    public INodeIdentity getNodeForResource(Urn urn) {
        if (urn.isUniversal()) {
            return chooseNode(getNodesWithAdapter(urn.getCatalog()));
        }
        return chooseNode(getOnlineNodes(
                Services.INSTANCE.getService(IResourceService.class).getPublicResourceCatalog().getNodes(urn.getUrn())));
    }

    @Override
    public List<INodeIdentity> getNodesForAuthority(String authority) {
        return chooseNodes(getNodesWithAuthority(authority));
    }

    private Collection<INodeIdentity> getOnlineNodes(Collection<String> nodes) {
        List<INodeIdentity> ret = new ArrayList<>();
        for (String node : nodes) {
            INodeIdentity n = onlineNodes.get(node);
            if (n != null) {
                ret.add(n);
            }
        }
        return ret;
    }

    /**
     * Sort the passed nodes by increasing load factor or decreasing capacity. TBD.
     * 
     * @param nodes
     * @return
     */
    private List<INodeIdentity> chooseNodes(Collection<INodeIdentity> nodes) {
        // TODO sort by increasing load factor
        return new ArrayList<>(nodes);
    }

    private INodeIdentity chooseNode(Collection<INodeIdentity> nodesWithAdapter) {
        // TODO use load factor and/or some intelligent criterion
        return nodesWithAdapter.isEmpty() ? null : nodesWithAdapter.iterator().next();
    }

    public Collection<INodeIdentity> getNodesWithAdapter(String adapter) {
        List<INodeIdentity> ret = new ArrayList<>();
        for (INodeIdentity node : onlineNodes.values()) {
            if (node.getAdapters().contains(adapter)) {
                ret.add(node);
            }
        }
        return ret;
    }

    public Collection<INodeIdentity> getNodesWithAuthority(String authority) {
        List<INodeIdentity> ret = new ArrayList<>();
        for (INodeIdentity node : onlineNodes.values()) {
            if (node.getAuthorities().containsKey(authority)) {
                ret.add(node);
            }
        }
        return ret;
    }
}
