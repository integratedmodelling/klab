package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
	
	private MongoGroupRepository mongoGroupRepository;
	
	public GroupServiceImpl(MongoGroupRepository mongoGroupRepository) {
		super();
		this.mongoGroupRepository = mongoGroupRepository;
	}	

	@Override
	public Collection<MongoGroup> getGroups() {
		return mongoGroupRepository.findAll();
	}

}
