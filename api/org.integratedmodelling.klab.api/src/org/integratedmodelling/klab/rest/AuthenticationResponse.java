package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResponse {
    
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
    
    public AuthenticationResponse() {}

    public AuthenticationResponse(AuthenticatedIdentity userData,
            List<NodeReference> nodes,
            String authenticatingNodeId) {
        super();
        this.userData = userData;
        this.nodes = nodes;
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
        AuthenticationResponse other = (AuthenticationResponse) obj;
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
        return "AuthenticationResponse [userData=" + userData + ", nodes=" + nodes + ", authenticatingNodeId="
                + authenticatingNodeId + "]";
    }
}
