package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodeReference {
    
    public static enum Permission {
        PUBLISH,
        QUERY
    }
    
    private String id;
    private Set<Permission> permissions = new HashSet<>();
    private IdentityReference partner;
    private List<String> urls = new ArrayList<>();
    private boolean online;
    private int retryPeriodMinutes;
    private int loadFactor;
    private List<String> incomingConnections = new ArrayList<>();
    private List<String> outgoingConnections = new ArrayList<>();
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Set<Permission> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    
    public IdentityReference getPartner() {
        return partner;
    }
    
    public void setPartner(IdentityReference partner) {
        this.partner = partner;
    }
    
    public List<String> getUrls() {
        return urls;
    }
    
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    
    public boolean isOnline() {
        return online;
    }
    
    public void setOnline(boolean online) {
        this.online = online;
    }
    
    public int getRetryPeriodMinutes() {
        return retryPeriodMinutes;
    }
    
    public void setRetryPeriodMinutes(int retryPeriodMinutes) {
        this.retryPeriodMinutes = retryPeriodMinutes;
    }
    
    public int getLoadFactor() {
        return loadFactor;
    }
    
    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }
    
    public List<String> getIncomingConnections() {
        return incomingConnections;
    }
    
    public void setIncomingConnections(List<String> incomingConnections) {
        this.incomingConnections = incomingConnections;
    }
    
    public List<String> getOutgoingConnections() {
        return outgoingConnections;
    }
    
    public void setOutgoingConnections(List<String> outgoingConnections) {
        this.outgoingConnections = outgoingConnections;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((incomingConnections == null) ? 0 : incomingConnections.hashCode());
        result = prime * result + loadFactor;
        result = prime * result + (online ? 1231 : 1237);
        result = prime * result + ((outgoingConnections == null) ? 0 : outgoingConnections.hashCode());
        result = prime * result + ((partner == null) ? 0 : partner.hashCode());
        result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
        result = prime * result + retryPeriodMinutes;
        result = prime * result + ((urls == null) ? 0 : urls.hashCode());
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
        NodeReference other = (NodeReference) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (incomingConnections == null) {
            if (other.incomingConnections != null)
                return false;
        } else if (!incomingConnections.equals(other.incomingConnections))
            return false;
        if (loadFactor != other.loadFactor)
            return false;
        if (online != other.online)
            return false;
        if (outgoingConnections == null) {
            if (other.outgoingConnections != null)
                return false;
        } else if (!outgoingConnections.equals(other.outgoingConnections))
            return false;
        if (partner == null) {
            if (other.partner != null)
                return false;
        } else if (!partner.equals(other.partner))
            return false;
        if (permissions == null) {
            if (other.permissions != null)
                return false;
        } else if (!permissions.equals(other.permissions))
            return false;
        if (retryPeriodMinutes != other.retryPeriodMinutes)
            return false;
        if (urls == null) {
            if (other.urls != null)
                return false;
        } else if (!urls.equals(other.urls))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NodeReference [id=" + id + ", permissions=" + permissions + ", partner=" + partner + ", urls=" + urls
                + ", online=" + online + ", retryPeriodMinutes=" + retryPeriodMinutes + ", loadFactor=" + loadFactor
                + ", incomingConnections=" + incomingConnections + ", outgoingConnections=" + outgoingConnections + "]";
    }
}
