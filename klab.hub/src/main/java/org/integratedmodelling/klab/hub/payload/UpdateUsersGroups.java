package org.integratedmodelling.klab.hub.payload;

import java.time.LocalDate;
import java.util.Set;

import org.joda.time.DateTime;

public class UpdateUsersGroups {
	public Set<String> usernames;
	
	public Set<String> groupnames;
	
	public LocalDate expiration;
	
	public UpdateUsersGroups() {};
	
	public UpdateUsersGroups(Set<String> usernames,Set<String> groupnames, LocalDate expiration) {
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

	public LocalDate getExpiration() {
		return expiration;
	}
	
}
