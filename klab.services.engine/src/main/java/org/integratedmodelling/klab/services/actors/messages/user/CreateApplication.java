package org.integratedmodelling.klab.services.actors.messages.user;

import java.io.Serializable;

import org.integratedmodelling.klab.api.engine.IScope;

public class CreateApplication implements Serializable {

    private String applicationId;
    private IScope scope;

    public CreateApplication() {
    }

    public CreateApplication(IScope scope, String sessionId) {
        this.scope = scope;
        this.applicationId = sessionId;
    }

    private static final long serialVersionUID = -6105256439472164152L;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public IScope getScope() {
        return scope;
    }

    public void setScope(IScope scope) {
        this.scope = scope;
    }

}
