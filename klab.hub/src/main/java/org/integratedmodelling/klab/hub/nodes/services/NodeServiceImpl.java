package org.integratedmodelling.klab.hub.nodes.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.nodes.commands.CreateMongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.DeleteMongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.GetMongoNodeById;
import org.integratedmodelling.klab.hub.nodes.commands.GetMongoNodeByName;
import org.integratedmodelling.klab.hub.nodes.commands.GetMongoNodes;
import org.integratedmodelling.klab.hub.nodes.commands.MongoNodeExists;
import org.integratedmodelling.klab.hub.nodes.commands.UpdateMongoNode;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
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
	public MongoNode create(MongoNode node) {
		if (!nodeExists(node.getName())) {
			return new CreateMongoNode(node, nodeRepository).execute();
		} else {
			throw new BadRequestException("Node by that name already exists");
		}
	}

	@Override
	public MongoNode update(MongoNode node) {
		return new UpdateMongoNode(node, nodeRepository).execute();
	}

	@Override
	public void delete(String name) {
		new DeleteMongoNode(name, nodeRepository).execute();
		return;
	}

	@Override
	public Collection<MongoNode> getAll() {
		return new GetMongoNodes(nodeRepository).execute();
	}

	@Override
	public MongoNode getByName(String nodeName) {
		return new GetMongoNodeByName(nodeName, nodeRepository).execute();
	}
	
	private Boolean nodeExists(String nodeName) {
		return nodeRepository.findByNameIgnoreCase(nodeName).isPresent();
	}

	@Override
	public void removeGroupFromNodes(String groupName) {
		nodeRepository.findAll().forEach(node -> {
			node.removeGroupByName(groupName);
			new UpdateMongoNode(node, nodeRepository).execute();
		});
	}

	@Override
	public boolean exists(String name) {
		return new MongoNodeExists(name, nodeRepository).execute();
	}

	@Override
	public MongoNode getById(String id) {
		return new GetMongoNodeById(id, nodeRepository).execute();
	}

}
