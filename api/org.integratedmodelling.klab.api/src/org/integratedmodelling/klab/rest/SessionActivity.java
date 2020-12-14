package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.rest.DataflowState.Status;

/**
 * Descriptor of a session activity. Used to keep and replay history and to
 * communicate usage for statistics.
 * 
 * @author Ferd
 *
 */
public class SessionActivity {

	public static class ResourceActivity {

		private String urn;
		private long averageTimeMs;
		private long minTimeMs;
		private long maxTimeMs;
		private Set<String> nodes = new LinkedHashSet<>();
		private Set<String> errors = new LinkedHashSet<>();
		private int nCalls;
		
		public String getUrn() {
			return urn;
		}

		public void setUrn(String urn) {
			this.urn = urn;
		}

		public long getAverageTimeMs() {
			return averageTimeMs;
		}

		public void setAverageTimeMs(long averageTimeMs) {
			this.averageTimeMs = averageTimeMs;
		}

		public long getMinTimeMs() {
			return minTimeMs;
		}

		public void setMinTimeMs(long minTimeMs) {
			this.minTimeMs = minTimeMs;
		}

		public long getMaxTimeMs() {
			return maxTimeMs;
		}

		public void setMaxTimeMs(long maxTimeMs) {
			this.maxTimeMs = maxTimeMs;
		}

		public Set<String> getNodes() {
			return nodes;
		}

		public void setNodes(Set<String> nodes) {
			this.nodes = nodes;
		}

		public Set<String> getErrors() {
			return errors;
		}

		public void setErrors(Set<String> errors) {
			this.errors = errors;
		}

		public int getnCalls() {
			return nCalls;
		}

		public void setnCalls(int nCalls) {
			this.nCalls = nCalls;
		}

		
		
	}

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
	private String stackTrace;
	private Map<String,ResourceActivity> resourceActivities = new LinkedHashMap<>();
	private Map<String, ResourceActivity> modelActivities = new LinkedHashMap<>();
	private String parentActivityId;
	private String activityId;
	
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

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Map<String, ResourceActivity> getResourceActivities() {
		return resourceActivities;
	}

	public void setResourceActivities(Map<String, ResourceActivity> resourceActivities) {
		this.resourceActivities = resourceActivities;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getParentActivityId() {
		return parentActivityId;
	}

	public void setParentActivityId(String parentActivityId) {
		this.parentActivityId = parentActivityId;
	}

	public Map<String,ResourceActivity> getModelActivities() {
		return modelActivities;
	}

	public void setModelActivities(Map<String,ResourceActivity> modelActivities) {
		this.modelActivities = modelActivities;
	}

}
