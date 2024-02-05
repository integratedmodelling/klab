package org.integratedmodelling.klab.hub.api;

import java.util.Objects;

public class GroupSummary {

    private String id;
    private String name;
    private String description;
    private String iconUrl;
    private boolean optIn;
    
    public GroupSummary(String id, String name, String description, String iconUrl, boolean optIn) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.optIn = optIn;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public boolean isOptIn() {
        return optIn;
    }
    public void setOptIn(boolean optIn) {
        this.optIn = optIn;
    }
    @Override
    public int hashCode() {
        return Objects.hash(description, iconUrl, id, name, optIn);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupSummary other = (GroupSummary) obj;
        return Objects.equals(description, other.description) && Objects.equals(iconUrl, other.iconUrl)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name) && optIn == other.optIn;
    }
}
