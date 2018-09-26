package org.integratedmodelling.klab.rest;

import java.util.List;

public class EngineStatus {

	private long bootTime;
	private long freeMemory;
	private long totalMemory;
	private float loadFactor;
	private List<SessionReference> sessions;

	public long getBootTime() {
		return bootTime;
	}

	public void setBootTime(long bootTime) {
		this.bootTime = bootTime;
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

	public float getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(float loadFactor) {
		this.loadFactor = loadFactor;
	}

	public List<SessionReference> getSessions() {
		return sessions;
	}

	public void setSessions(List<SessionReference> sessions) {
		this.sessions = sessions;
	}

}
