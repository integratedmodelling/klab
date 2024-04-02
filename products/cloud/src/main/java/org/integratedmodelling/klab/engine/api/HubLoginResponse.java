package org.integratedmodelling.klab.engine.api;

import org.integratedmodelling.klab.engine.services.HubUserProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HubLoginResponse {
	
	@JsonProperty("Profile")
	private HubUserProfile profile;
	
	@JsonProperty("Authentication")
	private AuthenticationToken authentication;

	public HubUserProfile getProfile() {
		return profile;
	}
	
	public AuthenticationToken getAuthentication() {
		return authentication;
	}

}
