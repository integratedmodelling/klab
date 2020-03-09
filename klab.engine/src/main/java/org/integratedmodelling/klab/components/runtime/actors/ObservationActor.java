package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.components.runtime.observations.Observation;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

public class ObservationActor extends KlabActor {

	static Behavior<KlabMessage> create(Observation observation) {
		return Behaviors.setup(ctx -> new ObservationActor(ctx, observation));
	}

	public ObservationActor(ActorContext<KlabMessage> context, Observation observation) {
		super(context, observation);
	}

}
