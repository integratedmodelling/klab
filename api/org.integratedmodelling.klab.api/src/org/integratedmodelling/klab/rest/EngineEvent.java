package org.integratedmodelling.klab.rest;

public class EngineEvent {

	public static enum Type {
		ResourceValidation
	}

	private Type type;
	private boolean started;
	private long timestamp;
	private String description;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "EngineEvent [type=" + type + ", started=" + started + "]";
	}

}
