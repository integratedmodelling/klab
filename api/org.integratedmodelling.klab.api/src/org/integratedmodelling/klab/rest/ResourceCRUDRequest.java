package org.integratedmodelling.klab.rest;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.data.CRUDOperation;

public class ResourceCRUDRequest {

    private Set<String>   resourceUrns = new HashSet<>();
    private String        destinationProject;
    private CRUDOperation operation;
    private Map<String, String> parameters = new LinkedHashMap<>();
    private Map<String, ServicePrototype.Argument> attributes = new LinkedHashMap<>();

    public Set<String> getResourceUrns() {
        return resourceUrns;
    }

    public void setResourceUrns(Set<String> resourceUrns) {
        this.resourceUrns = resourceUrns;
    }
    
    public String getDestinationProject() {
        return destinationProject;
    }

    public void setDestinationProject(String destinationProject) {
        this.destinationProject = destinationProject;
    }

    public CRUDOperation getOperation() {
        return operation;
    }

    public void setOperation(CRUDOperation operation) {
        this.operation = operation;
    }

	public Map<String, ServicePrototype.Argument> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, ServicePrototype.Argument> attributes) {
		this.attributes = attributes;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
