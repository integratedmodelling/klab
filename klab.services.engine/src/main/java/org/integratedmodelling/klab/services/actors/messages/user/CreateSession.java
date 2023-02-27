package org.integratedmodelling.klab.services.actors.messages.user;

import java.io.Serializable;

import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;

public class CreateSession implements Serializable {

    private static final long serialVersionUID = -4721979530562111456L;

    private String sessionId;
    private KScope scope;

    public CreateSession() {
    }
    
    public CreateSession(KScope scope, String sessionId) {
        this.setScope(scope);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public KScope getScope() {
        return scope;
    }

    public void setScope(KScope scope) {
        this.scope = scope;
    }

}
