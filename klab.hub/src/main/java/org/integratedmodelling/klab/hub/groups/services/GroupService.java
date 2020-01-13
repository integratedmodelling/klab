package org.integratedmodelling.klab.hub.groups.services;

import java.util.Collection;

import org.integratedmodelling.klab.hub.groups.MongoGroup;

public interface GroupService {
	public abstract Collection<MongoGroup> getGroups();
}
