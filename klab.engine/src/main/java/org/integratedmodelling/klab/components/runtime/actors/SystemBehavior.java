package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;

/**
 * Messages not accessible to users but necessary to enable the basic runtime
 * actor behavior. Includes spawning appropriate child actors, loading behavior
 * files and sending k.Actors message calls.
 * 
 * @author Ferd
 *
 */
public class SystemBehavior {

	public static class Load extends AbstractKlabMessage {

		IBehavior behavior;

		public Load(IBehavior behavior) {
			this.behavior = behavior;
		}
	}

	public static class Spawn extends AbstractKlabMessage {

		IActorIdentity<KlabMessage> identity;

		public Spawn(IActorIdentity<KlabMessage> identity) {
			this.identity = identity;
		}

	}

	/**
	 * The shuttle for a k.Actors message call.
	 * 
	 * @author Ferd
	 *
	 */
	public static class KActorsMessage extends AbstractKlabMessage {

	}
}
