package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

public class ResourceContextualizationRequest {

    private String geometry;
    private String overallGeometry;
    private ResourceReference resource;
    private String semantics;
    private Map<String, String> urnParameters = new HashMap<>();

    public String getGeometry() {
        return geometry;
    }
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
    public ResourceReference getResource() {
        return resource;
    }
    public void setResource(ResourceReference resource) {
        this.resource = resource;
    }
    public String getSemantics() {
        return semantics;
    }
    public void setSemantics(String semantics) {
        this.semantics = semantics;
    }
    public Map<String, String> getUrnParameters() {
        return urnParameters;
    }
    public void setUrnParameters(Map<String, String> urnParameters) {
        this.urnParameters = urnParameters;
    }
    public String getOverallGeometry() {
        return overallGeometry;
    }
    public void setOverallGeometry(String overallGeometry) {
        this.overallGeometry = overallGeometry;
    }

}
