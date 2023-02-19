package org.integratedmodelling.klab.engine.services.scope.actors.messages.user;

import java.io.Serializable;

public class CreateSession implements Serializable {

    private static final long serialVersionUID = -4721979530562111456L;

    private String sessionId;

    public CreateSession() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
