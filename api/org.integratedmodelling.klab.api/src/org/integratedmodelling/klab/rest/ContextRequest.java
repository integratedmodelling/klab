package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ContextRequest {

	private String geometry;
	private String urn;
	private boolean estimate;
	private List<String> scenarios = new ArrayList<>();

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public boolean isEstimate() {
		return estimate;
	}

	public void setEstimate(boolean estimate) {
		this.estimate = estimate;
	}

	public List<String> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<String> scenarios) {
		this.scenarios = scenarios;
	}

}
