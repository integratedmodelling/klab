package org.integratedmodelling.klab.rest;

public class Notification {

	private String level;
	private long timestamp;
	private String message;
	
	public Notification() {}
	
	public Notification(String message2, String level2, long timestamp2) {
		this.message = message2;
		this.level = level2;
		this.timestamp = timestamp2;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
