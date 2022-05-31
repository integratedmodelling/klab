package org.integratedmodelling.klab.engine.api;

import java.util.List;

import org.integratedmodelling.klab.rest.BehaviorReference;

public class RemoteUserLoginResponse {
	private String redirect;
	private String session;
	private String token;
	
	private List<BehaviorReference> publicApps;

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String sessionId) {
		this.session = sessionId;
	}

	public String getAuthorization() {
		return token;
	}

	public void setAuthorization(String authorization) {
		this.token = authorization;
	}

	public List<BehaviorReference> getPublicApps() {
		return publicApps;
	}

	public void setPublicApps(List<BehaviorReference> publicApps) {
		this.publicApps = publicApps;
	}

}
