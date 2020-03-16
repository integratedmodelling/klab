package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;

@Behavior(id = "user", version = Version.CURRENT)
public class UserBehavior {

	/**
	 * This is the message sent to the user actor when a message in a behavior
	 * doesn't match any other messages.
	 * 
	 * @author Ferd
	 *
	 */
	public static class UnknownMessage implements KlabMessage {
		KActorsMessage call;
		public UnknownMessage(KActorsMessage message) {
			this.call = message;
		}
	}

}
