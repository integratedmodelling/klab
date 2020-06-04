package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.BindUserAction;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewComponent.Type;
import org.integratedmodelling.klab.utils.NameGenerator;

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
	 * <p>
	 * The action's run() is called when the code is executed, a point where the
	 * view has already received the correspondent ViewComponent or a container if
	 * dynamic.
	 * <ul>
	 * <li>The view is set up by reflection on the behavior before the behavior is
	 * loaded. All components that do not depend on runtime conditions (variable
	 * values, in optional or repeated code) are defined there; those that do are
	 * given a container. Each active component has the ID of the action that will
	 * represent it in the actor.</li>
	 * <li>The KlabWidgetAction::run method is called when the action is run in the
	 * code. When run() is called, the following should happen:</li>
	 * <li>1. The widget action checks if it is dynamic (compute first, then cache)
	 * and if so, sends the ViewComponent code with the ID of the container (the
	 * internalId of the call that has generated the action).</li>
	 * <li>2. Bind the notifyId in the execution scope to each component ID (if
	 * dynamic) or the action internal ID (if static) so that session messages to
	 * this actor can send the message to the actor to lookup the listeners and
	 * invoke them.</li>
	 * </ul>
	 * 
	 * @author Ferd
	 *
	 */
	public static abstract class KlabWidgetAction extends KlabAction {

		Boolean dynamic = null;

		public KlabWidgetAction(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(Scope scope) {

			Session session = this.identity.getParentIdentity(Session.class);
			String bindId = this.callId;

			// if deemed dynamic at view setup, we create and communicate the component here
			if (this.identity.getView() != null && !this.identity.getView().getStaticComponents().containsKey(bindId)) {

				bindId = "kad" + NameGenerator.shortUUID();

				/*
				 * dynamic: create component in scope and send it
				 */
				ViewComponent component = createViewComponent(scope);
				component.setId(bindId);
				component.setParentId(this.callId);
				component.setIdentity(this.identity.getId());
				session.getMonitor().send(IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent,
						component);
			}

			/*
			 * bind the listener ID that matches the fire actions to the bindId in the actor, so that the
			 * view message can simulate a fire
			 */
			identity.getActor().tell(new BindUserAction(scope.listenerId, bindId));
		}

		/**
		 * Called only by static calls. Dynamic ones will use the call scope.
		 * 
		 * @return
		 */
		public ViewComponent getViewComponent() {
			return createViewComponent(this.scope);
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
		protected abstract ViewComponent createViewComponent(Scope scope);

	}

	@Action(id = "alert")
	public static class Alert extends KlabAction {

		public Alert(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			Session session = this.identity.getParentIdentity(Session.class);
			ViewComponent message = new ViewComponent();
			message.setType(Type.Alert);
			message.setContent(this.evaluateArgument(0, scope, "Alert"));
			session.getMonitor().send(IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

	@Action(id = "confirm")
	public static class Confirm extends KlabAction {

		public Confirm(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			Session session = this.identity.getParentIdentity(Session.class);
			ViewComponent message = new ViewComponent();
			message.setType(Type.Confirm);
			message.setContent(this.evaluateArgument(0, scope, "Confirm"));
			session.getMonitor().post((msg) -> {
				fire(msg.getPayload(ViewAction.class).isBooleanValue(), true);
			}, IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

	@Action(id = "button")
	public static class Button extends KlabWidgetAction {

		public Button(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.PushButton);
			message.setName(this.evaluateArgument(0, scope, "Button Text"));
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
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.TextInput);
			message.setContent(this.evaluateArgument(0, scope, (String) null));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return action.getStringValue();
		}
	}

}
