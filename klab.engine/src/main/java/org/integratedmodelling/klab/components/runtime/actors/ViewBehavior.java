package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
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

	/**
	 * These should be the superclass for any action that creates a persistent UI
	 * component (widget). The action visitor before running will use it to
	 * predefine the UI. An action will be created before run() with the sole
	 * purpose of creating the widgets.
	 * 
	 * @author Ferd
	 *
	 */
	public static abstract class KlabWidgetAction extends KlabAction {

		private int progressiveId;
		
		public KlabWidgetAction(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
				ActorRef<KlabMessage> sender) {
			super(identity, arguments, scope, sender);
		}

		@Override
		void run(Scope scope) {
			Session session = this.identity.getParentIdentity(Session.class);
			session.getMonitor().post((msg) -> fire(getFiredResult(msg.getPayload(ViewAction.class)), false),
					IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, getViewComponent());
		}

		/**
		 * Return the result to be fired, based on the action message sent by the view.
		 * 
		 * @param action
		 * @return
		 */
		protected abstract Object getFiredResult(ViewAction action);

		/**
		 * Create a view component definition reflecting the widget. The component will
		 * be indexed and inserted in the layout when called before run() is called.
		 * 
		 * @return
		 */
		public abstract ViewComponent getViewComponent();

		public int getProgressiveId() {
			return progressiveId;
		}

		public void setProgressiveId(int progressiveId) {
			this.progressiveId = progressiveId;
		}

	}

	@Action(id = "alert")
	public static class Alert extends KlabAction {

		public Alert(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender) {
			super(identity, arguments, scope, sender);
		}

		@Override
		void run(KlabActor.Scope scope) {
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
		void run(KlabActor.Scope scope) {
			Session session = this.identity.getParentIdentity(Session.class);
			ViewComponent message = new ViewComponent();
			message.setType(Type.Confirm);
			message.setContent(this.evaluateArgument(0, "Confirm"));
			session.getMonitor().post((msg) -> {
				fire(msg.getPayload(ViewAction.class).isBooleanValue(), true);
			}, IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

	@Action(id = "button")
	public static class Button extends KlabWidgetAction {

		public Button(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender) {
			super(identity, arguments, scope, sender);
		}

		@Override
		public ViewComponent getViewComponent() {
			ViewComponent message = new ViewComponent();
			message.setType(Type.PushButton);
			message.setName(this.evaluateArgument(0, "Button Text"));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return DEFAULT_FIRE;
		}
	}

	@Action(id = "textinput")
	public static class Text extends KlabWidgetAction {

		public Text(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender) {
			super(identity, arguments, scope, sender);
		}

		@Override
		public ViewComponent getViewComponent() {
			ViewComponent message = new ViewComponent();
			message.setType(Type.TextInput);
			message.setContent(this.evaluateArgument(0, (String) null));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return action.getStringValue();
		}
	}

}
