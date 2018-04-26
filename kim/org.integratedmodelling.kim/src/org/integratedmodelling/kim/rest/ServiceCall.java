package org.integratedmodelling.kim.rest;

import java.util.HashMap;
import java.util.Map;

public class ServiceCall {
    private String name;
    private Map<String, Object> parameters = new HashMap<>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
