package org.integratedmodelling.klab.rest;

public class AuthorityResolutionRequest {

	private String authority;
	private String identity;

	public AuthorityResolutionRequest() {
	}

	public AuthorityResolutionRequest(String authority, String identity) {
		this.authority = authority;
		this.identity = identity;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

}
