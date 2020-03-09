package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
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
	public static final class KlabMessage {
		String name;
		Object[] arguments;
	}
	
	protected KlabActor(ActorContext<KlabMessage> context, IIdentity identity, IBehavior... behaviors) {
		super(context);
		this.identity = identity;
		if (behaviors != null) {
			for (IBehavior behavior : behaviors) {
				this.behaviors.add(behavior);
			}
		}
	}

	@Override
	public Receive<KlabMessage> createReceive() {

		ReceiveBuilder<KlabMessage> builder = newReceiveBuilder();
		builder.onMessage(KlabMessage.class, this::onKlabMessage);
//		// TODO create all messages from the behavior specs
//		for (IMessageHandler message : behavior.getMessageHandlers()) {
//			// call .onMessage(ReadTemperature.class, this::onReadTemperature)
//			// .onSignal(PostStop.class, signal -> onPostStop()), whatever
//		}
		return builder.build();
	}

	private Behavior<KlabMessage> onKlabMessage(KlabMessage message) {
		return this;
	}
	
}
