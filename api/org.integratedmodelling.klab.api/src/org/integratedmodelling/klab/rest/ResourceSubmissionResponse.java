package org.integratedmodelling.klab.rest;

public class ResourceSubmissionResponse {

	public enum Status {
		REJECTED, ACCEPTED
	}

	private Status status;
	private String temporaryId;
	private String message;

	public String getTemporaryId() {
		return temporaryId;
	}

	public void setTemporaryId(String temporaryId) {
		this.temporaryId = temporaryId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
