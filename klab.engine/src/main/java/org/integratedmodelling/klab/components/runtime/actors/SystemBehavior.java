package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Collection;

import org.integratedmodelling.kactors.model.KActorsValue;
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

		@Override
		public void initialize(Parameters<String> arguments) {
		}
	}

	/**
	 * Report a temporal transition
	 * 
	 * @author Ferd
	 *
	 */
	public static class Transition extends AbstractKlabMessage {

		IRuntimeScope scope;

		public Transition(IRuntimeScope scope) {
			this.scope = scope;
		}

		@Override
		public void initialize(Parameters<String> arguments) {
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

		@Override
		public void initialize(Parameters<String> arguments) {
		}

	}

	/**
	 * The message installs a listener in a context that will fire an object to the
	 * sender whenever it is resolved and matches a pattern.
	 * 
	 * @author Ferd
	 *
	 */
	public static class When extends AbstractKlabMessage {

		String listenerId;
		Collection<KActorsValue> value;
		ActorRef<KlabMessage> sender;

		public When(ActorRef<KlabMessage> replyTo, Collection<KActorsValue> matches, String listenerId,
				IRuntimeScope scope) {
			this.listenerId = listenerId;
			this.value = matches;
			this.sender = replyTo;
			// TODO install a listener
		}

		@Override
		public void initialize(Parameters<String> arguments) {
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

		@Override
		public void initialize(Parameters<String> arguments) {
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
		IParameters<String> arguments;

		public KActorsMessage(ActorRef<KlabMessage> sender, String actionId, IParameters<String> parameters) {
			this.sender = sender;
			this.message = actionId;
			this.arguments = parameters;
		}

		@Override
		public void initialize(Parameters<String> arguments) {
		}

	}
}
