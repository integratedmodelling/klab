package org.integratedmodelling.klab.components.runtime.actors.testing;

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

	public static Behavior<Void> create() {
		return Behaviors.setup(TestActor::new);
	}

	public TestActor(ActorContext<Void> context) {
		super(context);
		// session actor started
	}

	@Override
	public Receive<Void> createReceive() {
		return newReceiveBuilder().onSignal(PostStop.class, signal -> onPostStop()).build();
	}

	private TestActor onPostStop() {
		// TODO Auto-generated method stub
		return this;
	}

}
