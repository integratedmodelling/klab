package org.integratedmodelling.klab.hub.payload;

import org.integratedmodelling.klab.rest.UserAuthenticationRequest;

public class UpdateEmailRequest extends UserAuthenticationRequest{
	
	public String email;
	public String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	

}
