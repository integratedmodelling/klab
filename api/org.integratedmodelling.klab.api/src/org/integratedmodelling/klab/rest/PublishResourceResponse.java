package org.integratedmodelling.klab.rest;

public class PublishResourceResponse {

	private String originalUrn;
	private String temporaryId;

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

}
