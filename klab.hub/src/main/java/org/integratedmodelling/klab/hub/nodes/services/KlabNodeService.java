package org.integratedmodelling.klab.hub.nodes.services;

import java.util.Collection;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.MongoNode;

public interface KlabNodeService {
	   public abstract void createNode(String nodename, MongoNode node);
	   public abstract void updateNodeGroups(String nodename, MongoNode node);
	   public abstract void updateNodeLastConnection(String nodename);
	   public abstract void deleteNode(String nodename);
	   public abstract Collection<MongoNode> getNodes();
	   public abstract Optional<MongoNode> getNode(String nodename);
}
