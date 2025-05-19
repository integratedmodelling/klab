package org.integratedmodelling.klab.hub.nodes.commands;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;

public class GetINodeIdentity {

    private MongoNode node;
    private Hub hub;

    public GetINodeIdentity(MongoNode node) {
        super();
        this.node = node;
        this.hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
    }

    public INodeIdentity execute() {
        INodeIdentity ident = new Node(hub.getName() + "." + node.getName(), node.getType(), hub.getParentIdentity());
        ident.getUrls().add(node.getUrl());
        return ident;
    }

}
