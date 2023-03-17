package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.commands.CreateMongoGroup;
import org.integratedmodelling.klab.hub.commands.DeleteMongoGroup;
import org.integratedmodelling.klab.hub.commands.GetAllMongoGroupNames;
import org.integratedmodelling.klab.hub.commands.GetAllMongoGroups;
import org.integratedmodelling.klab.hub.commands.GetMongoGroupById;
import org.integratedmodelling.klab.hub.commands.GetMongoGroupByName;
import org.integratedmodelling.klab.hub.commands.MongoGroupExists;
import org.integratedmodelling.klab.hub.commands.UpdateMongoGroup;
import org.integratedmodelling.klab.hub.exception.GroupDoesNotExistException;
import org.integratedmodelling.klab.hub.exception.GroupExistException;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.listeners.RemoveGroup;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
	
	private MongoGroupRepository repository;
	
	private HubEventPublisher<RemoveGroup> publisher;
	
	@Autowired
	public GroupServiceImpl(MongoGroupRepository repository,
			HubEventPublisher<RemoveGroup> publisher) {
		super();
		this.repository = repository;
		this.publisher = publisher;
	}	

	@Override
	public Collection<MongoGroup> getAll() {
		return new GetAllMongoGroups(repository).execute();
	}

	@Override
	public Collection<String> getGroupNames() {
		return new GetAllMongoGroupNames(repository).execute();
	}

	@Override
	public boolean exists(String groupName) {
		return new MongoGroupExists(groupName, repository).execute();
	}

	@Override
	public MongoGroup create(MongoGroup group) {
		if(repository.findByNameIgnoreCase(group.getName()).isPresent()) {
			throw new GroupExistException("Group by the name: " + group.getName() + " already exists");
		}
		 return new CreateMongoGroup(group, repository).execute();
	}

	@Override
	public MongoGroup update(MongoGroup group) {
		if(exists(group.getName())) {
			return new UpdateMongoGroup(group, repository).execute();
		} else {
			throw new GroupDoesNotExistException("No group by the name: " + group.getName() + " was found.");
		}
	}

	@Override
	public void delete(String name) {
		if(exists(name)) {
			MongoGroup group = getByName(name);
			//this needs to get called first, safer to remove the group only after it has been cascaded
			this.publisher.publish(new RemoveGroup(new Object(), group.getName()));
			new DeleteMongoGroup(group, repository).execute();
		} else {
			throw new GroupDoesNotExistException("No group by the name: " + name + " was found.");
		}		
	}

	@Override
	public MongoGroup getByName(String groupName) {
		MongoGroup group = null;
		group =  new GetMongoGroupByName(groupName, repository).execute();
		if(group != null) {
			return group;
		} else {
			throw new GroupDoesNotExistException("No group by the name: " + groupName + " was found.");
		}
	}

	@Override
	public MongoGroup getById(String id) {
		MongoGroup group = null;
		group =  new GetMongoGroupById(id, repository).execute();
		if(group != null) {
			return group;
		} else {
			throw new GroupDoesNotExistException("No group by the id: " + id + " was found.");
		}
	}
	
	

}
