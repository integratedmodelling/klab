package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A mapping stores pairs of strings, where no assumptions about uniqueness are made.
 * 
 * @author Ferd
 *
 */
public class MappingReference {

    private IArtifact.Type keyType;
    private IArtifact.Type valueType;
    private List<Pair<String, String>> mappings = new ArrayList<>();

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
    public List<Pair<String, String>> getMappings() {
        return mappings;
    }
    public void setMappings(List<Pair<String, String>> mappings) {
        this.mappings = mappings;
    }

}
