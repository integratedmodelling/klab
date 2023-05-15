package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ExternalAuthenticationCredentials {

	/**
	 * Credentials, depending on scheme 
	 * 
	 * for BASIC: username and password
	 * for OAUTH2: Authentication URL, grant type, client ID, client secret, scope
	 */
	private List<String> credentials = new ArrayList<>();

	/**
	 * one of BASIC, DIGEST, SSL, NTLM, OAUTH2, ANY
	 */
	private String scheme = "BASIC";

	public List<String> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<String> credentials) {
		this.credentials = credentials;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

}
