package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EngineAuthenticationResponse {

	private AuthenticatedIdentity userData;
	private HubReference hub;
	private List<NodeReference> nodes = new ArrayList<>();
	private List<NodeReference> services = new ArrayList<>();
	private ArrayList<HubNotificationMessage> messages;
	private String authentication; 

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
	
    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

	public EngineAuthenticationResponse() {
	}

	public EngineAuthenticationResponse(AuthenticatedIdentity userData, HubReference hub,
			Collection<NodeReference> nodes, Collection<NodeReference> services) {
		super();
		this.userData = userData;
		this.hub = hub;
		this.nodes.addAll(nodes);
		this.services.addAll(services);
	}
	
	public ArrayList<HubNotificationMessage> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<HubNotificationMessage> warnings) {
		this.messages = warnings;
	}

    @Override
    public String toString() {
        return "EngineAuthenticationResponse [userData=" + userData + ", hub=" + hub + ", nodes=" + nodes + 
                ", services=" + services + ", messages=" + messages + ", authentication=" + authentication + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authentication == null) ? 0 : authentication.hashCode());
        result = prime * result + ((hub == null) ? 0 : hub.hashCode());
        result = prime * result + ((messages == null) ? 0 : messages.hashCode());
        result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
        result = prime * result + ((services == null) ? 0 : services.hashCode());
        result = prime * result + ((userData == null) ? 0 : userData.hashCode());
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
        EngineAuthenticationResponse other = (EngineAuthenticationResponse) obj;
        if (authentication == null) {
            if (other.authentication != null)
                return false;
        } else if (!authentication.equals(other.authentication))
            return false;
        if (hub == null) {
            if (other.hub != null)
                return false;
        } else if (!hub.equals(other.hub))
            return false;
        if (messages == null) {
            if (other.messages != null)
                return false;
        } else if (!messages.equals(other.messages))
            return false;
        if (nodes == null) {
            if (other.nodes != null)
                return false;
        } else if (!nodes.equals(other.nodes))
            return false;
        if (services == null) {
            if (other.services != null)
                return false;
        } else if (!services.equals(other.services))
            return false;
        if (userData == null) {
            if (other.userData != null)
                return false;
        } else if (!userData.equals(other.userData))
            return false;
        return true;
    }

}
