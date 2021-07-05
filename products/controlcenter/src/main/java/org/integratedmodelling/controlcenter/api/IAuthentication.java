package org.integratedmodelling.controlcenter.api;

import java.util.List;

import org.integratedmodelling.klab.rest.Group;
import org.joda.time.DateTime;

public interface IAuthentication {

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
