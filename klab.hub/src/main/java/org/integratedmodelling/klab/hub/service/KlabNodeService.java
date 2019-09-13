package org.integratedmodelling.klab.hub.service;

import java.util.Collection;
import java.util.Optional;

import org.integratedmodelling.klab.hub.models.KlabNode;

public interface KlabNodeService {
	   public abstract void createNode(String nodename, KlabNode node);
	   public abstract void updateNodeGroups(String nodename, KlabNode node);
	   public abstract void updateNodeLastConnection(String nodename);
	   public abstract void deleteNode(String nodename);
	   public abstract Collection<KlabNode> getNodes();
	   public abstract Optional<KlabNode> getNode(String nodename);
}
