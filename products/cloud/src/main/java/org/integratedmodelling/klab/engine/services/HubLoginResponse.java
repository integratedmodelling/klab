package org.integratedmodelling.klab.engine.services;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HubLoginResponse {
	
	@JsonProperty("Profile")
	private HubUserProfile profile;
	
	@JsonProperty("Authentication")
	private JSONObject authentication;

	public HubUserProfile getProfile() {
		return profile;
	}
	
}
