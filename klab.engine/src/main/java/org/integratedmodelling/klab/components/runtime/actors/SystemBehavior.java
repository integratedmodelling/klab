package org.integratedmodelling.klab.components.runtime.actors;

import java.util.List;

import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;

/**
 * Messages not accessible to users but necessary to enable the base runtime
 * actor communication protocols. Includes spawning appropriate child actors,
 * loading behavior files, firing things and sending k.Actors message calls.
 * 
 * @author Ferd
 *
 */
public class SystemBehavior {

	/**
	 * Load a behavior
	 * 
	 * @author Ferd
	 *
	 */
	public static class Load extends AbstractKlabMessage {

		String behavior;

		public Load(String behavior) {
			this.behavior = behavior;
		}
	}

	/**
	 * Spawn an appropriate child actor.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Spawn extends AbstractKlabMessage {

		IActorIdentity<KlabMessage> identity;

		public Spawn(IActorIdentity<KlabMessage> identity) {
			this.identity = identity;
		}

	}

	/**
	 * The message sent back to a listening actor when an actor fires, triggering
	 * pattern matching.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Fire extends AbstractKlabMessage {

		String listenerId;
		Object value;

		public Fire(String listenerId, Object firedValue) {
			this.listenerId = listenerId;
			this.value = firedValue;
		}
	}

	/**
	 * The shuttle for a k.Actors message call. Always comes from a k.Actors
	 * behavior, sent by an actor to another.
	 * 
	 * @author Ferd
	 *
	 */
	public static class KActorsMessage extends AbstractKlabMessage {

		ActorRef<KlabMessage> sender;

		public KActorsMessage(ActorRef<KlabMessage> sender, String actionId, List<Object> parameters) {
			this.sender = sender;
		}

	}
}
