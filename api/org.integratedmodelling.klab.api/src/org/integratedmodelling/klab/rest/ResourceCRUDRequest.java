package org.integratedmodelling.klab.rest;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.data.CRUDOperation;

public class ResourceCRUDRequest {

    private Set<String>   resourceUrns = new HashSet<>();
    private String        sourceProject;
    private String        destinationProject;
    private CRUDOperation operation;

    public Set<String> getResourceUrns() {
        return resourceUrns;
    }

    public void setResourceUrns(Set<String> resourceUrns) {
        this.resourceUrns = resourceUrns;
    }

    public String getSourceProject() {
        return sourceProject;
    }

    public void setSourceProject(String sourceProject) {
        this.sourceProject = sourceProject;
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

}
