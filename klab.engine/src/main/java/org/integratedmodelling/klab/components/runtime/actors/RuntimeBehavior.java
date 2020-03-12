package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Collection;

import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

import akka.actor.typed.ActorRef;

@Behavior(id = "session", version = Version.CURRENT)
public class RuntimeBehavior {

	@Action(id = "observe")
	public static class Observe extends KlabAction {

		public Observe(ActorRef<KlabMessage> sender, IParameters<String> arguments, String messageId) {
			super(sender, arguments, messageId);
		}

	}

	@Action(id = "maybe")
	public static class Maybe extends KlabAction {

		double probability = 0.5;

		public Maybe(ActorRef<KlabMessage> sender, IParameters<String> arguments, String messageId) {
			super(sender, arguments, messageId);
			if (arguments.getUnnamedKeys().size() > 0) {
				probability = 1 - arguments.get(arguments.getUnnamedKeys().get(0), Double.class);
			}
		}

	}

	/**
	 * The message installs a listener in a context that will fire an object to the
	 * sender whenever it is resolved and matches a pattern.
	 * FIXME this is an action, not a message
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
	}

}
