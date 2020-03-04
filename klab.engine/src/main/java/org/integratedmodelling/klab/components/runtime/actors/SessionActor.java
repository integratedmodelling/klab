package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.components.runtime.actors.KlabMessages.Load;
import org.integratedmodelling.klab.components.runtime.actors.KlabMessages.Spawn;
import org.integratedmodelling.klab.engine.runtime.Session;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class SessionActor extends KlabActor {

	public static Behavior<KlabMessage> create(Session session) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session));
	}

	public SessionActor(ActorContext<KlabMessage> context, Session identity) {
		super(context, identity);
	}

	@Override
	public Receive<KlabMessage> createReceive() {
		return newReceiveBuilder()
				.onMessage(Load.class, this::loadBehavior)
				.onMessage(Spawn.class, this::createChild)
				.onSignal(PostStop.class, signal -> onPostStop()).build();
	}

	private SessionActor onPostStop() {
		// TODO Auto-generated method stub
		return this;
	}

}
