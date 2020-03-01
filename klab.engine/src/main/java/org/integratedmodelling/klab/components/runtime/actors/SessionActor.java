package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.runtime.ISession;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class SessionActor extends KlabActor {

	public interface SessionCommand extends KlabMessage {

	}

	public static Behavior<KlabMessage> create(ISession session) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session));
	}

	public SessionActor(ActorContext<KlabMessage> context, ISession identity) {
		super(context, identity);
	}
	

	@Override
	public Receive<KlabMessage> createReceive() {
		return newReceiveBuilder().onSignal(PostStop.class, signal -> onPostStop()).build();
	}

	private SessionActor onPostStop() {
		// TODO Auto-generated method stub
		return this;
	}

}
