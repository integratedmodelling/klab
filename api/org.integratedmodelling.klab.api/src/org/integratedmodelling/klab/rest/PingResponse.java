package org.integratedmodelling.klab.rest;

/**
 * Short response to a ping includes a "default" local session to join, if one
 * has been established and the request comes from localhost.
 * 
 * @author ferdinando.villa
 *
 */
public class PingResponse {

	private String engineId;
	private String localSessionId;
	private boolean online;
	private long uptime;
	private long bootTime;
	private String version;
	private long freeMemory;
	private long totalMemory;
	private long processorCount;
	private long requestTime;
	
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

	public void setEngineId(String engineId) {
		this.engineId = engineId;
	}

	public String getEngineId() {
		return engineId;
	}

	public long getBootTime() {
		return bootTime;
	}

	public void setBootTime(long bootTime) {
		this.bootTime = bootTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getProcessorCount() {
		return processorCount;
	}

	public void setProcessorCount(long processorCount) {
		this.processorCount = processorCount;
	}

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

}
