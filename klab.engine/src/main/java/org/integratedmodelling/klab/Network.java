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
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INetworkService;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;

public enum Network implements INetworkService {

	INSTANCE;

	public static final int NETWORK_CHECK_INTERVAL_SECONDS = 180;

	private static int MAX_THREADS = 10;

	Map<String, INodeIdentity> onlineNodes = Collections.synchronizedMap(new HashMap<>());
	Map<String, INodeIdentity> offlineNodes = Collections.synchronizedMap(new HashMap<>());

	Client client = Client.create();

	private HubReference hub;
	private Map<String, NodeReference> nodes = Collections.synchronizedMap(new HashMap<>());

	private Timer timer = new Timer("Network checking");

	private Network() {
		Services.INSTANCE.registerService(this, INetworkService.class);
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				checkNetwork();
			}
		}, 30 * 1000, NETWORK_CHECK_INTERVAL_SECONDS * 1000);
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

		Client client = Client.create();

		this.hub = authorization.getHub();

		for (NodeReference node : authorization.getNodes()) {
			this.nodes.put(node.getId(), node);
			Node identity = new Node(node, authorization.getUserData().getToken());
			try {
				mergeCapabilities(identity, client.with(authorization.getUserData().getToken())
						.get(chooseUrl(node.getUrls()) + API.CAPABILITIES, NodeCapabilities.class));
				onlineNodes.put(identity.getName(), identity);
			} catch (Exception e) {
				offlineNodes.put(identity.getName(), identity);
			}
		}

	}

	// these return descriptors to communicate to clients
	public HubReference getHub() {
		return this.hub;
	}

	public Collection<NodeReference> getNodeDescriptors() {
		return this.nodes.values();
	}

	public NodeReference getNodeDescriptor(String id) {
		return this.nodes.get(id);
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
	}

	@Override
	public <T, K> T broadcastGet(Class<? extends K> individualResponseType, Function<Collection<K>, T> merger,
			IMonitor monitor, Object... urlVariables) {

		Collection<Callable<K>> tasks = new ArrayList<>();
		for (INodeIdentity node : onlineNodes.values()) {
			tasks.add(new Callable<K>() {
				@Override
				public K call() throws Exception {
					return client.with(node).get(chooseUrl(node.getUrls()), individualResponseType,
							makeParameterMap(urlVariables));
				}
			});
		}

		ExecutorService executor = Executors
				.newFixedThreadPool((onlineNodes.size() + offlineNodes.size()) > MAX_THREADS ? MAX_THREADS
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
			String message = "broadcasting to network resulted in " + failures + " failed calls out of "
					+ onlineNodes.size();
			if (failures >= onlineNodes.size()) {
				monitor.error(message);
			} else {
				monitor.warn(message);
			}
		}

		executor.shutdown();

		return merger.apply(retvals);
	}

	private Map<String, ?> makeParameterMap(Object[] urlVariables) {
		Map<String, Object> ret = new HashMap<>();
		for (int i = 0; i < urlVariables.length; i++) {
			ret.put(urlVariables[i].toString(), urlVariables[++i]);
		}
		return ret;
	}

	public <T, K, V> T broadcastPost(V request, Class<? extends K> individualResponseType,
			Function<Collection<K>, T> merger, IMonitor monitor) {

		Collection<Callable<K>> tasks = new ArrayList<>();
		for (INodeIdentity node : onlineNodes.values()) {
			tasks.add(new Callable<K>() {
				@Override
				public K call() throws Exception {
					return client.with(node).post(chooseUrl(node.getUrls()), request, individualResponseType);
				}
			});
		}

		ExecutorService executor = Executors
				.newFixedThreadPool((onlineNodes.size() + offlineNodes.size()) > MAX_THREADS ? MAX_THREADS
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
			String message = "broadcasting to network resulted in " + failures + " failed calls out of "
					+ onlineNodes.size();
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

	protected void checkNetwork() {
		System.out.println("Checking network");
		List<INodeIdentity> moveOnline = new ArrayList<>();
		List<INodeIdentity> moveOffline = new ArrayList<>();
		for (INodeIdentity node : onlineNodes.values()) {
			try {
				NodeCapabilities capabilities = node.getClient().get(API.CAPABILITIES, NodeCapabilities.class);
			} catch (Exception e) {
				Logging.INSTANCE.info("node " + node.getName() + " went offline");
			}
		}
		for (INodeIdentity node : offlineNodes.values()) {
			try {
				NodeCapabilities capabilities = node.getClient().get(API.CAPABILITIES, NodeCapabilities.class);
				Logging.INSTANCE.info("node " + node.getName() + " went online");
			} catch (Exception e) {

			}
		}
	}

	@Override
	public INodeIdentity getNodeForResource(Urn urn) {
		if (urn.isUniversal()) {
			return chooseNode(getNodesWithAdapter(urn.getCatalog()));
		}
		return null;
	}

	private INodeIdentity chooseNode(Collection<INodeIdentity> nodesWithAdapter) {
		// TODO use load factor and/or some intelligent criterion
		return nodesWithAdapter.isEmpty() ? null : nodesWithAdapter.iterator().next();
	}

	private Collection<INodeIdentity> getNodesWithAdapter(String adapter) {
		List<INodeIdentity> ret = new ArrayList<>();
		for (INodeIdentity node : onlineNodes.values()) {
			if (node.getAdapters().contains(adapter)) {
				ret.add(node);
			}
		}
		return ret;
	}
}
