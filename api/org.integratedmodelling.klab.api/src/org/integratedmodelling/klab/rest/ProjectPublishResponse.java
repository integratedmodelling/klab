package org.integratedmodelling.klab.rest;

public class ProjectPublishResponse {

	private boolean accepted;
	private String reasonForDenial;
	private String repositoryKey;
	private String repositoryUrl;
	private ProjectPublishRequest currentStatus;
	private long validFor;
	private String notificationUrl;

	/**
	 * This should be checked first. If it does not return true, no other methods
	 * should be used except {@link #getReasonForDenial()}.
	 * 
	 * @return
	 */
	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getRepositoryKey() {
		return repositoryKey;
	}

	public void setRepositoryKey(String repositoryKey) {
		this.repositoryKey = repositoryKey;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public ProjectPublishRequest getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(ProjectPublishRequest currentStatus) {
		this.currentStatus = currentStatus;
	}

	public long getValidFor() {
		return validFor;
	}

	public void setValidFor(long validFor) {
		this.validFor = validFor;
	}

	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public String getReasonForDenial() {
		return reasonForDenial;
	}

	public void setReasonForDenial(String reasonForDenial) {
		this.reasonForDenial = reasonForDenial;
	}

}
