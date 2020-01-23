package org.integratedmodelling.klab.rest;

public class ResourcePublishResponse {

	private String originalUrn;
	private String ticketId;
	private String error;

	public String getOriginalUrn() {
		return originalUrn;
	}

	public void setOriginalUrn(String originalUrn) {
		this.originalUrn = originalUrn;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String temporaryId) {
		this.ticketId = temporaryId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
