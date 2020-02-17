package org.integratedmodelling.klab.components.runtime.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class SessionActor extends AbstractBehavior<Void> {

	public static Behavior<Void> create() {
		return Behaviors.setup(SessionActor::new);
	}

	public SessionActor(ActorContext<Void> context) {
		super(context);
		// session actor started
	}

	@Override
	public Receive<Void> createReceive() {
		return newReceiveBuilder().onSignal(PostStop.class, signal -> onPostStop()).build();
	}

	private SessionActor onPostStop() {
		// TODO Auto-generated method stub
		return this;
	}

}
