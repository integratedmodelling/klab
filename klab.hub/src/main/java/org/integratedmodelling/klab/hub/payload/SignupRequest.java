package org.integratedmodelling.klab.hub.payload;

import javax.validation.constraints.NotBlank;

public class SignupRequest {
	
	@NotBlank
	private String username;

	@NotBlank
	private String email;
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

}
