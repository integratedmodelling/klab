package org.integratedmodelling.klab.hub.api;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class GroupEntry {
	
	@DBRef
	private MongoGroup group;
	private LocalDate experation;
	private LocalDate inception;
	
	public GroupEntry(MongoGroup group, LocalDate experation) {
		this.group = group;
		if(experation != null) {
			this.experation = experation;
		} else {
			this.experation = LocalDate.now().plusDays(365);
		}
		setInception();
	}
	
	public GroupEntry(MongoGroup group) {
		this.group = group;
		this.experation = LocalDate.now().plusDays(365);
		setInception();
	}
	
	public GroupEntry() {
	}
	
	
	public void setInception() {
		this.inception = LocalDate.now();
	}

	public String getGroupName() {
		return group.getName();
	}

	public LocalDate getExperation() {
		if(experation == null) {
			return LocalDate.now().plusMonths(6);
		} else {
			return experation;
		}
	}

	public void setRenewal(LocalDate experation) {
		this.experation = experation;
	}

	public LocalDate getInception() {
		return inception;
	}
	
	public boolean isExpired() {
	    LocalDate expires = getExperation();
		expires.isAfter(LocalDate.now());
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
