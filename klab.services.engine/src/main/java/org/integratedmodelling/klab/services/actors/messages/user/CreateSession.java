package org.integratedmodelling.klab.services.actors.messages.user;

import java.io.Serializable;

import org.integratedmodelling.klab.api.engine.IScope;

public class CreateSession implements Serializable {

    private static final long serialVersionUID = -4721979530562111456L;

    private String sessionId;
    private IScope scope;

    public CreateSession() {
    }
    
    public CreateSession(IScope scope, String sessionId) {
        this.setScope(scope);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public IScope getScope() {
        return scope;
    }

    public void setScope(IScope scope) {
        this.scope = scope;
    }

}
