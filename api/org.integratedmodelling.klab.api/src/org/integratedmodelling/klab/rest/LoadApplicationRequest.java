package org.integratedmodelling.klab.rest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoadApplicationRequest {

	private URL scriptUrl;
	private String behavior;
	private boolean test;
	private boolean stop = false;
	private Map<String, String> parameters = new HashMap<>();

	public LoadApplicationRequest() {
	}

	public LoadApplicationRequest(URL url, boolean isTest, String... kvParameters) {
		this.scriptUrl = url;
		this.test = isTest;
		if (kvParameters != null) {
			for (int i = 0; i < kvParameters.length; i++) {
				parameters.put(kvParameters[i], kvParameters[++i]);
			}
		}
	}
	
	public LoadApplicationRequest(String behavior, boolean isTest, boolean isStop) {
		this.behavior = behavior;
		this.test = isTest;
		this.stop = isStop;
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

	/**
	 * If stop is called, the behaviorId should be the application ID
	 * @return
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * If stop is called, the behaviorId should be the application ID
	 * @return
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
