package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.NodeReference;

public enum Network implements INetworkService {

	INSTANCE;

	private static int MAX_THREADS = 10;

	Map<String, INodeIdentity> onlineNodes = Collections.synchronizedMap(new HashMap<>());
	Map<String, INodeIdentity> offlineNodes = Collections.synchronizedMap(new HashMap<>());

	Client client = Client.create();

	@Override
	public Collection<INodeIdentity> getNodes() {
		return new HashSet<>(onlineNodes.values());
	}

	/**
	 * Build the network based on the result of authentication.
	 * 
	 * @param authorization
	 */
	public void buildNetwork(EngineAuthenticationResponse authorization) {

		Client client = Client.create();

		for (NodeReference node : authorization.getNodes()) {
			Node identity = new Node(node, authorization.getUserData().getToken());

			try {
				mergeCapabilities(identity, client.with(authorization.getUserData().getToken())
						.get(chooseUrl(node.getUrls()) + API.CAPABILITIES, Capabilities.class));
				onlineNodes.put(identity.getName(), identity);
			} catch (Exception e) {
				offlineNodes.put(identity.getName(), identity);
			}
		}

	}

	private void mergeCapabilities(Node node, Capabilities capabilities) {
		// TODO Auto-generated method stub
		
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

}
