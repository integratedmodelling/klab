package org.integratedmodelling.klab.hub.models;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class GroupEntry {
	
	@DBRef
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
		setInception();
	}
	
	public GroupEntry() {
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

	public KlabGroup getGroup() {
		return group;
	}
	
	public void setGroup(KlabGroup group) {
		this.group = group;
	}
}
