package org.integratedmodelling.klab.rest;

public class ResourceOperationRequest {

	private String urn;
	private String operation;
	
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

}
