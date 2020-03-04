package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.components.runtime.actors.KlabMessages.Load;
import org.integratedmodelling.klab.components.runtime.actors.KlabMessages.Spawn;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	private List<IBehavior> behaviors = new ArrayList<>();
	private IActorIdentity<KlabMessage> identity;

	/**
	 * The main message for anything sent through k.Actors
	 * 
	 * @author Ferd
	 *
	 */
	public interface KlabMessage {

	}

	protected IIdentity getIdentity() {
		return this.identity;
	}

	protected KlabActor(ActorContext<KlabMessage> context, IActorIdentity<KlabMessage> identity) {
		super(context);
		this.identity = identity;
	}

	@Override
	public Receive<KlabMessage> createReceive() {
		ReceiveBuilder<KlabMessage> builder = newReceiveBuilder();
		return builder.onMessage(Load.class, this::loadBehavior).onMessage(Spawn.class, this::createChild).build();
	}

	protected Behavior<KlabMessage> loadBehavior(Load message) {
		this.behaviors.add(message.behavior);
		return this;
	}

	protected Behavior<KlabMessage> createChild(Spawn message) {
		Behavior<KlabMessage> behavior = null;
		// TODO potentially more differentiation according to host
		if (message.identity instanceof Observation) {
			behavior = ObservationActor.create((Observation)message.identity);
		} else if (message.identity instanceof Session) {
			behavior = SessionActor.create((Session)message.identity);
		}
		ActorRef<KlabMessage> actor = getContext().spawn(behavior, message.identity.getId());
		message.identity.setActor(actor);
		return this;
	}

}
