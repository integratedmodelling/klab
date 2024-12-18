package org.integratedmodelling.klab.rest;

import java.util.Objects;

public class UserAuthenticationRequest {

	private String username;	

	private boolean remote = false;

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
		this.username = username;
	}

	public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean jwtToken) {
        this.remote = jwtToken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(remote, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserAuthenticationRequest)) {
            return false;
        }
        UserAuthenticationRequest other = (UserAuthenticationRequest) obj;
        return remote == other.remote && Objects.equals(username, other.username);
    }

    @Override
    public String toString() {
        return "UserAuthenticationRequest [username=" + username + ", remote=" + remote + "]";
    }

}
