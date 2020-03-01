package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.observations.IObservation;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

public class ObservationActor extends KlabActor {

	static Behavior<KlabMessage> create(IObservation observation) {
		return Behaviors.setup(ctx -> new ObservationActor(ctx, observation));
	}

	public ObservationActor(ActorContext<KlabMessage> context, IObservation observation) {
		super(context,observation);
	}

}
