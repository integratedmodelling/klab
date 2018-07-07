package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.auth.ICertificate;

public class NodeAuthenticationRequest {
    
    private String username;
    private String userKey;
    private String userType;
    private String certificate;
	private ICertificate.Level level = ICertificate.Level.USER;
	
    public NodeAuthenticationRequest() {}
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserKey() {
        return userKey;
    }
    
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String getCertificate() {
        return certificate;
    }
    
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

	public ICertificate.Level getLevel() {
		return level;
	}

	public void setLevel(ICertificate.Level level) {
		this.level = level;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((certificate == null) ? 0 : certificate.hashCode());
        result = prime * result + ((userKey == null) ? 0 : userKey.hashCode());
        result = prime * result + ((userType == null) ? 0 : userType.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        NodeAuthenticationRequest other = (NodeAuthenticationRequest) obj;
        if (certificate == null) {
            if (other.certificate != null)
                return false;
        } else if (!certificate.equals(other.certificate))
            return false;
        if (userKey == null) {
            if (other.userKey != null)
                return false;
        } else if (!userKey.equals(other.userKey))
            return false;
        if (userType == null) {
            if (other.userType != null)
                return false;
        } else if (!userType.equals(other.userType))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}
