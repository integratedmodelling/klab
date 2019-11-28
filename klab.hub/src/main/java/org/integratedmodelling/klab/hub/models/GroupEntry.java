package org.integratedmodelling.klab.hub.models;

import org.joda.time.DateTime;

public class GroupEntry {
	
	private KlabGroup group;
	private DateTime experation;
	private DateTime inception;
	
	public GroupEntry(KlabGroup group, DateTime experation) {
		this.group = group;
		this.experation = experation;
		setInception();
	}
	
	public GroupEntry(KlabGroup group) {
		this.group = group;
		this.experation = null;
	}
	
	
	public void setInception() {
		this.inception = DateTime.now();
	}

	public String getGroupName() {
		return group.getId();
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
