package org.integratedmodelling.klab.hub.nodes.services;

import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.services.GenericHubService;

public interface NodeService extends GenericHubService<MongoNode>{

	void removeGroupFromNodes(String groupName);	
}
