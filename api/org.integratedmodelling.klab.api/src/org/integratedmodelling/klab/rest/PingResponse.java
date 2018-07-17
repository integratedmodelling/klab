package org.integratedmodelling.klab.rest;

/**
 * Short response to a ping includes a "default" local session to join, if one
 * has been established and the request comes from localhost.
 * 
 * @author ferdinando.villa
 *
 */
public class PingResponse {

	private String localSessionId;
	private boolean online;
	private long uptime;

	public String getLocalSessionId() {
		return localSessionId;
	}

	public void setLocalSessionId(String localSessionId) {
		this.localSessionId = localSessionId;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public long getUptime() {
		return uptime;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}

}
