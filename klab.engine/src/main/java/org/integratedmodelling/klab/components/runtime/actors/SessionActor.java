package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.engine.runtime.Session;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class SessionActor extends KlabActor {

	public static Behavior<KlabMessage> create(Session session) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session));
	}

	public SessionActor(ActorContext<KlabMessage> context, Session identity) {
		super(context, identity);
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
