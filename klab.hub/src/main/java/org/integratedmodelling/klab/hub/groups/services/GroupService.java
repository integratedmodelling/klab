package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.rest.Group;

public interface GroupService {
	public abstract MongoGroup createGroup(MongoGroup group);
   	public abstract void updateGroup(MongoGroup group);
   	public abstract void deleteGroup(MongoGroup group);
	public abstract Collection<MongoGroup> getGroups();
	public abstract MongoGroup getGroupByName(String groupName);
	public abstract Collection<String> getGroupNames();
	public abstract boolean groupExists(String groupName);
}
