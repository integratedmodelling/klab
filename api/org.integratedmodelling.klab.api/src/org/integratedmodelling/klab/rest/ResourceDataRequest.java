package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ResourceDataRequest {

	private String urn;
	private String geometry;
	private List<AttributeReference> outputs = new ArrayList<>();

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	/**
	 * @return the outputs
	 */
	public List<AttributeReference> getOutputs() {
		return outputs;
	}

	/**
	 * @param outputs the outputs to set
	 */
	public void setOutputs(List<AttributeReference> outputs) {
		this.outputs = outputs;
	}

}
