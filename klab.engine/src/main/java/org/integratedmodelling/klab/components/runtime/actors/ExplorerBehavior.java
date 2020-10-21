package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;

/**
 * Messages dedicated to talking to the k.LAB explorer view.
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@Behavior(id = "explorer", version = Version.CURRENT)
public class ExplorerBehavior {

	@Action(id = "show", fires = IKActorsValue.Type.OBSERVATION, description = "Show an artifact in the explorer,identified by name, semantics, or the artifact itself."
			+ " When selected, fire the artifact if all OK, an error, or empty if it was shown already.")
	public static class Show extends KlabActionExecutor {

		String listenerId = null;

		public Show(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (arguments.getUnnamedKeys().isEmpty()) {
			}
		}
	}

	@Action(id = "hide", fires = IKActorsValue.Type.OBSERVATION, description = "Hide an artifact in the explorer, identified by name, semantics, or the artifact itself."
			+ " When selected, fire the artifact if all OK, an error, or empty if it wasn't shown.")
	public static class Hide extends KlabActionExecutor {

		String listenerId = null;

		public Hide(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (arguments.getUnnamedKeys().isEmpty()) {
			}
		}
	}

}
