package org.integratedmodelling.klab.rest;

public class InterruptTask {

	private boolean forceInterruption;
	private String taskId;
	
	public boolean isForceInterruption() {
		return forceInterruption;
	}
	public void setForceInterruption(boolean forceInterruption) {
		this.forceInterruption = forceInterruption;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
