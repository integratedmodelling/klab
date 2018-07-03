package org.integratedmodelling.klab.rest;

import java.util.logging.Level;

public class Notification {

	private Level level;
	private long timestamp;
	private String message;
	
	public Notification() {}
	
	public Notification(String message2, Level level2, long timestamp2) {
		this.message = message2;
		this.level = level2;
		this.timestamp = timestamp2;
	}
	
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
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
