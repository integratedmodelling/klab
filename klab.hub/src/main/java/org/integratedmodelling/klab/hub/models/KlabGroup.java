package org.integratedmodelling.klab.hub.models;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "KlabGroups")
public class KlabGroup {

	@Id
	String id;

	String description;

	String sshKey;

	@NotEmpty
	List<String> projectUrls;

	@NotEmpty
	Boolean worldview;

	@NotEmpty
	List<ObservableReference> observables;

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

	public List<ObservableReference> getObservables() {
		return observables;
	}

	public void setObservables(List<ObservableReference> observables) {
		this.observables = observables;
	}
	
	public KlabGroup() {
	}

	public KlabGroup(String id) {
		this.id = id;
	}

}
