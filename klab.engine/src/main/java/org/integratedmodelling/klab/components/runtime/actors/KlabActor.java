package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.components.runtime.actors.KlabMessages.Load;
import org.integratedmodelling.klab.components.runtime.actors.KlabMessages.Spawn;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	private List<IBehavior> behaviors = new ArrayList<>();
	private IIdentity identity;

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

	protected KlabActor(ActorContext<KlabMessage> context, IIdentity identity) {
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
		// TODO
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> createChild(Spawn message) {
		Behavior<KlabMessage> behavior = null;
		message.ref = getContext().spawn(behavior, message.identity.getId());
		message.replyTo.tell(message);
		return Behaviors.same();
	}

}
