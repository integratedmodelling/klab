package org.integratedmodelling.klab.rest;

public class ObservationRequest {

	private String urn;
	private String contextId;
	private String searchContextId;

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

}
