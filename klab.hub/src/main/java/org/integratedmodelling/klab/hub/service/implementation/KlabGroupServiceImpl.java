package org.integratedmodelling.klab.hub.service.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class KlabGroupServiceImpl implements KlabGroupService {
	
	private final MongoTemplate mongoTemplate;
	
    @Autowired
    public KlabGroupServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
	@Override
	public void createGroup(String id, KlabGroup group) {
		Query query = new Query(Criteria.where("id").is(id));
		List<KlabGroup> found = mongoTemplate.find(query, KlabGroup.class);
		if (found.size() == 0) {
			mongoTemplate.save(group);
		} else {
			throw new BadRequestException("A group by that name exists with different data.");
		}
	}

	@Override
	public void updateGroup(String id, KlabGroup group) {
		Query query = new Query(Criteria.where("id").is(id));
		List<KlabGroup> found = mongoTemplate.find(query, KlabGroup.class);
		if (found.size() == 1) {
			mongoTemplate.save(group);
		}
	}

	@Override
	public void deleteGroup(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		List<KlabGroup> found = mongoTemplate.find(query, KlabGroup.class);
		mongoTemplate.remove(found);
	}

	@Override
	public Collection<KlabGroup> getGroups() {
		return mongoTemplate.findAll(KlabGroup.class);
	}

	@Override
	public Optional<KlabGroup> getGroup(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		List<KlabGroup> found = mongoTemplate.find(query, KlabGroup.class);
		if (found.size() == 1) {
			Optional<KlabGroup> group = Optional.of(found.get(0));
			return group;
		}
		Optional<KlabGroup> emptyGroup = Optional.empty();
		return emptyGroup;
	}

	@Override
	public Collection<String> getGroupNames() {
		Collection<KlabGroup> groups = mongoTemplate.findAll(KlabGroup.class);
		Collection<String> groupNames = new HashSet<>();
		for(KlabGroup group: groups) {
			groupNames.add(group.getId());
		}
		return groupNames;
	}

}
