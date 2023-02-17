package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.components.runtime.actors.EmptyKlabMessage;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Supervisor extends AbstractBehavior<KlabMessage> {

    /*
     * --------- messages --------------------
     */

    public static class CreateUser extends EmptyKlabMessage {

        String id;
        ActorRef<UserCreated> replyTo;
        IScope scope;

        public CreateUser(String id, IScope scope, ActorRef<UserCreated> replyTo) {
            this.id = id;
            this.scope = scope;
            this.replyTo = replyTo;
        }
    }

    public static class UserCreated extends EmptyKlabMessage {
        public ActorRef<KlabMessage> userAgent;
        public UserCreated(ActorRef<KlabMessage> agent) {
            this.userAgent = agent;
        }
    }

    /*
     * --------- methods --------------------
     */

    public static Behavior<KlabMessage> create() {
        return Behaviors.setup(Supervisor::new);
    }

    private Supervisor(ActorContext<KlabMessage> context) {
        super(context);
        Logging.INSTANCE.info("k.LAB actor system initialized");
    }

    private Behavior<KlabMessage> handleCreateUser(CreateUser message) {
        ActorRef<KlabMessage> user = getContext().spawn(UserAgent.create(message.scope), message.id);
        message.replyTo.tell(new UserCreated(user));
        return Behaviors.same();
    }
    
    @Override
    public Receive<KlabMessage> createReceive() {
        return newReceiveBuilder()
                // for now just stop
                .onMessage(CreateUser.class, this::handleCreateUser)
                .onSignal(PostStop.class, signal -> onPostStop()).build();
    }

    private Supervisor onPostStop() {
        getContext().getLog().info("k.LAB actor system stopped");
        return this;
    }
}
