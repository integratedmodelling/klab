package org.integratedmodelling.klab.rest;

/**
 * Sent by the scheduler any time it is started or reset.
 * @author ferdinando.villa
 *
 */
public class SchedulerNotification {

	public static enum Type {
		STARTED,
		FINISHED,
		RESET
	}
	
	private Type type;
	private String contextId;
	private long resolution;
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getContextId() {
		return contextId;
	}
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
	public long getResolution() {
		return resolution;
	}
	public void setResolution(long resolution) {
		this.resolution = resolution;
	}
	
	
}
