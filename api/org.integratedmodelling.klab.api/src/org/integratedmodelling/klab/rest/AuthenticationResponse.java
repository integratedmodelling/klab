package org.integratedmodelling.klab.rest;

public abstract class AuthenticationResponse {
	
	private AuthenticatedIdentity userData;
	private HubReference hubReference;
	
	public AuthenticatedIdentity getUserData() {
		return userData;
	}
	public void setUserData(AuthenticatedIdentity userData) {
		this.userData = userData;
	}
	public HubReference getHubReference() {
		return hubReference;
	}
	public void setHubReference(HubReference hubReference) {
		this.hubReference = hubReference;
	}

}
