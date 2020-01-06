package org.integratedmodelling.klab.rest;

/**
 * The resource submission made to a node from an engine when the resource is
 * defined through a single resource.json file.
 * 
 * @author Ferd
 *
 */
public class ResourceSubmission {

	private String temporaryId;
	private ResourceReference data;

	public String getTemporaryId() {
		return temporaryId;
	}

	public void setTemporaryId(String temporaryId) {
		this.temporaryId = temporaryId;
	}

	public ResourceReference getData() {
		return data;
	}

	public void setData(ResourceReference data) {
		this.data = data;
	}

}
