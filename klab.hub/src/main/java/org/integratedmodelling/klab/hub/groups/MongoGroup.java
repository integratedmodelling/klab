package org.integratedmodelling.klab.hub.groups;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Groups")
public class MongoGroup {

	@Id
	private String id;
	
	@Indexed(unique = true)
	private String groupName;

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
    
    private List<String> dependsOn;

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
	
	public MongoGroup() {
	}

	public MongoGroup(String id) {
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<String> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(List<String> dependsOn) {
		this.dependsOn = dependsOn;
	}
	
	
}
