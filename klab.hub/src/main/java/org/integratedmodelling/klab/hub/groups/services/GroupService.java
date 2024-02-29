package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.hub.services.GenericHubService;
import org.integratedmodelling.klab.hub.users.dto.GroupSummary;
import org.integratedmodelling.klab.hub.users.dto.MongoGroup;
import org.springframework.stereotype.Service;

@Service
public interface GroupService extends GenericHubService<MongoGroup>{
	public abstract Collection<String> getGroupNames();
	public abstract Collection<GroupSummary> getGroupsSummary();
//	public abstract boolean groupExists(String groupName);
//	public abstract MongoGroup createGroup(MongoGroup group);
//	public abstract void updateGroup(MongoGroup group);
//	public abstract void deleteGroup(MongoGroup group);
//  public abstract Collection<MongoGroup> getGroups();
//  public abstract MongoGroup getGroupByName(String groupName);

    List<MongoGroup> getGroupsDefault();
}
