package org.integratedmodelling.klab.hub.nodes.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.CreateMongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.GetMongoNodeByName;
import org.integratedmodelling.klab.hub.repository.MongoNodeRepository;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl implements NodeService {
	
	private MongoNodeRepository repository;

	@Override
	public MongoNode createNode(MongoNode node) {
		if (!nodeExists(node.getNode())) {
			return new CreateMongoNode(node, repository).execute();
		} else {
			throw new BadRequestException("Node by that name already exists");
		}
	}

	@Override
	public MongoNode updateNode(MongoNode node) {
		// TODO Auto-generated method stub
		return null;
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
		return new GetMongoNodeByName(nodeName, repository).execute();
	}
	
	private Boolean nodeExists(String nodeName) {
		return repository.findByNodeIgnoreCase(nodeName).isPresent();
	}

}
