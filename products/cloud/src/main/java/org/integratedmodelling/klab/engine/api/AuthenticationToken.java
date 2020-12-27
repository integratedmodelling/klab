package org.integratedmodelling.klab.engine.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationToken {
	
	@JsonProperty
	private String tokenString;

	public String getTokenString() {
		return tokenString;
	}

	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}

}
