package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.model.KActorsActionCall;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.ViewBehavior.KlabWidgetAction;
import org.integratedmodelling.klab.components.runtime.actors.behavior.BehaviorAction;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.View;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtil;

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

	@Override
	protected Behavior<KlabMessage> loadBehavior(Load message) {

		IBehavior behavior = Actors.INSTANCE.getBehavior(message.behavior);

		View view = Actors.INSTANCE.getView(behavior);
		if (!view.empty()) {
			((Session) identity).getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.SetupInterface,
					view);
		}

		/*
		 * spawn a new runtime actor and have it load the behavior
		 */
		ActorRef<KlabMessage> actor = getContext().spawn(RuntimeActor.create((Session) identity),
				identity.getId() + NameGenerator.shortUUID());

		actor.tell(message);

		return Behaviors.same();

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
