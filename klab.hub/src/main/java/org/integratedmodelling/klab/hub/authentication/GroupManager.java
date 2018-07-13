package org.integratedmodelling.klab.hub.authentication;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.springframework.stereotype.Service;

/**
 * Group catalog and management service.
 * 
 * TODO add CRUD operations and back to something more sensible.
 * 
 * @author ferdinando.villa
 *
 */
@Service
public class GroupManager {

	private Map<String, Group> groups = new HashMap<>();

	public GroupManager() {
		// TODO read this from the file only if not stored, then manage using a
		// persistent DB
		groups = FileCatalog.create(getClass().getClassLoader().getResource("auth/groups.json"), Group.class);
	}

	/**
	 * All groups known to this hub.
	 * 
	 * @return
	 */
	public Collection<Group> getGroups() {
		return groups.values();
	}

	public Group getGroup(String key) {
		return groups.get(key);
	}
}
