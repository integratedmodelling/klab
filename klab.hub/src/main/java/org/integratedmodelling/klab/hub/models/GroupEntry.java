package org.integratedmodelling.klab.hub.models;

import org.joda.time.DateTime;

public class GroupEntry {
	
	private String groupName;
	private DateTime experation;
	private DateTime inception;
	
	public GroupEntry(String groupName, DateTime experation) {
		this.groupName = groupName;
		this.experation = experation;
		setInception();
	}
	
	public GroupEntry(String groupName) {
		this.groupName = groupName;
		this.experation = null;
	}
	
	
	public void setInception() {
		this.inception = DateTime.now();
	}

	public String getGroupName() {
		return groupName;
	}

	public DateTime getExperation() {
		return experation;
	}

	public void setRenewal(DateTime experation) {
		this.experation = experation;
	}

	public DateTime getInception() {
		return inception;
	}
	
	public boolean isExpired() {
		DateTime expires = getExperation();
		expires.isAfterNow();
		return true;
	}
	
}
