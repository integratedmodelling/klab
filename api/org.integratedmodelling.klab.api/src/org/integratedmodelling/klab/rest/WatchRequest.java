package org.integratedmodelling.klab.rest;

public class WatchRequest {

	private boolean active;
	private EngineEvent.Type eventType;
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

	public EngineEvent.Type getEventType() {
		return eventType;
	}

	public void setEventType(EngineEvent.Type eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "WatchRequest [active=" + active + ", eventType=" + eventType + ", observationId=" + observationId
				+ ", rootContextId=" + rootContextId + "]";
	}
	
	

}
