package org.integratedmodelling.kim.rest;

import java.util.ArrayList;
import java.util.List;

public class AuthenticatedIdentity {

    private IdentityReference identity;
    private List<Group> groups = new ArrayList<>();
    private String expiry;
    private String token;
    
    public IdentityReference getIdentity() {
        return identity;
    }
    
    public void setIdentity(IdentityReference identity) {
        this.identity = identity;
    }
    
    public List<Group> getGroups() {
        return groups;
    }
    
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    public String getExpiry() {
        return expiry;
    }
    
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiry == null) ? 0 : expiry.hashCode());
        result = prime * result + ((groups == null) ? 0 : groups.hashCode());
        result = prime * result + ((identity == null) ? 0 : identity.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        AuthenticatedIdentity other = (AuthenticatedIdentity) obj;
        if (expiry == null) {
            if (other.expiry != null)
                return false;
        } else if (!expiry.equals(other.expiry))
            return false;
        if (groups == null) {
            if (other.groups != null)
                return false;
        } else if (!groups.equals(other.groups))
            return false;
        if (identity == null) {
            if (other.identity != null)
                return false;
        } else if (!identity.equals(other.identity))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }

    public AuthenticatedIdentity() {}
    
    public AuthenticatedIdentity(IdentityReference identity, List<Group> groups, String expiry, String token) {
        super();
        this.identity = identity;
        this.groups = groups;
        this.expiry = expiry;
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthenticatedIdentity [identity=" + identity + ", groups=" + groups + ", expiry=" + expiry + ", token="
                + token + "]";
    }
    
}
