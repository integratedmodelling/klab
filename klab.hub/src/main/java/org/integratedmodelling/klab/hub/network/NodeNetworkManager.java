package org.integratedmodelling.klab.hub.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INetworkService;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.springframework.web.client.ResourceAccessException;

public enum NodeNetworkManager implements INetworkService {
	
	INSTANCE;
	
	private HubReference hub;
	
	Map<String, INodeIdentity> onlineNodes = Collections.synchronizedMap(new HashMap<>());
	Map<String, INodeIdentity> offlineNodes = Collections.synchronizedMap(new HashMap<>());
	
	public static final int NODE_CHECK_INTERVAL_SECONDS = 10;
	private Timer timer = new Timer("Node check");
	
	private NodeNetworkManager() {
		Services.INSTANCE.registerService(this, INetworkService.class);
		
		this.hub = new GenerateHubReference().execute();
		
		// schedule the network check service
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkNodes();
			}
		}, NODE_CHECK_INTERVAL_SECONDS * 1000, NODE_CHECK_INTERVAL_SECONDS * 1000);
	}

	@Override
	public Collection<INodeIdentity> getNodes() {
		Collection<INodeIdentity> nodes = new HashSet<>(onlineNodes.values());
		nodes.addAll(offlineNodes.values());
		return nodes;
	}

	@Override
	public Collection<INodeIdentity> getNodes(Permission permission, boolean onlineOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, K> T broadcastGet(String endpoint, Class<? extends K> individualResponseType,
			Function<Collection<K>, T> merger, IMonitor monitor, Object... urlVariables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, K, V> T broadcastPost(String endpoint, V request, Class<? extends K> individualResponseType,
			Function<Collection<K>, T> merger, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized INodeIdentity getNode(String name) {
		
		synchronized (onlineNodes) {
			if(onlineNodes.containsKey(name)) {
				return onlineNodes.get(name);
			}
		}
		
		synchronized (onlineNodes) {
			if(offlineNodes.containsKey(name)) {
				return offlineNodes.get(name);
			}
		}
		
		return null;
	}

	
	@Override
	public INodeIdentity getNodeForResource(Urn urn) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<INodeIdentity> getNodesForAuthority(String authority) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<NodeReference> getNodeReferences() {
		Collection<NodeReference> refs = new HashSet<>();
		
		synchronized (onlineNodes) {
			for (INodeIdentity node : onlineNodes.values()) {
				refs.add(createNodeReference(node, true));
			}
		}
		
		synchronized (offlineNodes) {
			for (INodeIdentity node : offlineNodes.values()) {
				refs.add(createNodeReference(node, false));
			}
		}
		
		return refs;
	}
	
	
	private NodeReference createNodeReference(INodeIdentity node, boolean isOnline) {
		node.getClient();
		NodeReference ret = new NodeReference(node);
		ret.setOnline(isOnline);
		ret.setPartner(this.hub.getPartner());
		return ret;
	}
	
	
	public void notifyAuthorizedNode(INodeIdentity ret, boolean online) {
		if(getNodes().contains(ret) && online == true) {
			//move the offline node to online node
			synchronized (offlineNodes) {
				if (offlineNodes.containsKey(ret.getName())) {
			    	offlineNodes.remove(ret.getName());
			    }
			}
			
			synchronized (onlineNodes) {
			    if (!onlineNodes.containsKey(ret.getName())) {
			    	onlineNodes.put(ret.getName(), ret);
			    } 
			 }
		} else if (getNodes().contains(ret) && online == false) {
			synchronized (onlineNodes) {
			    if (onlineNodes.containsKey(ret.getName())) {
			    	onlineNodes.remove(ret.getName());
			    } 
			 }
			synchronized (offlineNodes) {
				if (!offlineNodes.containsKey(ret.getName())) {
			    	offlineNodes.put(ret.getName(), ret);
			    }
			}
		} else {
			if(online) {
				synchronized (onlineNodes) {
					onlineNodes.put(ret.getName(), ret); 
				 }
			} else {
				synchronized (offlineNodes) {
					offlineNodes.put(ret.getName(), ret);
				}
			}
		}
	}
	
	private void checkNodes() {
		Logging.INSTANCE.info("Checking nodes");
		Collection<INodeIdentity> nodes = getNodes();
		for (INodeIdentity iNode : nodes) {
			try {
				//set thhe node to false so ping can return true;
				Node node = new Node(createNodeReference(iNode, false),null);
				if(node.ping()) {
					Logging.INSTANCE.info("Node Online: " + node.getName());
					setNodeOnline(node);
				} else {
					Logging.INSTANCE.info("Node Offline: " + iNode.getName());
					setNodeOffline(iNode);
				}
			} catch (ResourceAccessException e) {
				Logging.INSTANCE.info("Node Offline: " + iNode.getName());
				setNodeOffline(iNode);
			}
		}
	}

	private void setNodeOffline(INodeIdentity iNode) {
		synchronized (onlineNodes) {
			if(onlineNodes.containsKey(iNode.getName())) {
				onlineNodes.remove(iNode.getName());
			}
		}
		offlineNodes.putIfAbsent(iNode.getName(), iNode);
	}

	private void setNodeOnline(INodeIdentity node) {
		synchronized (offlineNodes) {
			if(offlineNodes.containsKey(node.getName())) {
				offlineNodes.remove(node.getName());
			}
		}
		onlineNodes.putIfAbsent(node.getName(), node);
	}
	
}
