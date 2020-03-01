package org.integratedmodelling.klab.components.runtime.actors.testing;

import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.actors.SessionActor;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * The specialized actor that executes the test actions in a behavior tagged as a 
 * testcase. Can intercept assertions and the like.
 * 
 * @author Ferd
 *
 */
public class TestActor extends SessionActor {

	public static Behavior<KlabMessage> create(ISession session) {
		return Behaviors.setup(ctx -> new TestActor(ctx, session));
	}

	public TestActor(ActorContext<KlabMessage> context, ISession session) {
		super(context, session);
		// session actor started
	}

	@Override
	public Receive<KlabMessage> createReceive() {
		return newReceiveBuilder().onSignal(PostStop.class, signal -> onPostStop()).build();
	}

	private TestActor onPostStop() {
		// TODO Auto-generated method stub
		return this;
	}

}
