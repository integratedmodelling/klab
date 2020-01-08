package org.integratedmodelling.klab.rest;

public class ResourceSubmissionResponse {

	public enum Status {
		REJECTED, ACCEPTED
	}

	private Status status;
	private String ticket;
	private String message;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String temporaryId) {
		this.ticket = temporaryId;
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
