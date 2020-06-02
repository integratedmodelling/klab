package org.integratedmodelling.klab.rest;

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
