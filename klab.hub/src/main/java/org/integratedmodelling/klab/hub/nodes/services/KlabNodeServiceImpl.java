package org.integratedmodelling.klab.hub.nodes.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class KlabNodeServiceImpl implements KlabNodeService{
	
	private final MongoTemplate mongoTemplate;
	
    @Autowired
    public KlabNodeServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

	@Override
	public void createNode(String nodename, MongoNode node) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<MongoNode> found = mongoTemplate.find(query, MongoNode.class);
		if (found.size() == 0) {
			mongoTemplate.save(node);
		} else {
			throw new BadRequestException("A node by that name exists with different data.");
		}
	}

	@Override
	public void updateNodeGroups(String nodename, MongoNode node) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<MongoNode> found = mongoTemplate.find(query, MongoNode.class);
		if (found.size() == 1) {
			MongoNode savedNode = found.get(0);
			savedNode.setGroups(node.getGroups());
			mongoTemplate.save(savedNode);
		} else {
			throw new BadRequestException("Found more than one node of that name");
		}
	}
	
	@Override
	public void updateNodeLastConnection(String nodename) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<MongoNode> found = mongoTemplate.find(query, MongoNode.class);
		if (found.size() == 1) {
			MongoNode savedNode = found.get(0);
			savedNode.setLastConnection();
			mongoTemplate.save(savedNode);
		} else {
			throw new BadRequestException("Found more than one node of that name");
		}
	}

	@Override
	public void deleteNode(String nodename) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<MongoNode> found = mongoTemplate.find(query, MongoNode.class);
		if (found.size() == 1) {
			MongoNode deleteNode = found.get(0);
			mongoTemplate.remove(deleteNode);
		} else {
			throw new BadRequestException("Found more than one node of that name");
		}
	}

	@Override
	public Collection<MongoNode> getNodes() {
		return mongoTemplate.findAll(MongoNode.class);
	}

	@Override
	public Optional<MongoNode> getNode(String nodename) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<MongoNode> found = mongoTemplate.find(query, MongoNode.class);
		if (found.size() == 1) {
			Optional<MongoNode> node = Optional.of(found.get(0));
			return node;
		}
		Optional<MongoNode> emptyNode = Optional.empty();
		return emptyNode;
	}
	
}
