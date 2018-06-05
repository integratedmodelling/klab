package org.integratedmodelling.klab.rest;

public class DataflowReference {
	
	private String taskId;
	private String kdlCode;
	
	public DataflowReference() {}
	
	public DataflowReference(String taskId, String kdlCode) {
		this.taskId = taskId;
		this.kdlCode = kdlCode;
	}

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getKdlCode() {
		return kdlCode;
	}
	public void setKdlCode(String kdlCode) {
		this.kdlCode = kdlCode;
	}

	
}
