package org.integratedmodelling.klab.hub.payload;

import java.util.Set;

import org.joda.time.DateTime;

public class UpdateUsersGroups {
	public Set<String> usernames;
	
	public Set<String> groupnames;
	
	public DateTime experation;
	
	public UpdateUsersGroups() {};
	
	public UpdateUsersGroups(Set<String> usernames,Set<String> groupnames, DateTime experation) {
		this.usernames = usernames;
		this.groupnames = groupnames;
		this.experation = experation;
	}

	public Set<String> getUsernames() {
		return usernames;
	}

	public Set<String> getGroupnames() {
		return groupnames;
	}

	public DateTime getExperation() {
		return experation;
	}
	
}
