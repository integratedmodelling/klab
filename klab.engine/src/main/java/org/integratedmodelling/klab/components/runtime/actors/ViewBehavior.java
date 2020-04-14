package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewComponent.Type;

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

		public Alert(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender) {
			super(identity, arguments, scope, sender);
		}

		@Override
		void run() {
			Session session = this.identity.getParentIdentity(Session.class);
			ViewComponent message = new ViewComponent();
			message.setType(Type.Alert);
			message.setContent(this.evaluateArgument(0, "Alert"));
			session.getMonitor().send(IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

	@Action(id = "confirm")
	public static class Confirm extends KlabAction {

		public Confirm(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender) {
			super(identity, arguments, scope, sender);
		}

		@Override
		void run() {
			Session session = this.identity.getParentIdentity(Session.class);
			ViewComponent message = new ViewComponent();
			message.setType(Type.Confirm);
			message.setContent(this.evaluateArgument(0, "Confirm"));
			session.getMonitor().post((msg) -> {
				System.out.println("PORCODDIO HA RISPOSTO! SBORRO!");
				fire(msg.getPayload(ViewAction.class).isBooleanValue(), true);
			}, IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

}
