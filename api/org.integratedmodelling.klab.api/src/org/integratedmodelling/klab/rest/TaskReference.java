package org.integratedmodelling.klab.rest;

public class TaskReference {

	private String id;
	private String parentId;
	private String urn;
	private String description;
	private String error;

	public TaskReference() {
	}
	
	public TaskReference(TaskReference other) {
		this.id = other.id;
		this.parentId = other.parentId;
		this.urn = other.urn;
		this.description = other.description;
		this.error = other.error;
	}
	
	// TODO provenance info - agent, cause etc
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

    @Override
    public String toString() {
        return "TaskReference [id=" + id + ", description=" + description + "]";
    }

}
