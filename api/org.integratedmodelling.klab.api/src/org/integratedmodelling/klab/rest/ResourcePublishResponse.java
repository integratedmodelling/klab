package org.integratedmodelling.klab.rest;

public class ResourcePublishResponse {

	private String originalUrn;
	private String temporaryId;
	private String error;

	public String getOriginalUrn() {
		return originalUrn;
	}

	public void setOriginalUrn(String originalUrn) {
		this.originalUrn = originalUrn;
	}

	public String getTemporaryId() {
		return temporaryId;
	}

	public void setTemporaryId(String temporaryId) {
		this.temporaryId = temporaryId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
