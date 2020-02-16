package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.observations.IObservation;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class ObservationPeer extends AbstractActor {


	static public ActorRef create(IObservation observation) {
		// context = (get context from runtime context)
		// return context.actorOf(ObservationActor.props(observation), observation.getId());
		return null;
	}
	
	static Props props(IObservation observation) {
		return Props.create(ObservationPeer.class, () -> new ObservationPeer(observation));
	}

	private final IObservation observation;

	public ObservationPeer(IObservation observation) {
		this.observation = observation;
	}

	// add all message classes as serializable public static final
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null; // receiveBuilder().match(MessageClass.class, request -> { getSender().tell(new Response(....)); });
	}

}
