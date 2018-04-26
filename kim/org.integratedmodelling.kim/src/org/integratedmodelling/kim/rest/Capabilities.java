package org.integratedmodelling.kim.rest;

import java.util.ArrayList;
import java.util.List;

public class Capabilities {
    private String name;
    private String version;
    private String build;
    private IdentityReference owner;
    private List<ServiceCall> services = new ArrayList<>();
    private List<AuthorityReference> authorities = new ArrayList<>();
    private List<ComponentReference> staticComponents = new ArrayList<>();
    private List<ComponentReference> dynamicComponents = new ArrayList<>();
    private long refreshFrequencyMillis;
    private int loadFactor;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getBuild() {
        return build;
    }
    
    public void setBuild(String build) {
        this.build = build;
    }
    
    public IdentityReference getOwner() {
        return owner;
    }
    
    public void setOwner(IdentityReference owner) {
        this.owner = owner;
    }
    
    public List<ServiceCall> getServices() {
        return services;
    }
    
    public void setServices(List<ServiceCall> services) {
        this.services = services;
    }
    
    public List<AuthorityReference> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(List<AuthorityReference> authorities) {
        this.authorities = authorities;
    }
    
    public List<ComponentReference> getStaticComponents() {
        return staticComponents;
    }
    
    public void setStaticComponents(List<ComponentReference> staticComponents) {
        this.staticComponents = staticComponents;
    }
    
    public List<ComponentReference> getDynamicComponents() {
        return dynamicComponents;
    }
    
    public void setDynamicComponents(List<ComponentReference> dynamicComponents) {
        this.dynamicComponents = dynamicComponents;
    }
    
    public long getRefreshFrequencyMillis() {
        return refreshFrequencyMillis;
    }
    
    public void setRefreshFrequencyMillis(long refreshFrequencyMillis) {
        this.refreshFrequencyMillis = refreshFrequencyMillis;
    }
    
    public int getLoadFactor() {
        return loadFactor;
    }
    
    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime * result + ((build == null) ? 0 : build.hashCode());
        result = prime * result + ((dynamicComponents == null) ? 0 : dynamicComponents.hashCode());
        result = prime * result + loadFactor;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + (int) (refreshFrequencyMillis ^ (refreshFrequencyMillis >>> 32));
        result = prime * result + ((services == null) ? 0 : services.hashCode());
        result = prime * result + ((staticComponents == null) ? 0 : staticComponents.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        Capabilities other = (Capabilities) obj;
        if (authorities == null) {
            if (other.authorities != null)
                return false;
        } else if (!authorities.equals(other.authorities))
            return false;
        if (build == null) {
            if (other.build != null)
                return false;
        } else if (!build.equals(other.build))
            return false;
        if (dynamicComponents == null) {
            if (other.dynamicComponents != null)
                return false;
        } else if (!dynamicComponents.equals(other.dynamicComponents))
            return false;
        if (loadFactor != other.loadFactor)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (refreshFrequencyMillis != other.refreshFrequencyMillis)
            return false;
        if (services == null) {
            if (other.services != null)
                return false;
        } else if (!services.equals(other.services))
            return false;
        if (staticComponents == null) {
            if (other.staticComponents != null)
                return false;
        } else if (!staticComponents.equals(other.staticComponents))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Capabilities [name=" + name + ", version=" + version + ", build=" + build + ", owner=" + owner
                + ", services=" + services + ", authorities=" + authorities + ", staticComponents=" + staticComponents
                + ", dynamicComponents=" + dynamicComponents + ", refreshFrequencyMillis=" + refreshFrequencyMillis
                + ", loadFactor=" + loadFactor + "]";
    }
}
