package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

public class ResourceOperationRequest {

	public enum Standard {
		Revalidate
	}

	private String urn;
	/**
	 * This is either the text value of one of the Standard operations or another
	 * string that is passed to the adapter for adapter-specific operations.
	 */
	private String operation;

	/**
	 * parameters for the operation, if needed.
	 */
	private Map<String, String> parameters = new HashMap<>();

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
