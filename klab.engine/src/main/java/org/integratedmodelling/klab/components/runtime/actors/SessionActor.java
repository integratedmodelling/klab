package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.utils.NameGenerator;

import akka.actor.typed.ActorRef;
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

	public static Behavior<KlabMessage> create(Session session) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session));
	}

	public SessionActor(ActorContext<KlabMessage> context, Session identity) {
		super(context, identity);
	}

//	@Override
//	protected Behavior<KlabMessage> loadBehavior(Load message) {
//
//		IBehavior behavior = Actors.INSTANCE.getBehavior(message.behavior);
//
//		Layout view = Actors.INSTANCE.getView(behavior, this.identity);
//		if (!view.empty()) {
//			this.identity.setLayout(view);
//			((Session) this.identity).getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.SetupInterface,
//					view);
//		}
//
//		/*
//		 * spawn a new runtime actor and have it load the behavior
//		 */
//		ActorRef<KlabMessage> actor = getContext().spawn(RuntimeActor.create((Session) identity),
//				identity.getId() + NameGenerator.shortUUID());
//
//		actor.tell(message);
//
//		return Behaviors.same();
//
//	}

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
