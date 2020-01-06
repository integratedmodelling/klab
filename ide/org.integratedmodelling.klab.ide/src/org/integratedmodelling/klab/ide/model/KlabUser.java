package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.rest.IdentityReference;

public class KlabUser {

	private String username = "Offline";
	private String email = "";
	private boolean online;
	private String token;

	public KlabUser() {
	}

	public KlabUser(IdentityReference owner) {
		this.email = owner.getEmail();
		this.username = owner.getId();
		this.online = true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

}
