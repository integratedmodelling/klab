package org.integratedmodelling.klab.engine.services.scope.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;
import org.integratedmodelling.klab.components.runtime.actors.EmptyKlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabAgent;
import org.integratedmodelling.klab.components.runtime.actors.RuntimeBehavior;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

/**
 * The session actor only does small things, like interacting with the monitor and, crucially,
 * spawning all other actors needed in a session. Essentially it's a cow actor dedicated to making
 * actors and running the actions in {@link RuntimeBehavior} - even behaviors running in a session
 * will be launched in a Runtime actor, to prevent deadlocks when an action run within a session
 * requires another actor to spawn in order to terminate.
 * 
 * @author Ferd
 *
 */
public class SessionAgent extends KlabAgent {

    /*
     * --------- messages --------------------
     */

    public static class CreateContext extends EmptyKlabMessage {
        String id;
        IContextScope scope;
        ActorRef<ContextCreated> replyTo;
        public CreateContext(String id, IContextScope scope, ActorRef<ContextCreated> replyTo) {
            this.id = id;
            this.scope = scope;
            this.replyTo = replyTo;
        }
    }

    public static class ContextCreated extends EmptyKlabMessage {
        public ActorRef<KlabMessage> contextAgent;
        public ContextCreated(ActorRef<KlabMessage> agent) {
            this.contextAgent = agent;
        }
    }
    /*
     * --------- methods --------------------
     */

    public static Behavior<KlabMessage> create(ISessionScope session) {
        return Behaviors.setup(ctx -> new SessionAgent(ctx, session));
    }

    public static Behavior<KlabMessage> create(ISessionScope scope, IBehavior application) {
        return Behaviors.setup(ctx -> new SessionAgent(ctx, scope, application));
    }

    public SessionAgent(ActorContext<KlabMessage> context, ISessionScope scope) {
        super(context, scope);
    }

    public SessionAgent(ActorContext<KlabMessage> context, ISessionScope scope, IBehavior application) {
        super(context, scope);
        /*
         * TODO load application
         */
    }

    @Override
    protected ReceiveBuilder<KlabMessage> configure() {
        return super.configure()
                .onMessage(CreateContext.class, this::handleCreateContext);
    }

    private Behavior<KlabMessage> handleCreateContext(CreateContext request) {
        return Behaviors.same();
    }
    
    
    @Override
    protected SessionAgent onPostStop() {
        // TODO do something
        return this;
    }

}
