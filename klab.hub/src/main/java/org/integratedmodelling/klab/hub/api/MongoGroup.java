package org.integratedmodelling.klab.hub.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Groups")
@TypeAlias("MongoGroup")
public class MongoGroup extends GenericModel{

	private String description;

	private String sshKey;

	private List<String> projectUrls;

	@NotNull
	private Boolean worldview;

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

	public Boolean getWorldview() {
		return worldview;
	}

	public void setWorldview(Boolean worldview) {
		this.worldview = worldview;
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


	
	
}
