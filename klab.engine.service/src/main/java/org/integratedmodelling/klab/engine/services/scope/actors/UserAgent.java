package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.engine.services.scope.actors.messages.user.CreateAgentResponse;
import org.integratedmodelling.klab.engine.services.scope.actors.messages.user.CreateApplication;
import org.integratedmodelling.klab.engine.services.scope.actors.messages.user.CreateSession;

import io.reacted.core.reactors.ReActions.Builder;
import io.reacted.core.reactorsystem.ReActorContext;

public class UserAgent extends KAgent {

    public UserAgent(String name) {
        super(name);
    }

    @Override
    protected Builder setBehavior() {
        return super.setBehavior()
                .reAct(CreateSession.class, this::createSession)
                .reAct(CreateApplication.class, this::createApplication);
    }

    private void createSession(ReActorContext rctx, CreateSession message) {
        rctx.spawnChild(new SessionAgent(message.getSessionId())).ifSuccess((ref) -> rctx.reply(new CreateAgentResponse(ref)));
    }
    
    private void createApplication(ReActorContext rctx, CreateApplication message) {
        
    }


    
}
