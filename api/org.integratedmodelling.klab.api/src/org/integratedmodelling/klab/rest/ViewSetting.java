package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

public class ViewSetting {

	enum Operation {
		Show, Hide, Enable, Disable
	}

	enum Target {
		Observation, View, Tree, Report, Dataflow
	}

	private Operation operation;
	private Target target;
	private String targetId;
	private Map<String, String> parameters = new HashMap<>();

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
