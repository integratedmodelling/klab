package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;

/**
 * Sent from the engine to a client when a selection is made by the client from a semantic search,
 * reporting the current status of the observable being built.
 * 
 * @author Ferd
 *
 */
public class QueryStatusResponse {

    private List<StyledKimToken> code = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    private String contextId;
    private IKimConcept.Type currentType;
    private String description;

    public List<StyledKimToken> getCode() {
        return code;
    }
    public void setCode(List<StyledKimToken> code) {
        this.code = code;
    }

    public String getContextId() {
        return contextId;
    }
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public IKimConcept.Type getCurrentType() {
        return currentType;
    }
    public void setCurrentType(IKimConcept.Type currentType) {
        this.currentType = currentType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
