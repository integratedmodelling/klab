package org.integratedmodelling.klab.rest;

public class WatchRequest {

	private boolean active;
	private String observationId;
	private String rootContextId;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getObservationId() {
		return observationId;
	}

	public void setObservationId(String observationId) {
		this.observationId = observationId;
	}

	public String getRootContextId() {
		return rootContextId;
	}

	public void setRootContextId(String rootContextId) {
		this.rootContextId = rootContextId;
	}

}
