package org.integratedmodelling.klab.rest;

public class ObservationRequest {
	
	private String urn;
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
	
}
