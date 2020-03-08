package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;
import akka.actor.typed.scaladsl.Behaviors;

public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	private IBehavior behavior;
	private IActorIdentity<KlabMessage> identity;

	/**
	 * The main message for anything sent through k.Actors
	 * 
	 * @author Ferd
	 *
	 */
	public interface KlabMessage {

		// unique ID to ensure reply and notification
		String getId();

	}

	protected void waitForCompletion(KlabMessage message) {

	}

	protected IIdentity getIdentity() {
		return this.identity;
	}

	protected KlabActor(ActorContext<KlabMessage> context, IActorIdentity<KlabMessage> identity) {
		super(context);
		this.identity = identity;
	}

	/**
	 * Basic messages. Redefine extending the result of super() to add.
	 * 
	 * @return a builder for the behavior
	 */
	protected ReceiveBuilder<KlabMessage> configure() {
		ReceiveBuilder<KlabMessage> builder = newReceiveBuilder();
		return builder
				.onMessage(Load.class, this::loadBehavior)
				.onMessage(Spawn.class, this::createChild)
				.onMessage(KActorsMessage.class, this::executeCall)
				.onSignal(PostStop.class, signal -> onPostStop());
	}

	protected KlabActor onPostStop() {
		// TODO deactivate the underlying observation, send changes
		return this;
	}

	@Override
	final public Receive<KlabMessage> createReceive() {
		return configure().build();
	}

	protected Behavior<KlabMessage> loadBehavior(Load message) {
		this.behavior = Actors.INSTANCE.getBehavior(message.behavior);
		for (IBehavior.Action action : this.behavior.getActions("main", "@main")) {
			run(action);
		}
		return Behaviors.same();
	}

	protected void run(IBehavior.Action action) {
		execute(action.getStatement().getCode());
	}

	private void execute(IKActorsStatement code) {
		switch (code.getType()) {
		case ACTION_CALL:
			break;
		case ASSIGNMENT:
			break;
		case DO_STATEMENT:
			break;
		case FIRE_VALUE:
			break;
		case FOR_STATEMENT:
			break;
		case IF_STATEMENT:
			break;
		case STATEMENT_GROUP:
			break;
		case TEXT_BLOCK:
			break;
		case WHILE_STATEMENT:
			break;
		default:
			break;
		
		}
	}

	protected Behavior<KlabMessage> executeCall(KActorsMessage message) {
		return Behaviors.same();
	}

	/**
	 * Set the appropriate actor in the identity. Asking end may wait until that is
	 * done but we do not reply otherwise.
	 * 
	 * @param message
	 * @return
	 */
	protected Behavior<KlabMessage> createChild(Spawn message) {

		Behavior<KlabMessage> behavior = null;
		// TODO potentially more differentiation according to host
		if (message.identity instanceof Observation) {
			behavior = ObservationActor.create((Observation) message.identity);
		} else if (message.identity instanceof Session) {
			behavior = SessionActor.create((Session) message.identity);
		}
		ActorRef<KlabMessage> actor = getContext().spawn(behavior, message.identity.getId());
		message.identity.instrument(actor);
		return Behaviors.same();
	}

}
