package org.integratedmodelling.controlcenter.api;

import java.util.List;

import org.joda.time.DateTime;

public interface IAuthentication {

	public class Group {
		public String name;
		public String description;
		public String iconUrl;
	}
	
	enum Status {
		ANONYMOUS,
		OFFLINE,
		EXPIRED,
		INVALID,
		VALID
	}
	
	Status getStatus();
	
	String getAuthenticationEndpoint();
	
	String getUsername();
	
	String getEmail();
	
	DateTime getExpiration();
	
	List<Group> getGroups();
}
