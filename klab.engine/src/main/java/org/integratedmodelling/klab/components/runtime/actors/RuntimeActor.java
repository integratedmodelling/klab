package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.Session;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

/**
 * The session actor that actually runs a k.Actors behavior. Spawned at
 * loadBehavior by SessionActor and told to load the behavior. If the Session
 * actor ran the behavior directly, any action that requires it to spawn a child
 * would have to wait for the action to finish.
 * 
 * @author Ferd
 *
 */
public class RuntimeActor extends KlabActor {

	public static Behavior<KlabMessage> create(Session session, String appId) {
		return Behaviors.setup(ctx -> new RuntimeActor(ctx, session, appId));
	}

	public RuntimeActor(ActorContext<KlabMessage> context, Session identity, String appId) {
		super(context, identity, appId);
	}

	@Override
	protected ReceiveBuilder<KlabMessage> configure() {
		// TODO add all view messages and runtime messages
		return super.configure();
	}

	@Override
	protected RuntimeActor onPostStop() {
		// TODO do something
		return this;
	}

}
