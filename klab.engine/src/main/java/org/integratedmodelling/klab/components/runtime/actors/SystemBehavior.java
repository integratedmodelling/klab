package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Parameters;

import akka.actor.typed.ActorRef;

/**
 * Messages not accessible to users but necessary to enable the base runtime
 * actor communication protocols. Includes spawning appropriate child actors,
 * loading behavior files, firing things and sending k.Actors message calls.
 * Their initialize() method won't be called.
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
		IRuntimeScope scope;

		public Load(String behavior, IRuntimeScope scope) {
			this.behavior = behavior;
			this.scope = scope;
		}
	}

	/**
	 * Report a temporal transition
	 * 
	 * @author Ferd
	 *
	 */
	public static class Transition extends AbstractKlabMessage {

		KlabActor.Scope scope;

		public Transition(KlabActor.Scope scope) {
			this.scope = scope;
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
	 * pattern matching. If finalize == true, the listener in the actor must be
	 * removed as the sending actor won't fire again.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Fire extends AbstractKlabMessage {

		String listenerId;
		Object value;
		boolean finalize;

		public Fire(String listenerId, Object firedValue, boolean isFinal) {
			this.listenerId = listenerId;
			this.value = firedValue;
			this.finalize = isFinal;
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
		String message;
		String receiver;
		IParameters<String> arguments = Parameters.create();
		KlabActor.Scope scope;

		public KActorsMessage(ActorRef<KlabMessage> sender, String receiver, String actionId,
				IParameters<String> arguments, KlabActor.Scope scope) {
			this.sender = sender;
			this.receiver = receiver;
			this.message = actionId;
			if (arguments != null) {
				this.arguments.putAll(arguments);
			}
			this.scope = scope;
		}

	}
}
