package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class MappingReference {

    private IArtifact.Type keyType;
    private IArtifact.Type valueType;
    private Map<String, String> mappings = new HashMap<>();
    
    public IArtifact.Type getKeyType() {
        return keyType;
    }
    public void setKeyType(IArtifact.Type keyType) {
        this.keyType = keyType;
    }
    public IArtifact.Type getValueType() {
        return valueType;
    }
    public void setValueType(IArtifact.Type valueType) {
        this.valueType = valueType;
    }
    public Map<String, String> getMappings() {
        return mappings;
    }
    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }

}
