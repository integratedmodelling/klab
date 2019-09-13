package org.integratedmodelling.klab.hub.service.implementation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.models.KlabNode;
import org.integratedmodelling.klab.hub.service.KlabNodeService;
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
	public void createNode(String nodename, KlabNode node) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<KlabNode> found = mongoTemplate.find(query, KlabNode.class);
		if (found.size() == 0) {
			mongoTemplate.save(node);
		} else {
			throw new BadRequestException("A node by that name exists with different data.");
		}
	}

	@Override
	public void updateNodeGroups(String nodename, KlabNode node) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<KlabNode> found = mongoTemplate.find(query, KlabNode.class);
		if (found.size() == 1) {
			KlabNode savedNode = found.get(0);
			savedNode.setGroups(node.getGroups());
			mongoTemplate.save(savedNode);
		} else {
			throw new BadRequestException("Found more than one node of that name");
		}
	}
	
	@Override
	public void updateNodeLastConnection(String nodename) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<KlabNode> found = mongoTemplate.find(query, KlabNode.class);
		if (found.size() == 1) {
			KlabNode savedNode = found.get(0);
			savedNode.setLastNodeConnection();
			mongoTemplate.save(savedNode);
		} else {
			throw new BadRequestException("Found more than one node of that name");
		}
	}

	@Override
	public void deleteNode(String nodename) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<KlabNode> found = mongoTemplate.find(query, KlabNode.class);
		mongoTemplate.remove(found);
	}

	@Override
	public Collection<KlabNode> getNodes() {
		return mongoTemplate.findAll(KlabNode.class);
	}

	@Override
	public Optional<KlabNode> getNode(String nodename) {
		Query query = new Query(Criteria.where("node").is(nodename));
		List<KlabNode> found = mongoTemplate.find(query, KlabNode.class);
		if (found.size() == 1) {
			Optional<KlabNode> node = Optional.of(found.get(0));
			return node;
		}
		Optional<KlabNode> emptyNode = Optional.empty();
		return emptyNode;
	}
	
}
