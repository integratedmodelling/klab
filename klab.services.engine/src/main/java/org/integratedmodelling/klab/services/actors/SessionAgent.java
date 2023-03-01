package org.integratedmodelling.klab.services.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.components.runtime.actors.vm.KActorsVM;

import io.reacted.core.messages.reactors.ReActorInit;
import io.reacted.core.messages.reactors.ReActorStop;
import io.reacted.core.reactorsystem.ReActorContext;

public class SessionAgent extends KAgent {

    KActorsVM vm;

    public SessionAgent(String name) {
        super(name);
    }

    public SessionAgent(IBehavior application) {
        super(application.getName());
        // TODO create VM (must be quick)
    }

    @Override
    protected void initialize(ReActorContext rctx, ReActorInit message) {
        super.initialize(rctx, message);
        // TODO start VM if not null
    }

    @Override
    protected void stop(ReActorContext rctx, ReActorStop message) {
        super.stop(rctx, message);
        // TODO stop VM if not null, notify listeners
    }

}
