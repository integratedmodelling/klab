package org.integratedmodelling.klab.hub.api;

import java.util.Objects;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class GroupEntry {
	
	@DBRef
	private MongoGroup group;
	private DateTime expiration;
	private DateTime inception;
	
	public GroupEntry(MongoGroup group, DateTime experation) {
		this.group = group;
		if(experation != null) {
			this.expiration = experation;
		} else {
			this.expiration = DateTime.now().plusDays(365);
		}
		setInception();
	}
	
	public GroupEntry(MongoGroup group) {
		this.group = group;
		this.expiration = DateTime.now().plusDays(365);
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
		if(expiration == null) {
			return DateTime.now().plusMonths(6);
		} else {
			return expiration;
		}
	}

	public void setRenewal(DateTime experation) {
		this.expiration = experation;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(group);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupEntry other = (GroupEntry) obj;
		return Objects.equals(group, other.group);
	}
}
