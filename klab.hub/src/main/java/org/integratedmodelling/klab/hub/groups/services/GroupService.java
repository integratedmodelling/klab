package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.integratedmodelling.klab.hub.service.GenericHubService;
import org.springframework.stereotype.Service;

@Service
public interface GroupService extends GenericHubService<MongoGroup>{
	public abstract Collection<String> getGroupNames();
//	public abstract boolean groupExists(String groupName);
//	public abstract MongoGroup createGroup(MongoGroup group);
//	public abstract void updateGroup(MongoGroup group);
//	public abstract void deleteGroup(MongoGroup group);
//  public abstract Collection<MongoGroup> getGroups();
//  public abstract MongoGroup getGroupByName(String groupName);
}
