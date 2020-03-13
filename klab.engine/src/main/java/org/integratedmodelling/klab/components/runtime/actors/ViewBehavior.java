package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;

import akka.actor.typed.ActorRef;

/**
 * View messages.
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@Behavior(id = "view", version = Version.CURRENT)
public class ViewBehavior {

	@Action(id = "alert")
	public static class Alert extends KlabAction {

		public Alert(ActorRef<KlabMessage> sender, IParameters<String> arguments, KlabActor.Scope scope) {
			super(sender, arguments, scope);
		}

		@Override
		void run() {
			// TODO Auto-generated method stub

		}
	}
}
