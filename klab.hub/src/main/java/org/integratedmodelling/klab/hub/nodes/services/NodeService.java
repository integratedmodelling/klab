package org.integratedmodelling.klab.hub.nodes.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.api.MongoNode;

public interface NodeService {
	public abstract MongoNode createNode(MongoNode node);
	public abstract MongoNode updateNode(MongoNode node);
	public abstract void deleteNode(MongoNode node);
	public abstract Collection<MongoNode> getNodes();
	public abstract MongoNode getNode(String nodeName);
	public abstract void removeGroupFromNodes(String groupName);
}
