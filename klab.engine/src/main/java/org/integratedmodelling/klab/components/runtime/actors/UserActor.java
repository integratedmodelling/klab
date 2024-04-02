package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior.UnknownMessage;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class UserActor extends KlabActor {

    /*
     * --------- messages --------------------
     */

    public static class CreateSession extends EmptyKlabMessage {

        String id;
        ActorRef<SessionCreated> replyTo;

        public CreateSession(String id/* , ISessionScope scope */, ActorRef<SessionCreated> replyTo) {
            this.id = id;
            this.replyTo = replyTo;
        }
    }

    public static class CreateApplication extends EmptyKlabMessage {
        String behavior;
        ActorRef<SessionCreated> replyTo;
        public CreateApplication(String behavior/* , ISessionScope scope */, ActorRef<SessionCreated> replyTo) {
            this.behavior = behavior;
            this.replyTo = replyTo;
        }
    }

    public static class SessionCreated extends EmptyKlabMessage {
        public ActorRef<KlabMessage> sessionAgent;
        public SessionCreated(ActorRef<KlabMessage> agent) {
            this.sessionAgent = agent;
        }
    }

    /*
     * --------- methods --------------------
     */

    public static Behavior<KlabMessage> create(IEngineUserIdentity user) {
        return Behaviors.setup(ctx -> new UserActor(ctx, user, user.getUsername()));
    }

//    public static Behavior<KlabMessage> create(IScope scope) {
//        return Behaviors.setup(ctx -> new UserActor(ctx, scope, scope.getUser().getUsername()));
//    }

    public UserActor(ActorContext<KlabMessage> context, IEngineUserIdentity user, String id) {
        super(context, user, id);
    }

//    public UserActor(ActorContext<KlabMessage> context, IScope scope, String id) {
//        super(context, scope.getUser(), id);
//    }

    @Override
    protected ReceiveBuilder<KlabMessage> configure() {
        return super.configure().onMessage(UnknownMessage.class, this::onUnknownMessage)
                .onMessage(CreateApplication.class, this::handleCreateApplication)
                .onMessage(CreateSession.class, this::handleCreateSession);
    }

    private Behavior<KlabMessage> onUnknownMessage(UnknownMessage message) {
        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.out.println("UNKNOWN MESSAGE " + message);
        }
        return Behaviors.same();
    }

    private Behavior<KlabMessage> handleCreateApplication(CreateApplication message) {
        message.replyTo.tell(new SessionCreated(null /* TODO */));
        return Behaviors.same();
    }

    private Behavior<KlabMessage> handleCreateSession(CreateSession message) {

//        ActorRef<KlabMessage> actor = getContext().spawn(Behaviors.supervise(SessionActor.create(message.id))
//                .onFailure(SupervisorStrategy.resume().withLoggingEnabled(true)), actorName);
        message.replyTo.tell(new SessionCreated(null /* TODO */));
        return Behaviors.same();
    }

    @Override
    protected UserActor onPostStop() {
        // TODO do something
        return this;
    }

}
