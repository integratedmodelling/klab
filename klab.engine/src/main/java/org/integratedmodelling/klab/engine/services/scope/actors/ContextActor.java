package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.api.engine.IContextScope;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ContextActor extends AbstractBehavior<Void> {

    /*
     * ------- messages ----------------
     */

    static public class ObserveMessage {

    }

    /*
     * ------- methods -----------------
     */

    public static Behavior<Void> create(IContextScope scope, String id) {
        return Behaviors.setup(ctx -> new ContextActor(ctx, scope, id));
    }

    private ContextActor(ActorContext<Void> context, IContextScope scope, String id) {
        super(context);
    }

    @Override
    public Receive<Void> createReceive() {
        return newReceiveBuilder()
                // for now just stop
                .onSignal(PostStop.class, signal -> onPostStop()).build();
    }

    private ContextActor onPostStop() {
        getContext().getLog().info("k.LAB actor system stopped");
        return this;
    }

}
