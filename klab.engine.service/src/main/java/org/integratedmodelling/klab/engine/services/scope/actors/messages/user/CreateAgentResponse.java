package org.integratedmodelling.klab.engine.services.scope.actors.messages.user;

import java.io.Serializable;

import io.reacted.core.reactorsystem.ReActorRef;

public class CreateAgentResponse implements Serializable {

    private static final long serialVersionUID = -2163634037409987704L;

    private ReActorRef agent;

    public CreateAgentResponse() {}
    
    public CreateAgentResponse(ReActorRef ref) {
        this.agent = ref;
    }

    public ReActorRef getAgent() {
        return agent;
    }

    public void setAgent(ReActorRef agent) {
        this.agent = agent;
    }

}
