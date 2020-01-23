package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.rest.Group;

public interface MongoGroupService {
	public abstract void createGroup(String id, MongoGroup group);
	public abstract void updateGroup(String id, MongoGroup group);
	public abstract void deleteGroup(String id);
	public abstract Collection<MongoGroup> getGroups();
	public abstract Optional<MongoGroup> getGroup(String id);
	public abstract Collection<String> getGroupNames();
	public abstract Collection<? extends Group> getGroupsList();
	public abstract boolean groupsExists(List<String> groupNames);
	public abstract boolean groupExists(String groupName);
}
