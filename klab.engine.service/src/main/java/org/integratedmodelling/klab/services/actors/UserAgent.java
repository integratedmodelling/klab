package org.integratedmodelling.klab.services.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.services.actors.messages.user.CreateApplication;
import org.integratedmodelling.klab.services.actors.messages.user.CreateSession;

import io.reacted.core.reactors.ReActions.Builder;
import io.reacted.core.reactorsystem.ReActorContext;

public class UserAgent extends KAgent {

    public UserAgent(String name) {
        super(name);
    }

    @Override
    protected Builder setBehavior() {
        return super.setBehavior().reAct(CreateSession.class, this::createSession).reAct(CreateApplication.class,
                this::createApplication);
    }

    private void createSession(ReActorContext rctx, CreateSession message) {
        rctx.spawnChild(new SessionAgent(message.getSessionId())).ifSuccess((ref) -> rctx.reply(ref));
    }

    private void createApplication(ReActorContext rctx, CreateApplication message) {
        IBehavior behavior = message.getScope().getResources().resolveBehavior(message.getApplicationId(), message.getScope());
        if (behavior == null) {
            /*
             * TODO reply with error - decide how to best do that (configuration of agent should
             * have a universal receiver) - or just throw an exception to be captured outside?
             */
        }
        rctx.spawnChild(new SessionAgent(behavior)).ifSuccess((ref) -> rctx.reply(ref));
    }

}
