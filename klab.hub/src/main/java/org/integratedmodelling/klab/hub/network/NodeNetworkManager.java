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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INetworkService;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
        return Stream.concat(onlineNodes.values().stream(), offlineNodes.values().stream())
                .collect(Collectors.toUnmodifiableList());
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
		NodeReference ret = new NodeReference(node);
		ret.setOnline(isOnline);
		ret.setPartner(this.hub.getPartner());
		return ret;
	}
	
    private void retrieveNodeCapabilities(INodeIdentity node) {
        if (!(node instanceof Node)) {
            return;
        }
        if (!((Node)node).getAdapters().isEmpty()) {
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        ((Node)node).getUrls().forEach(url -> {
            try {
                ResponseEntity<NodeCapabilities> responseEntity = restTemplate.getForEntity(url + API.CAPABILITIES, NodeCapabilities.class);
                ((Node)node).mergeCapabilities(responseEntity.getBody());
                return;
            } catch (RestClientException e) {
                // no need to manage the exception, just try the next URL
            }
        });
    }

    public void notifyAuthorizedNode(INodeIdentity node, boolean online) {
        node.getClient();
        if (getNodes().contains(node)) {
            if (online) {
                setNodeOnline(node);
            } else {
                setNodeOffline(node);
            }
        } else {
            if (online) {
                synchronized (onlineNodes) {
                    onlineNodes.putIfAbsent(node.getName(), node);
                }
            } else {
                synchronized (offlineNodes) {
                    offlineNodes.putIfAbsent(node.getName(), node);
                }
            }
        }
    }
	
	private void checkNodes() {
		Logging.INSTANCE.info("Checking nodes");
		Collection<NodeReference> nodes = getNodeReferences();
		for (NodeReference ref : nodes) {
			Node node = new Node(ref,null);
			node.getClient();
            retrieveNodeCapabilities(node);
			try {
				if(node.ping()) {
					Logging.INSTANCE.info("Node Online: " + node.getName());
					setNodeOnline(node);
				} else {
					Logging.INSTANCE.info("Node Offline: " + node.getName());
					setNodeOffline(node);
				}
			} catch (ResourceAccessException e) {
				Logging.INSTANCE.info("Node Offline: " + node.getName());
				setNodeOffline(node);
			}
		}
	}

    private void setNodeOffline(INodeIdentity iNode) {
        synchronized (onlineNodes) {
            onlineNodes.remove(iNode.getName());
        }
        synchronized (offlineNodes) {
            offlineNodes.put(iNode.getName(), iNode);
        }
    }

    private void setNodeOnline(INodeIdentity node) {
        synchronized (offlineNodes) {
            offlineNodes.remove(node.getName());
        }
        synchronized (onlineNodes) {
            onlineNodes.put(node.getName(), node);
        }
    }
}
