package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple bean for basic requests to the engine from a client, e.g. open a
 * debugger.
 * 
 * @author Ferd
 *
 */
public class EngineAction {

	private String request;
	private Map<String, String> parameters = new HashMap<>();

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
