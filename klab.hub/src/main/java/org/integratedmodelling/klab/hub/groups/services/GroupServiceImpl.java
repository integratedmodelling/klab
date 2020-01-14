package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;
import java.util.HashSet;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.groups.commands.CreateMongoGroup;
import org.integratedmodelling.klab.hub.groups.commands.DeleteMongoGroup;
import org.integratedmodelling.klab.hub.groups.commands.GetMongoGroupByName;
import org.integratedmodelling.klab.hub.groups.commands.UpdateMongoGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
	
	private MongoGroupRepository repository;
	
	public GroupServiceImpl(MongoGroupRepository repository) {
		super();
		this.repository = repository;
	}	

	@Override
	public Collection<MongoGroup> getGroups() {
		return repository.findAll();
	}

	@Override
	public Collection<String> getGroupNames() {
		Collection<MongoGroup> groups = getGroups();
		Collection<String> groupNames = new HashSet<>();
		groups.forEach(group -> groupNames.add(group.getGroupName()));
		return groupNames;
	}

	@Override
	public boolean groupExists(String groupName) {
		return repository.findByGroupNameIgnoreCase(groupName).isPresent();
	}

	@Override
	public MongoGroup createGroup(MongoGroup group) {
		if(!groupExists(group.getGroupName())) {
			return new CreateMongoGroup(group, repository).execute();
		} else {
			throw new BadRequestException("Group by that group name already exists.");
		}
	}

	@Override
	public void updateGroup(MongoGroup group) {
		if(!groupExists(group.getGroupName())) {
			new UpdateMongoGroup(group, repository).execute();
		} else {
			throw new BadRequestException("Group by that group name already exists.");
		}
	}

	@Override
	public void deleteGroup(MongoGroup group) {
		if(!groupExists(group.getGroupName())) {
			new DeleteMongoGroup(group, repository).execute();
		} else {
			throw new BadRequestException("Group by that group name already exists.");
		}		
	}

	@Override
	public MongoGroup getGroupByName(String groupName) {
		return new GetMongoGroupByName(groupName, repository).execute();
	}

}
