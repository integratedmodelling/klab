package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Session data. Does not include the ID as that's the authentication token for
 * info requests or is sent back along with these data after session
 * authorization.
 * 
 * @author Ferd
 *
 */
public class SessionReference {

	private long timeEstablished;
	private long timeLastJoined;
	private long timeRetrieved;
	private long timeLastActivity;
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

}
