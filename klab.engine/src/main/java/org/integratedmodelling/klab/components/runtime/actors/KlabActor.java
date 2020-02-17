package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IMessageHandler;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class KlabActor extends AbstractBehavior<KlabCommand> {

	private IBehavior behavior;

	protected KlabActor(ActorContext<KlabCommand> context, IBehavior behavior) {
		super(context);
		this.behavior = behavior;
	}

	@Override
	public Receive<KlabCommand> createReceive() {

		ReceiveBuilder<KlabCommand> builder = newReceiveBuilder();
		// TODO create all messages from the behavior specs
		for (IMessageHandler message : behavior.getMessageHandlers()) {
			// call .onMessage(ReadTemperature.class, this::onReadTemperature)
			// .onSignal(PostStop.class, signal -> onPostStop()), whatever
		}
		return builder.build();
	}

}
