package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ObservationRequest {

	private String urn;
	private String contextId;
	private String searchContextId;
	private boolean estimate;
	private long estimatedCost;
	private List<String> scenarios = new ArrayList<>();
	// observable -> value (as string) for single-valued states to inject before
	// making the main observation
	private Map<String, String> states = new LinkedHashMap<>();
	// observable -> geometry for objects to inject before making the main
	// observation
	private Map<String, String> objects = new LinkedHashMap<>();

	public ObservationRequest() {
		this.estimatedCost = -1;
	}

	public ObservationRequest(String urn, String contextId, String contextSearchId) {
		this.estimatedCost = -1;
		this.urn = urn;
		this.contextId = contextId;
		this.searchContextId = contextSearchId;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getSearchContextId() {
		return searchContextId;
	}

	public void setSearchContextId(String searchContextId) {
		this.searchContextId = searchContextId;
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

	@Override
	public String toString() {
		return "ObservationRequest [urn=" + urn + ", contextId=" + contextId + ", searchContextId=" + searchContextId
				+ ", scenarios=" + scenarios + "]";
	}

	public boolean isEstimate() {
		return estimate;
	}

	public void setEstimate(boolean estimate) {
		this.estimate = estimate;
	}

	public long getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(long estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public Map<String, String> getStates() {
		return states;
	}

	public void setStates(Map<String, String> states) {
		this.states = states;
	}

	public Map<String, String> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, String> objects) {
		this.objects = objects;
	}

}
