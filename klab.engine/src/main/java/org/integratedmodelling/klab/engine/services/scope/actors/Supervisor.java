package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.Logging;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Supervisor extends AbstractBehavior<Void> {

    public static Behavior<Void> create() {
        return Behaviors.setup(Supervisor::new);
    }

    private Supervisor(ActorContext<Void> context) {
        super(context);
        Logging.INSTANCE.info("k.LAB actor system initialized");
    }

    @Override
    public Receive<Void> createReceive() {
        return newReceiveBuilder()
                // for now just stop
                .onSignal(PostStop.class, signal -> onPostStop()).build();
    }

    private Supervisor onPostStop() {
        getContext().getLog().info("k.LAB actor system stopped");
        return this;
    }
}
