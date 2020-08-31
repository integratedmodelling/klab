package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EngineAuthenticationResponse {

	private AuthenticatedIdentity userData;
	private HubReference hub;
	private List<NodeReference> nodes = new ArrayList<>();
	private ArrayList<String> warnings;

	public AuthenticatedIdentity getUserData() {
		return userData;
	}

	public void setUserData(AuthenticatedIdentity userData) {
		this.userData = userData;
	}

	public List<NodeReference> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeReference> nodes) {
		this.nodes = nodes;
	}
	
	public HubReference getHub() {
		return hub;
	}

	public void setHub(HubReference hub) {
		this.hub = hub;
	}

	public EngineAuthenticationResponse() {
	}

	public EngineAuthenticationResponse(AuthenticatedIdentity userData, HubReference hub,
			Collection<NodeReference> nodes) {
		super();
		this.userData = userData;
		this.hub = hub;
		this.nodes.addAll(nodes);
	}
	
	public ArrayList<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(ArrayList<String> warnings) {
		this.warnings = warnings;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EngineAuthenticationResponse other = (EngineAuthenticationResponse) obj;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
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
		return "EngineAuthenticationResponse [userData=" + userData + ", nodes=" + nodes + ", hub=" + hub + "]";
	}
}
