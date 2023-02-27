package org.integratedmodelling.klab.services.actors.messages.user;

import java.io.Serializable;

import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;

public class CreateApplication implements Serializable {

    private String applicationId;
    private KScope scope;

    public CreateApplication() {
    }

    public CreateApplication(KScope scope, String sessionId) {
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

    public KScope getScope() {
        return scope;
    }

    public void setScope(KScope scope) {
        this.scope = scope;
    }

}
