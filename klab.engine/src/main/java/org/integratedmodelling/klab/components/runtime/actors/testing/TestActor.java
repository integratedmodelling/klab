package org.integratedmodelling.klab.components.runtime.actors.testing;

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SessionActor;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.engine.runtime.Session;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

/**
 * The specialized actor that executes the test actions in a behavior tagged as a 
 * testcase. Can intercept assertions and the like.
 * 
 * @author Ferd
 *
 */
public class TestActor extends SessionActor {

	public static Behavior<KlabMessage> create(Session session) {
		return Behaviors.setup(ctx -> new TestActor(ctx, session));
	}

	public TestActor(ActorContext<KlabMessage> context, Session session) {
		super(context, session, null);
		// session actor started
	}

	@Override
	protected Behavior<KlabMessage> handleLoadBehaviorMessage(Load message) {
		super.handleLoadBehaviorMessage(message);
		// TODO exec all tests
		return Behaviors.same();
	}

	
	
}
