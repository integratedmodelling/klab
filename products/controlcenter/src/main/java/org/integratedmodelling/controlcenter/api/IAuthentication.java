package org.integratedmodelling.controlcenter.api;

import java.util.Date;
import java.util.List;

public interface IAuthentication {

	public interface Group {
		
	}
	
	enum Status {
		ANONYMOUS,
		OFFLINE,
		EXPIRED,
		INVALID,
		VALID
	}
	
	String getAuthenticationEndpoint();
	
	String getUsername();
	
	String getEmail();
	
	Date getExpiration();
	
	List<Group> groups();
}
