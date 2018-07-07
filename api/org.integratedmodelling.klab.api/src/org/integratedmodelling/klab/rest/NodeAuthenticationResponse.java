package org.integratedmodelling.klab.rest;

public class NodeAuthenticationResponse {

	private AuthenticatedIdentity userData;
	private String authenticatingNodeId;

	public AuthenticatedIdentity getUserData() {
		return userData;
	}

	public void setUserData(AuthenticatedIdentity userData) {
		this.userData = userData;
	}

	public String getAuthenticatingNodeId() {
		return authenticatingNodeId;
	}

	public void setAuthenticatingNodeId(String authenticatingNodeId) {
		this.authenticatingNodeId = authenticatingNodeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authenticatingNodeId == null) ? 0 : authenticatingNodeId.hashCode());
		result = prime * result + ((userData == null) ? 0 : userData.hashCode());
		return result;
	}

	public NodeAuthenticationResponse() {
	}

	public NodeAuthenticationResponse(AuthenticatedIdentity userData, String authenticatingNodeId) {
		super();
		this.userData = userData;
		this.authenticatingNodeId = authenticatingNodeId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeAuthenticationResponse other = (NodeAuthenticationResponse) obj;
		if (authenticatingNodeId == null) {
			if (other.authenticatingNodeId != null)
				return false;
		} else if (!authenticatingNodeId.equals(other.authenticatingNodeId))
			return false;
		if (userData == null) {
			if (other.userData != null)
				return false;
		} else if (!userData.equals(other.userData))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EngineAuthenticationResponse [userData=" + userData + "]";
	}
}
