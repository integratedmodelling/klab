package org.integratedmodelling.klab.services.actors;

import org.integratedmodelling.klab.api.lang.kactors.KKActorsBehavior;
import org.integratedmodelling.klab.services.actors.messages.user.CreateApplication;
import org.integratedmodelling.klab.services.actors.messages.user.CreateSession;
import org.integratedmodelling.klab.services.engine.EngineService;

import io.reacted.core.reactors.ReActions.Builder;
import io.reacted.core.reactorsystem.ReActorContext;
import io.reacted.core.reactorsystem.ReActorRef;

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
        KKActorsBehavior behavior = EngineService.INSTANCE.getResourceService().resolveBehavior(message.getApplicationId(), message.getScope());
        if (behavior == null) {
            message.getScope().error("cannot find behavior " + message.getApplicationId());
            rctx.reply(ReActorRef.NO_REACTOR_REF);
        } else {
            
            rctx.spawnChild(new SessionAgent(behavior)).ifSuccess((ref) -> rctx.reply(ref));
        }
    }

}
