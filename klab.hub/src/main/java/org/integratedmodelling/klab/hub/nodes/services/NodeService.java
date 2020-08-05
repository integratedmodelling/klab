package org.integratedmodelling.klab.hub.nodes.services;

import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.service.GenericHubService;

public interface NodeService extends GenericHubService<MongoNode>{

	void removeGroupFromNodes(String groupName);	
}
