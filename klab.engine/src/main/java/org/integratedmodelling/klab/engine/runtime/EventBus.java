package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.components.runtime.observations.Subject;

import akka.actor.ActorRef;

/**
 * TARIK this "event bus" is a single instance that resides in the runtime scope for an entire observation tree.
 * I suggest that we use it as the holder of the root actor for a context; we can also give it a dispose() method
 * that is called when the context expires. Not sure if this call is the one that should be used to create
 */
public class EventBus implements IEventBus {

	private Session session;
	private ActorRef root;

	/**
	 * Called when the root subject is created. Establishes the session and creates
	 * the root actor for the context.
	 * 
	 * @param subject
	 */
	public EventBus(Subject subject) {
		this.session = subject.getRuntimeScope().getMonitor().getIdentity().getParentIdentity(Session.class);
		// TARIK this should create the root actor for the context, which should turn the context subject into
		// an actor. All successive actors should be created based on this by DefaultRuntimeProvider.createObservation(),
		// which should call a method in here. The call below is what I thought the call should be, but clearly this
		// is not the correct API.
		//this.root = session.getRootActor().actorOf(subject.getId());
	}

}
