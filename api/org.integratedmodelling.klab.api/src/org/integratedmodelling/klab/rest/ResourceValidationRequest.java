package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResourceValidationRequest {

	private List<String> urns = new ArrayList<>();

	public ResourceValidationRequest() {
	}

	public ResourceValidationRequest(Collection<String> urns) {
		this.urns.addAll(urns);
	}

	public List<String> getUrns() {
		return urns;
	}

	public void setUrns(List<String> urns) {
		this.urns = urns;
	}

}
