package org.integratedmodelling.klab.hub.payload;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class InviteRequest {

	@NotBlank
	private String email;
	
	private List<String> groups;

	public String getEmail() {
		return email;
	}
	
	public List<String> getGroups() {
		return groups;
	}


}
