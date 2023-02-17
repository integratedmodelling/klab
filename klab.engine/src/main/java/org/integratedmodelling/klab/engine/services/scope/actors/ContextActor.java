package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.components.runtime.actors.SessionActor.CreateContext;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ContextActor extends AbstractBehavior<KlabMessage> {

    /*
     * ------- messages ----------------
     */
    IScale geometry = null;

    static public class ObserveMessage {

    }

    /*
     * ------- methods -----------------
     */

    public static Behavior<KlabMessage> create(IContextScope scope, String id) {
        return Behaviors.setup(ctx -> new ContextActor(ctx, scope, id));
    }

    private ContextActor(ActorContext<KlabMessage> context, IContextScope scope, String id) {
        super(context);
    }

    @Override
    public Receive<KlabMessage> createReceive() {
        return newReceiveBuilder()
                // for now just stop
                .onSignal(PostStop.class, signal -> onPostStop()).build();
    }

    private ContextActor onPostStop() {
        return this;
    }

}
