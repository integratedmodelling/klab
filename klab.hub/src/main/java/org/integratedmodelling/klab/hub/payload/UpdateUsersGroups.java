package org.integratedmodelling.klab.hub.payload;

import java.util.Set;

public class UpdateUsersGroups {
	public Set<String> usernames;
	
	public Set<String> groupnames;

	public Set<String> getUsernames() {
		return usernames;
	}

	public Set<String> getGroupnames() {
		return groupnames;
	}
	
}
