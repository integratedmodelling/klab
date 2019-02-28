package org.integratedmodelling.klab.rest;

public class GlobalActionRequest {

	// Action to provide
	private ActionReference action;
	// context ID: if null, action is for the global menu
	private String contextId;

	public ActionReference getAction() {
		return action;
	}

	public void setAction(ActionReference action) {
		this.action = action;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

}
