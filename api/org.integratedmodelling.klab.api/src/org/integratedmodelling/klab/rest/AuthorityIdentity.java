package org.integratedmodelling.klab.rest;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IAuthority;

public class AuthorityIdentity implements IAuthority.Identity {

	private String id;
	private String conceptName;
	private List<String> parentIds;
	private String label;
	private String description;
	private float score = 1.0f;
	private String error;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getConceptName() {
		return conceptName;
	}

	@Override
	public List<String> getParentIds() {
		return parentIds;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public float getScore() {
		return score;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public void setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
