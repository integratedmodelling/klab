package org.integratedmodelling.klab.hub.payload;

import javax.validation.constraints.NotBlank;

public class SignupRequest {
	
	public SignupRequest(){
	}
	
	public SignupRequest(@NotBlank String username, @NotBlank String email) {
		super();
		this.username = username;
		this.email = email;
	}

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
