package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

public class UserInputResponse {

	private String requestId;
	private boolean cancelRun;
	private Map<String, String> values = new HashMap<>();

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean isCancelRun() {
		return cancelRun;
	}

	public void setCancelRun(boolean cancelRun) {
		this.cancelRun = cancelRun;
	}
}
