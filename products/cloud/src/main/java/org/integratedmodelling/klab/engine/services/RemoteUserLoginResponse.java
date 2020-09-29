package org.integratedmodelling.klab.engine.services;

public class RemoteUserLoginResponse {
	private String redirect;
	
	private String authorization;

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
