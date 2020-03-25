package org.integratedmodelling.klab.hub.nodes.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.commands.CreateMongoNode;
import org.integratedmodelling.klab.hub.commands.GetMongoNodeByName;
import org.integratedmodelling.klab.hub.commands.UpdateMongoNode;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl implements NodeService {
	
	public NodeServiceImpl(MongoNodeRepository nodeRepository) {
		super();
		this.nodeRepository = nodeRepository;
	}

	private MongoNodeRepository nodeRepository;

	@Override
	public MongoNode createNode(MongoNode node) {
		if (!nodeExists(node.getName())) {
			return new CreateMongoNode(node, nodeRepository).execute();
		} else {
			throw new BadRequestException("Node by that name already exists");
		}
	}

	@Override
	public MongoNode updateNode(MongoNode node) {
		return new UpdateMongoNode(node, nodeRepository).execute();
	}

	@Override
	public void deleteNode(MongoNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<MongoNode> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MongoNode getNode(String nodeName) {
		return new GetMongoNodeByName(nodeName, nodeRepository).execute();
	}
	
	private Boolean nodeExists(String nodeName) {
		return nodeRepository.findByNameIgnoreCase(nodeName).isPresent();
	}

}
