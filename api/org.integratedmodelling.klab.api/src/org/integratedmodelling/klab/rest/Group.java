package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private String id;
	private String description;
	private String sshKey;
	private String iconUrl;
	private List<String> projectUrls = new ArrayList<>();
	private List<ObservableReference> observables = new ArrayList<>();
	private boolean worldview;
	
	// TODO add owner and possibly more authentication

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", description=" + description + "]";
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
	 * If true, the projects from this group are worldview projects.
	 * 
	 * @return
	 */
	public boolean isWorldview() {
		return worldview;
	}

	public void setWorldview(boolean worldview) {
		this.worldview = worldview;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

}
