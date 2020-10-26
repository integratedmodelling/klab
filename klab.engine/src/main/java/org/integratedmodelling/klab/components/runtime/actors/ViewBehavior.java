package org.integratedmodelling.klab.components.runtime.actors;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActionExecutor.Component;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewAction.Operation;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewComponent.Type;
import org.integratedmodelling.klab.utils.MarkdownUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtils;

import akka.actor.typed.ActorRef;

/**
 * View messages. Uses metadata for layout control
 * <p>
 * Metadata this far:
 * <p>
 * No argument:
 * <ul>
 * <li>:right, :left, :top, :bottom</li>
 * <li>:hfill, :vfill, :fill</li>
 * <li>:disabled {!disabled for completeness}</li>
 * <li>:hidden {!hidden}</li>
 * <li>:vbox :hbox [:grid = default] for component layout (in annotation or for
 * groups)</li>
 * </ul>
 * <p>
 * With argument:
 * <ul>
 * <li>:cspan, :rspan (columns and rows spanned in grid)</li>
 * <li>:fg, :bg (color name for now?)</li>
 * <li>:bstyle {?HTML solid dotted}</li>
 * <li>:bwidth <n> border width (always solid for now)</li>
 * <li>:fstyle {bold|italic|strike|normal}</li>
 * <li>:fsize <n></li>
 * <li>:symbol {font awesome char code}</li>
 * <li>:class (CSS class)</li>
 * <li>:wmin, :hmin (minimum height and width)</li>
 * <li>:cols, :equal for panel grids</li>
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
	public static abstract class KlabWidgetActionExecutor extends KlabActionExecutor implements Component {

		Boolean dynamic = null;
		String name;

		public KlabWidgetActionExecutor(IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
				Scope scope, ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		// never called, do nothing
		@Override
		void run(Scope scope) {
		}

		@Override
		public final void onMessage(KlabMessage message, Scope scope) {

			if (message instanceof KActorsMessage) {

				KActorsMessage mess = (KActorsMessage) message;
				ViewAction action = null;
				switch (mess.message) {
				case "disable":
					action = new ViewAction(Operation.Enable, false);
					break;
				case "enable":
					action = new ViewAction(Operation.Enable, true);
					break;
				default:
					action = getResponse(mess, scope);
				}
				action.setApplicationId(mess.appId);
				action.setData(getMetadata(mess.arguments, scope));
				action.setComponentTag(this.getName());
				session.getMonitor().send(IMessage.MessageClass.ViewActor, IMessage.Type.ViewAction, action);
			}
		}

		/**
		 * Create the view action response for the passed message to the widget. Should
		 * only fill in the functional fields as the rest will be done in the
		 * superclass.
		 * 
		 * @param message
		 * @param scope
		 * @return
		 */
		protected abstract ViewAction getResponse(KActorsMessage message, Scope scope);

		/**
		 * Called only by static calls. Dynamic ones will use the call scope.
		 * 
		 * @return
		 */
		public ViewComponent getViewComponent() {
			ViewComponent ret = createViewComponent(this.scope);
			ret.setApplicationId(this.identity.getId());
			ret.setId(this.callId);
			return ret;
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
	public static class Alert extends KlabActionExecutor {

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
			message.getAttributes().putAll(getMetadata(arguments, scope));
			session.getMonitor().send(IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

	@Action(id = "confirm")
	public static class Confirm extends KlabActionExecutor {

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
			message.getAttributes().putAll(getMetadata(arguments, scope));
			session.getMonitor().post((msg) -> {
				fire(msg.getPayload(ViewAction.class).isBooleanValue(), true);
			}, IMessage.MessageClass.ViewActor, IMessage.Type.CreateViewComponent, message);
		}
	}

	@Action(id = "button")
	public static class Button extends KlabWidgetActionExecutor {

		public Button(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.PushButton);
			message.setName(this.evaluateArgument(0, scope, "Button Text"));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return DEFAULT_FIRE;
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			return ret;
		}

	}

	@Action(id = "checkbutton")
	public static class CheckButton extends KlabWidgetActionExecutor {

		public CheckButton(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.CheckButton);
			message.setName(this.evaluateArgument(0, scope, "Button Text"));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return DEFAULT_FIRE;
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			if ("update".equals(message.message)) {
				ret.setOperation(Operation.Update);
				ret.setStringValue(getDefaultAsString(message.arguments, this, scope));
			}
			return ret;
		}

	}

	@Action(id = "radiobutton")
	public static class RadioButton extends KlabWidgetActionExecutor {

		public RadioButton(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.RadioButton);
			message.setName(this.evaluateArgument(0, scope, "Button Text"));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return DEFAULT_FIRE;
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			return ret;
		}

	}

	@Action(id = "label")
	public static class Label extends KlabWidgetActionExecutor {

		public Label(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.Label);
			message.setContent(this.evaluateArgument(0, scope, null));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return DEFAULT_FIRE;
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			if ("update".equals(message.message)) {
				ret.setOperation(Operation.Update);
				ret.setStringValue(getDefaultAsString(message.arguments, this, scope));
			}
			return ret;
		}

	}

	@Action(id = "textinput")
	public static class Text extends KlabWidgetActionExecutor {

		public Text(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.TextInput);
			message.setContent(this.evaluateArgument(0, scope, (String) null));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return action.getStringValue();
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			if ("update".equals(message.message)) {
				ret.setOperation(Operation.Update);
				ret.setStringValue(getDefaultAsString(message.arguments, this, scope));
			}
			return ret;
		}

	}

	@Action(id = "combo")
	public static class Combo extends KlabWidgetActionExecutor {

		private Map<String, IKActorsValue> values = new HashMap<>();

		public Combo(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.Combo);
			for (String argument : arguments.getUnnamedKeys()) {
				Object value = arguments.get(argument);
				if (value instanceof KActorsValue) {
					Map<String, String> map = ((KActorsValue) value).asMap();
					message.getChoices().add(new Pair<>(map.get("id"), map.get("label")));
					this.values.put(map.get("id"), (IKActorsValue) value);
				}
			}
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return action.getStringValue();
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			return ret;
		}

	}

	@Action(id = "separator")
	public static class Separator extends KlabWidgetActionExecutor {

		public Separator(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.Separator);
			if (arguments.getUnnamedKeys().size() > 0) {
				message.setTitle(evaluateArgument(0, scope).toString());
			}
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return action.getStringValue();
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			return ret;
		}

	}

	@Action(id = "tree")
	public static class Tree extends KlabWidgetActionExecutor {

		public Tree(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.Tree);
			message.setTree(getTree((KActorsValue) arguments.get(arguments.getUnnamedKeys().iterator().next())));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			if (!message.getAttributes().containsKey("name")) {
				// tree "name" is the root element if it's a string
				message.setName(message.getTree().getValues().get(message.getTree().getRootId()).get("id"));
			}
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			return action.getStringValue();
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			return ret;
		}

	}

	/**
	 * Bound to the %%% .... %%% template syntax; handled directly by actors. Can
	 * also be referenced directly.
	 * 
	 * @author Ferd
	 *
	 */
	@Action(id = "text")
	public static class RichText extends KlabWidgetActionExecutor {

		public RichText(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
			// TODO compile template and set dynamic status if text contains runtime
			// template calls
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.Text);
			message.setContent(processTemplate(arguments.get(arguments.getUnnamedKeys().iterator().next()), scope));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected Object getFiredResult(ViewAction action) {
			/**
			 * TODO eventually handle links in the text; for now the Eclipse widget cannot
			 * use them, and the explorer can implement them directly but should be able to
			 * also fire the link when it's matched.
			 */
			return null;
		}

		@Override
		protected ViewAction getResponse(KActorsMessage message, Scope scope) {
			ViewAction ret = new ViewAction();
			return ret;
		}
	}

	public static ViewComponent.Tree getTree(KActorsValue tree) {
		@SuppressWarnings("unchecked")
		Graph<KActorsValue, DefaultEdge> graph = (Graph<KActorsValue, DefaultEdge>) tree.getValue();
		ViewComponent.Tree ret = new ViewComponent.Tree();
		int rootId = -1;
		int id = 0;
		Map<KActorsValue, Integer> ids = new HashMap<>();
		for (KActorsValue value : graph.vertexSet()) {
			ids.put(value, id);
			if (rootId < 0 && graph.outgoingEdgesOf(value).isEmpty()) {
				rootId = id;
			}
			ret.getValues().add(value.asMap());
			id++;
		}
		for (DefaultEdge edge : graph.edgeSet()) {
			ret.getLinks().add(new Pair<>(ids.get(graph.getEdgeSource(edge)), ids.get(graph.getEdgeTarget(edge))));
		}
		ret.setRootId(rootId);
		return ret;
	}

	public static String getDefaultAsString(IParameters<String> arguments, KlabActionExecutor action, Scope scope) {
		String ret = "";
		if (arguments.getUnnamedKeys().size() > 0) {
			Object a = arguments.get(arguments.getUnnamedKeys().iterator().next());
			if (a != null) {
				if (a instanceof KActorsValue) {
					a = action.evaluateInContext((KActorsValue) a, scope);
				}
				if (a != null) {
					ret = a.toString();
				}
			}
		}
		return ret;
	}

	public static String processTemplate(Object value, Scope scope) {
		String template = value instanceof String ? (String) value : null;
		if (template == null && value instanceof KActorsValue) {
			template = ((KActorsValue) value).getValue().toString();
		}
		/*
		 * TODO engage the template system to merge with the runtime context
		 */
		/*
		 * FIXME The insertBeginning (which should be more generic) is needed because of
		 * a bug in the Nebula component. May be bad for the sensible HTML in the
		 * explorer?
		 */
		template = StringUtils.insertBeginning(StringUtils.stripLeadingWhitespace(template), "&nbsp;", (string) -> {
			String tr = string.trim();
			if (tr.startsWith("* ") || tr.startsWith(". ") || tr.matches("[0-9]+\\.\\s")) {
				return true;
			}
			return false;
		});
		return MarkdownUtils.INSTANCE.format(template);
	}

	public static Map<String, String> getMetadata(IParameters<String> arguments, Scope scope) {
		Map<String, String> ret = new HashMap<>();
		if (arguments != null) {
			for (String key : arguments.getNamedKeys()) {
				Object o = arguments.get(key);
				if (o instanceof KActorsValue) {
					o = ((KActorsValue) o).getValue();
				}
				if (o == null) {
					ret.put(key, "null");
				} else {
					ret.put(key, o.toString());
				}
			}
		}
		return ret;
	}

}
