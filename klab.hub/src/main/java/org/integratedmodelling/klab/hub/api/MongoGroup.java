package org.integratedmodelling.klab.hub.api;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Document(collection = "Groups")
@TypeAlias("MongoGroup")
public class MongoGroup extends GenericModel{

	private String description;

	private String sshKey;

	private List<String> projectUrls;

	private boolean worldview;

	@Reference
	private List<Observable> observables;
	
	private String iconUrl;
	
    @Enumerated(EnumType.STRING)
    Role roleRequirement;
    
    private List<String> dependsOn;
    
    /*
     * If true user should be able to add this
     * group to themselves
     */
    private boolean optIn;
    
    /* 
     *If true this group is added to new users
     */
    private boolean preliminary;
    
    /* 
     *Max number of bytes allowed to be uploaded by members of this group.
     *Apples and Apples comparison for the multipart class size method.
     */
    private long maxUpload = 1073741824;

    /* 
     *Limit of upload, should be communicated to the node
     *and checked on user uploads.
     */
    private long uploadLimit;
    
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSshKey() {
		return sshKey;
	}

	public void setSshKey(String sshKey) {
		this.sshKey = sshKey;
	}

	public List<String> getProjectUrls() {
		return projectUrls;
	}

	public void setProjectUrls(List<String> projectUrls) {
		this.projectUrls = projectUrls;
	}

	public List<Observable> getObservables() {
		return observables;
	}

	public void setObservables(List<Observable> observables) {
		this.observables = observables;
	}
	
	public MongoGroup() {
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Role getRoleRequirement() {
		return roleRequirement;
	}

	public void setRoleRequirement(Role roleRequirement) {
		this.roleRequirement = roleRequirement;
	}
	
	public List<ObservableReference> getObservableReferences() {
		List<ObservableReference> observableList = new ArrayList<>();
		for (Observable obs : observables) {
			observableList.add(obs.getObservableReference());
		}
		return observableList;
	}

	public List<String> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(List<String> dependsOn) {
		this.dependsOn = dependsOn;
	}

	public boolean isOptIn() {
		return optIn;
	}

	public void setOptIn(boolean optIn) {
		this.optIn = optIn;
	}

	public boolean isPreliminary() {
		return preliminary;
	}

	public void setPreliminary(boolean preliminary) {
		this.preliminary = preliminary;
	}

	/**
	 * @return the worldview
	 */
	public boolean isWorldview() {
		return worldview;
	}

	/**
	 * @param worldview the worldview to set
	 */
	public void setWorldview(boolean worldview) {
		this.worldview = worldview;
	}

	/**
	 * @return the uploadLimit
	 */
	public long getUploadLimit() {
		return uploadLimit;
	}

	/**
	 * @param uploadLimit the uploadLimit to set
	 */
	public void setUploadLimit(long uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	public long getMaxUpload() {
		return maxUpload;
	}

	public void setMaxUpload(long maxUpload) {
		this.maxUpload = maxUpload;
	}
	
	
}
