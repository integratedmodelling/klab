package org.integratedmodelling.klab.hub.api;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class GroupEntry {
	
	@DBRef
	private MongoGroup group;
	private DateTime experation;
	private DateTime inception;
	
	public GroupEntry(MongoGroup group, DateTime experation) {
		this.group = group;
		this.experation = experation;
		setInception();
	}
	
	public GroupEntry(MongoGroup group) {
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
		return group.getName();
	}

	public DateTime getExperation() {
		if(experation == null) {
			return DateTime.now().plusMonths(6);
		} else {
			return experation;
		}
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

	public MongoGroup getGroup() {
		return group;
	}
	
	public void setGroup(MongoGroup group) {
		this.group = group;
	}
}
