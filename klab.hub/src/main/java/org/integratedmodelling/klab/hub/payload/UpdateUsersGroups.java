package org.integratedmodelling.klab.hub.payload;

import java.time.LocalDateTime;
import java.util.Set;

public class UpdateUsersGroups {
	public Set<String> usernames;
	
	public Set<String> groupnames;
	
	public LocalDateTime expiration;
	
	public UpdateUsersGroups() {};
	
	public UpdateUsersGroups(Set<String> usernames,Set<String> groupnames, LocalDateTime expiration) {
		this.usernames = usernames;
		this.groupnames = groupnames;
		this.expiration = expiration;
	}

	public Set<String> getUsernames() {
		return usernames;
	}

	public Set<String> getGroupNames() {
		return groupnames;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}
	
}
