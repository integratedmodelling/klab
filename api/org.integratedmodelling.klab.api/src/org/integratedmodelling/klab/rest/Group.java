package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Group {

	private String id;
	private String name;
	private String description;
	private String iconUrl;
	private String sshKey;
	private long defaultExpirationTime;
	private boolean worldview;
	private boolean optIn;
	private boolean complimentary;
	private long maxUpload;
	private List<String> projectUrls = new ArrayList<String>();
	private List<ObservableReference> observables = new ArrayList<ObservableReference>();
	private Set<CustomPropertyRest> customProperties= new HashSet<CustomPropertyRest>();
	public Map<String, CustomPropertyRest> customPropertyMap = new HashMap<>();
	private List<String> dependsOn = new ArrayList<String>();

	public Group() {
	}

	public Group(String id) {
		this.id = id;
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

	/**
	 * Groups may be associated to a SSH key for uploading projects in Git
	 * repositories.
	 * 
	 * @return
	 */
	public String getSshKey() {
		return sshKey;
	}

	public void setSshKey(String sshKey) {
		this.sshKey = sshKey;
	}

	/**
	 * These are the Git URLs of any projects that the group requires in the
	 * workspace for its members. Others may become available through searches when
	 * needed.
	 * 
	 * @return
	 */
	public List<String> getProjectUrls() {
		return projectUrls;
	}

	public void setProjectUrls(List<String> projectUrls) {
		this.projectUrls = projectUrls;
	}

	/**
	 * Observable queries that we deem of interest for members of this group. These
	 * provide a default for a new member, stored with the user profile and changing
	 * according to user preferences and history.
	 * 
	 * @return
	 */
	public List<ObservableReference> getObservables() {
		return observables;
	}

	public void setObservables(List<ObservableReference> observables) {
		this.observables = observables;
	}

	/**
	 * If true, the projects from this group are worldview projects.maxUpload
	 * 
	 * @return
	 */
	public boolean isWorldview() {
		return worldview;
	}

	public void setWorldview(boolean worldview) {
		this.worldview = worldview;
	}

	/**
	 * The public icon url for the group
	 * 
	 * @return
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public void setMaxUpload(long maxUpload) {
		this.maxUpload = maxUpload;
	}

	/**
	 * The max upload permitted for that group
	 * @return
	 */
	public long getMaxUpload() {
		return maxUpload;
	}

	/**
	 * Custom properties for the group
	 * @return
	 */
    public Set<CustomPropertyRest> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Set<CustomPropertyRest> customProperties) {
        this.customProperties = customProperties;
    }
    
    /**
     * Default expiration time for the group
     * The time will be add to the date of group/user assignment
     * @return
     */
    public long getDefaultExpirationTime() {
		return defaultExpirationTime;
	}

	public void setDefaultExpirationTime(long defaultExpirationTime) {
		this.defaultExpirationTime = defaultExpirationTime;
	}

	/**
	 * If true the group is automatic added on user request
	 * @return
	 */
	public boolean isOptIn() {
		return optIn;
	}

	public void setOptIn(boolean optIn) {
		this.optIn = optIn;
	}

	/**
	 * If true the group will be assigned automatically on user creation
	 * @return
	 */
	public boolean isComplimentary() {
		return complimentary;
	}

	public void setComplimentary(boolean complimentary) {
		this.complimentary = complimentary;
	}

	/**
	 * List of group IDs that are need
	 * @return
	 */
	public List<String> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(List<String> dependsOn) {
		this.dependsOn = dependsOn;
	}
	
	

	public Map<String, CustomPropertyRest> getCustomPropertyMap() {
        return customPropertyMap;
    }

    public void setCustomPropertyMap(Map<String, CustomPropertyRest> customPropertyMap) {
        this.customPropertyMap = customPropertyMap;
    }

    @Override
	public int hashCode() {
		return Objects.hash(complimentary, customProperties, defaultExpirationTime, description, iconUrl, id, maxUpload,
				observables, optIn, projectUrls, sshKey, worldview, dependsOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return complimentary == other.complimentary && Objects.equals(customProperties, other.customProperties)
				&& defaultExpirationTime == other.defaultExpirationTime
				&& Objects.equals(description, other.description) && Objects.equals(iconUrl, other.iconUrl)
				&& Objects.equals(id, other.id) && maxUpload == other.maxUpload
				&& Objects.equals(observables, other.observables) && optIn == other.optIn
				&& Objects.equals(projectUrls, other.projectUrls) && Objects.equals(sshKey, other.sshKey)
				&& worldview == other.worldview && Objects.equals(dependsOn, other.dependsOn);
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", description=" + description + "]";
	}


}
