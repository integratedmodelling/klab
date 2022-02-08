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

import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INetworkService;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.commands.GenerateHubReference;
import org.integratedmodelling.klab.hub.commands.GetINodeIdentity;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;

public enum NodeNetworkManager implements INetworkService  {
    
    INSTANCE;
    
    private HubReference hub;
    
    private NodeService nodeService;
    
    Map<String, INodeIdentity> nodes = Collections.synchronizedMap(new HashMap<>());
    
    public static final int NODE_CHECK_INTERVAL_SECONDS = 20;
    private Timer timer;
    
    private NodeNetworkManager() {
        Services.INSTANCE.registerService(this, INetworkService.class);
        this.hub = new GenerateHubReference().execute();
    }
    
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
        if (timer == null) {
            timer = new Timer("Node repository check");
        } else {
            timer.cancel();
        }   
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateNodes();
            }
        }, NODE_CHECK_INTERVAL_SECONDS * 1000, NODE_CHECK_INTERVAL_SECONDS * 1000);
        updateNodes();
    }
    
    private void updateNodes() {
        if (nodeService == null) {
            return;
        }
        Collection<MongoNode> mongoNodes = nodeService.getAll();
        nodes.clear();
        for  (MongoNode node: mongoNodes) {
            INodeIdentity nodeIdentity = new GetINodeIdentity(node).execute();
            nodes.putIfAbsent(nodeIdentity.getName(), nodeIdentity);
        }
    }

    @Override
    public Collection<INodeIdentity> getNodes() {
        return nodes.values();
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
        
        synchronized (nodes) {
            if(nodes.containsKey(name)) {
                return nodes.get(name);
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
        synchronized (nodes) {
            for (INodeIdentity node : nodes.values()) {
                NodeReference ret = new NodeReference(node, false);
                ret.setPartner(this.hub.getPartner());
                refs.add(ret);
            }
        }
        return refs;
    }
}
