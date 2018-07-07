package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EngineAuthenticationResponse {

    private AuthenticatedIdentity userData;
    private List<NodeReference> nodes = new ArrayList<>();
    private String authenticatingNodeId;
    
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
        result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
        result = prime * result + ((userData == null) ? 0 : userData.hashCode());
        return result;
    }
    
    public EngineAuthenticationResponse() {}

    public EngineAuthenticationResponse(AuthenticatedIdentity userData,
            Collection<NodeReference> nodes,
            String authenticatingNodeId) {
        super();
        this.userData = userData;
        this.nodes.addAll(nodes);
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
        EngineAuthenticationResponse other = (EngineAuthenticationResponse) obj;
        if (authenticatingNodeId == null) {
            if (other.authenticatingNodeId != null)
                return false;
        } else if (!authenticatingNodeId.equals(other.authenticatingNodeId))
            return false;
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
        return "EngineAuthenticationResponse [userData=" + userData + ", nodes=" + nodes + ", authenticatingNodeId="
                + authenticatingNodeId + "]";
    }
}
