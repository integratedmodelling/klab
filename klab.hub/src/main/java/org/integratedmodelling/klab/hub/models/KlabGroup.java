package org.integratedmodelling.klab.hub.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "KlabGroups")
public class KlabGroup {

	@Id
	private String id;

	private String description;

	private String sshKey;

	@NotEmpty
	private List<String> projectUrls;

	@NotEmpty
	private Boolean worldview;

	@Reference
	private List<Observable> observables;
	
	private String iconUrl;
	
    @Enumerated(EnumType.STRING)
    Role roleRequirement;
    
    @DBRef
    private List<KlabGroup> dependsOn;

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
	
	public KlabGroup() {
	}

	public KlabGroup(String id) {
		this.id = id;
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

}
