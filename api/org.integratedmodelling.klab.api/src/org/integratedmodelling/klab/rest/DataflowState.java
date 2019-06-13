package org.integratedmodelling.klab.rest;

public class DataflowState {

	public enum Status {
		STARTED, FINISHED, ABORTED
	}

	private String nodeId;
	private Status status;
	private boolean monitorable;
	private double progress;
	private String contextId;

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

}
