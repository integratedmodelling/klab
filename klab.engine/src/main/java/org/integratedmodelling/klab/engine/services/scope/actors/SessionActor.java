package org.integratedmodelling.klab.engine.services.scope.actors;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.EmptyKlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.RuntimeBehavior;
import org.integratedmodelling.klab.engine.runtime.Session;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

/**
 * The session actor only does small things, like interacting with the monitor
 * and, crucially, spawning all other actors needed in a session. Essentially
 * it's a cow actor dedicated to making actors and running the actions in
 * {@link RuntimeBehavior} - even behaviors running in a session will be
 * launched in a Runtime actor, to prevent deadlocks when an action run within a
 * session requires another actor to spawn in order to terminate.
 * 
 * @author Ferd
 *
 */
public class SessionActor extends KlabActor {

    
    /*
     * --------- messages --------------------
     */
    
    public static class CreateContext extends EmptyKlabMessage {
        
    }
    
    /*
     * --------- methods  --------------------
     */

	public static Behavior<KlabMessage> create(Session session, String appId) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session, appId));
	}

	public SessionActor(ActorContext<KlabMessage> context, Session identity, String appId) {
		super(context, identity, appId);
	}

	@Override
	protected ReceiveBuilder<KlabMessage> configure() {
		// TODO add all view messages and runtime messages
		return super.configure();
	}

	@Override
	protected SessionActor onPostStop() {
		// TODO do something
		return this;
	}

}
