package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.observations.IObservation;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Create any observation actor as
 * 
 * @author ferdinando.villa
 *
 */
public class ObservationActor extends AbstractActor {


	static public ActorRef create(IObservation observation) {
		// context = (get context from runtime context)
		// return context.actorOf(ObservationActor.props(observation), observation.getId());
		return null;
	}
	
	static Props props(IObservation observation) {
		return Props.create(ObservationActor.class, () -> new ObservationActor(observation));
	}

	private final IObservation observation;

	public ObservationActor(IObservation observation) {
		this.observation = observation;
	}

	// add all message classes as serializable public static final
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null; // receiveBuilder().match(MessageClass.class, request -> { getSender().tell(new Response(....)); });
	}

}
