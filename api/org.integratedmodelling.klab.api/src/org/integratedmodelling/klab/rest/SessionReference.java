package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

public class SessionReference {

	private String id;
	private long timeEstablished;
	private long timeLastJoined;
	private long timeRetrieved;
	private long timeLastActivity;
	private IdentityReference owner;
	Map<String, ObservationReference> rootObservations = new HashMap<>();

	public long getTimeEstablished() {
		return timeEstablished;
	}

	public void setTimeEstablished(long timeEstablished) {
		this.timeEstablished = timeEstablished;
	}

	public long getTimeLastJoined() {
		return timeLastJoined;
	}

	public void setTimeLastJoined(long timeLastJoined) {
		this.timeLastJoined = timeLastJoined;
	}

	public long getTimeRetrieved() {
		return timeRetrieved;
	}

	public void setTimeRetrieved(long timeRetrieved) {
		this.timeRetrieved = timeRetrieved;
	}

	public Map<String, ObservationReference> getRootObservations() {
		return rootObservations;
	}

	public void setRootObservations(Map<String, ObservationReference> rootObservations) {
		this.rootObservations = rootObservations;
	}

	public long getTimeLastActivity() {
		return timeLastActivity;
	}

	public void setTimeLastActivity(long timeLastActivity) {
		this.timeLastActivity = timeLastActivity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdentityReference getOwner() {
		return owner;
	}

	public void setOwner(IdentityReference owner) {
		this.owner = owner;
	}

}
