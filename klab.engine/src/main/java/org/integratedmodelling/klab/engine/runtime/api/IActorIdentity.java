package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;

import akka.actor.typed.ActorRef;

/**
 * Provides the actor interface to any identity in the k.LAB runtime by exposing
 * an Akka actor reference.
 * 
 * @author Ferd
 *
 */
public interface IActorIdentity<T> extends IIdentity {

	/**
	 * Get the actor peer for the identity. If the actor needs to be created, ask
	 * the relevant parent's actor to create it first.
	 * 
	 * @return the actor reference. Never null.
	 */
	ActorRef<T> getActor();

	/**
	 * Load a specified behavior.
	 * 
	 * @param behavior
	 */
	void load(IBehavior behavior);

	/**
	 * Set the actor in the identity.
	 * 
	 * @param actor
	 */
	void instrument(ActorRef<T> actor);

}
