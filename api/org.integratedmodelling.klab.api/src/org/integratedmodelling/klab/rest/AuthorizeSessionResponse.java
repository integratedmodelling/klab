package org.integratedmodelling.klab.rest;

public class AuthorizeSessionResponse {
	
	private String sessionId;
	private String info;
	private long sessionStart;
	private long sessionAge;
	private long lastActivity;

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getSessionStart() {
		return sessionStart;
	}
	public void setSessionStart(long sessionStart) {
		this.sessionStart = sessionStart;
	}
	public long getSessionAge() {
		return sessionAge;
	}
	public void setSessionAge(long sessionAge) {
		this.sessionAge = sessionAge;
	}
	public long getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(long lastActivity) {
		this.lastActivity = lastActivity;
	}

}
