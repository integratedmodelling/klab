package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class IdentityReference {

	private String id;
	private String email;
	private String lastLogin;
	private List<GroupReference> groups = new ArrayList<>();

	public IdentityReference() {
	}

	public IdentityReference(String id, String email, String lastLogin) {
		super();
		this.id = id;
		this.email = email;
		this.lastLogin = lastLogin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentityReference other = (IdentityReference) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IdentityReference [id=" + id + ", email=" + email + ", lastLogin=" + lastLogin + "]";
	}

	public List<GroupReference> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupReference> groups) {
		this.groups = groups;
	}

}
