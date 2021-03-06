package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.runtime.rest.INotification;

public class Notification implements INotification {

	private String level;
	private long timestamp;
	private String message;
	private Type type = Type.None;
	private String identity;
	private String id;
	
	public Notification() {
	}

	public Notification(String message, String level) {
		this.message = message;
		this.level = level;
		this.timestamp = System.currentTimeMillis();
	}
	
	public Notification(String message2, String level2, long timestamp2) {
		this.message = message2;
		this.level = level2;
		this.timestamp = timestamp2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.INotification#getLevel()
	 */
	@Override
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.INotification#getTimestamp()
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.INotification#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Notification [level=" + level + ", message=" + message + ", identity=" + identity + ", type=" + type
				+ "]";
	}

	@Override
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
