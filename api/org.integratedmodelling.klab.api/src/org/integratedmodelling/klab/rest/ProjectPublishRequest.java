package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sent to a node when an engine wants to publish a project. Gets back
 * approval/denial and if approved, SSH keys and repository URL for pushing.
 * 
 * @author ferdinando.villa
 *
 */
public class ProjectPublishRequest {

	private String projectId;
	private boolean isWorldview;
	private String ownerGroup;
	private List<Group> permittedGroups = new ArrayList<>();
	private Map<String, Boolean> synchronizedUsage = new HashMap<>();

	/**
	 * ID of the project and the repository. If everything checks out any missing
	 * repositories will be created before a response is sent.
	 * 
	 * @return
	 */
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * Must specify if we want to contribute to the worldview for additional
	 * integrity checks after pushing.
	 * 
	 * @param isWorldview
	 */
	public boolean isWorldview() {
		return isWorldview;
	}

	public void setWorldview(boolean isWorldview) {
		this.isWorldview = isWorldview;
	}

	/**
	 * The ID of the group that owns or will own the repository. The request will be
	 * rejected if the repository exists and is owned by another group.
	 * 
	 * @return
	 */
	public String getOwnerGroup() {
		return ownerGroup;
	}

	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	/**
	 * Groups that are allowed to see the repository after push.
	 * 
	 * @return
	 */
	public List<Group> getPermittedGroups() {
		return permittedGroups;
	}

	public void setPermittedGroups(List<Group> permittedGroups) {
		this.permittedGroups = permittedGroups;
	}

	/**
	 * The keys in this map are the group IDs for the allowed groups; a
	 * corresponding value of true means the project will be synchronized on startup
	 * at the user's end. False (default if the map is empty) means that they will
	 * only be synchronized when a model from it is requested. Modules contributing
	 * concepts and high-bandwidth projects should set this to true.
	 * 
	 * @return
	 */
	public Map<String, Boolean> getSynchronizedUsage() {
		return synchronizedUsage;
	}

	public void setSynchronizedUsage(Map<String, Boolean> synchronizedUsage) {
		this.synchronizedUsage = synchronizedUsage;
	}

}
