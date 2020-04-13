package org.integratedmodelling.klab.rest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RunScriptRequest {

	private URL scriptUrl;
	private String behavior;
	private boolean test;
	private Map<String, String> parameters = new HashMap<>();

	public RunScriptRequest() {
	}

	public RunScriptRequest(URL url, boolean isTest, String... kvParameters) {
		this.scriptUrl = url;
		this.test = isTest;
		if (kvParameters != null) {
			for (int i = 0; i < kvParameters.length; i++) {
				parameters.put(kvParameters[i], kvParameters[++i]);
			}
		}
	}
	
	public RunScriptRequest(String behavior, boolean isTest) {
		this.behavior = behavior;
		this.test = isTest;
	}

	public URL getScriptUrl() {
		return scriptUrl;
	}

	public void setScriptUrl(URL scriptUrl) {
		this.scriptUrl = scriptUrl;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

}
