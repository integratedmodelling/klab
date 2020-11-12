package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.rest.DataflowState.Status;

/**
 * Descriptor of a session activity. Used to keep and replay history and to
 * communicate usage for statistics.
 * 
 * @author Ferd
 *
 */
public class SessionActivity {

	private String user;
	private String sessionId;
	private long start;
	private long end;
	private Status status;
	private String urnObserved;
	private String geometrySet;
	private String contextId;
	private String dataflowCode;
	private String applicationLoaded;
	private List<String> scenarios = new ArrayList<>();
	private long datasize;
	private long load;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUrnObserved() {
		return urnObserved;
	}

	public void setUrnObserved(String urnObserved) {
		this.urnObserved = urnObserved;
	}

	public String getGeometrySet() {
		return geometrySet;
	}

	public void setGeometrySet(String geometrySet) {
		this.geometrySet = geometrySet;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public List<String> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<String> scenarios) {
		this.scenarios = scenarios;
	}

	public long getDatasize() {
		return datasize;
	}

	public void setDatasize(long datasize) {
		this.datasize = datasize;
	}

	public long getLoad() {
		return load;
	}

	public void setLoad(long load) {
		this.load = load;
	}

	public String getDataflowCode() {
		return dataflowCode;
	}

	public void setDataflowCode(String dataflowCode) {
		this.dataflowCode = dataflowCode;
	}

	public String getApplicationLoaded() {
		return applicationLoaded;
	}

	public void setApplicationLoaded(String applicationLoaded) {
		this.applicationLoaded = applicationLoaded;
	}

}
