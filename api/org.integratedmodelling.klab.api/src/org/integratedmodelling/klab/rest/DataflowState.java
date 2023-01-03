package org.integratedmodelling.klab.rest;

public class DataflowState {

	public enum Status {
		WAITING, STARTED, CHANGED, FINISHED, ABORTED, /* this only sent by UIs for now */ INTERRUPTED, EMPTY
	}

	private String nodeId;
	private Status status;
	private boolean monitorable;
	private double progress;
	private String contextId;
	private int rating = -1;
	private String comment;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isMonitorable() {
		return monitorable;
	}

	public void setMonitorable(boolean monitorable) {
		this.monitorable = monitorable;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
