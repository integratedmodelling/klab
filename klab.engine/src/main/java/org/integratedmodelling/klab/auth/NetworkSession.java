package org.integratedmodelling.klab.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.data.rest.resources.NodeReference;

public class NetworkSession implements INetworkSessionIdentity {

    INodeIdentity parent;
    String token;
    List<INodeIdentity> nodes = new ArrayList<>();
    
    public NetworkSession(String token, List<NodeReference> nodes, Node node) {
        this.token = token;
        this.parent = node;
        for (NodeReference n : nodes) {
            this.nodes.add(new Node(n, Auth.INSTANCE.requirePartner(n.getOwningPartner())));
        }
    }

    @Override
    public String getId() {
        return token;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.NETWORK_SESSION;
    }


    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public INodeIdentity getParentIdentity() {
        return parent;
    }


    @Override
    public Collection<INodeIdentity> getNodes() {
        return nodes;
    }

}
