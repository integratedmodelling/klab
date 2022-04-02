package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ContextRequest {

    private String geometry;
    private String contextType;
    private List<String> observables = new ArrayList<>();
    private List<String> scenarios = new ArrayList<>();
    private boolean estimate;
    private String estimateId;
    
    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public List<String> getObservables() {
        return observables;
    }

    public void setObservables(List<String> observables) {
        this.observables = observables;
    }

    public List<String> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<String> scenarios) {
        this.scenarios = scenarios;
    }

    public boolean isEstimate() {
        return estimate;
    }

    public void setEstimate(boolean estimate) {
        this.estimate = estimate;
    }

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

}
