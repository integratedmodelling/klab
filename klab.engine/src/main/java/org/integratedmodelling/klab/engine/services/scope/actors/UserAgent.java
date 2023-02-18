package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;
import org.integratedmodelling.klab.components.runtime.actors.EmptyKlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabAgent;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior.UnknownMessage;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class UserAgent extends KlabAgent {

    /*
     * --------- messages --------------------
     */

    public static class CreateSession extends EmptyKlabMessage {

        String id;
        ActorRef<SessionCreated> replyTo;
		ISessionScope scope;

        public CreateSession(String id, ISessionScope scope, ActorRef<SessionCreated> replyTo) {
            this.id = id;
            this.scope = scope;
            this.replyTo = replyTo;
        }
    }

    public static class CreateApplication extends EmptyKlabMessage {
        String behavior;
        ISessionScope scope;
        ActorRef<SessionCreated> replyTo;
        public CreateApplication(String behavior, ISessionScope scope, ActorRef<SessionCreated> replyTo) {
            this.behavior = behavior;
            this.replyTo = replyTo;
            this.scope = scope;
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

    public static Behavior<KlabMessage> create(IScope scope) {
        return Behaviors.setup(ctx -> new UserAgent(ctx, scope));
    }

    public UserAgent(ActorContext<KlabMessage> context, IScope scope) {
        super(context, scope);
    }

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
        IBehavior application = scope.getResources().resolveBehavior(message.behavior, scope);
        ActorRef<KlabMessage> actor = getContext().spawn(SessionAgent.create(message.scope, application), message.behavior);
        message.replyTo.tell(new SessionCreated(actor));
        return Behaviors.same();
    }

    private Behavior<KlabMessage> handleCreateSession(CreateSession message) {
    	ActorRef<KlabMessage> actor = getContext().spawn(SessionAgent.create(message.scope), message.id);
        message.replyTo.tell(new SessionCreated(actor));
        return Behaviors.same();
    }

    @Override
    protected UserAgent onPostStop() {
        // TODO do something
        return this;
    }

}
