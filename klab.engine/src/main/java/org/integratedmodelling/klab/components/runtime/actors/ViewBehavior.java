package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.groovy.util.Maps;
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
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewComponent.Type;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MarkdownUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Triple;

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

		protected String name;
		protected ViewComponent component;
		// immutable copy of the original component installed by the actor after
		// instrumenting it.
		protected ViewComponent initializedComponent;

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

		/**
		 * Called when a k.Actors action is called with a component as receiver.
		 */
		@Override
		public final void onMessage(KlabMessage message, Scope scope) {

			if (message instanceof KActorsMessage) {

				KActorsMessage mess = (KActorsMessage) message;
				ViewAction action = null;
				switch (mess.message) {
				case "disable":
					action = new ViewAction(this.component = enable(false));
					break;
				case "enable":
					action = new ViewAction(this.component = enable(true));
					break;
				case "show":
					action = new ViewAction(this.component = show(true));
					break;
				case "hide":
					action = new ViewAction(this.component = show(false));
					break;
				case "reset":
					action = new ViewAction(this.component = copyComponent(this.initializedComponent));
					break;
				default:
					action = new ViewAction(this.component = setComponent(mess, scope));
				}
				action.setApplicationId(mess.appId);
				action.setData(getMetadata(mess.arguments, scope));
				action.setComponentTag(this.getName());
				session.getState().updateView(this.component);
				session.getMonitor().send(IMessage.MessageClass.ViewActor, IMessage.Type.ViewAction, action);
			}
		}

		protected ViewComponent enable(boolean b) {
			if (b) {
				this.component.getAttributes().remove("disabled");
			} else {
				this.component.getAttributes().put("disabled", "true");
			}
			return this.component;
		}

		protected ViewComponent show(boolean b) {
			if (b) {
				this.component.getAttributes().remove("hidden");
			} else {
				this.component.getAttributes().put("hidden", "true");
			}
			return this.component;
		}

		/**
		 * Modify the component according to the message and return it. May modify the
		 * current component directly or return a new one.
		 * 
		 * @param message
		 * @param scope
		 * @return
		 */
		protected abstract ViewComponent setComponent(KActorsMessage message, Scope scope);

		/**
		 * Call the virtual to create the component and save it for bookkeeping.
		 * 
		 * @return
		 */
		public ViewComponent getViewComponent() {
			this.component = createViewComponent(this.scope);
			return this.component;
		}

		/**
		 * Create a view component definition reflecting the widget. The component will
		 * be indexed and inserted in the layout when called before run() is called.
		 * 
		 * @return
		 */
		protected abstract ViewComponent createViewComponent(Scope scope);

		public Object getFiredValue(ViewAction action) {
			Object ret = onViewAction(action, scope);
			session.getState().updateView(this.component);
			return ret;
		}

		/**
		 * Receive the view action performed through the UI and return the valued that
		 * the k.Actors component should fire. Update the component as needed to keep
		 * the view descriptor synchronized in the session.
		 * 
		 * @param action
		 * @return
		 */
		protected abstract Object onViewAction(ViewAction action, Scope scope);

		public void setInitializedComponent(ViewComponent viewComponent) {
			this.initializedComponent = copyComponent(viewComponent);
		}

		private ViewComponent copyComponent(ViewComponent viewComponent) {
			try {
				return JsonUtils.cloneObject(viewComponent);
			} catch (Throwable e) {
				this.identity.getMonitor().error("internal error cloning view component");
			}
			return viewComponent;
		}
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
			message.setName(this.evaluateArgument(0, scope, ""));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			return this.component;
		}

		@Override
		public Object onViewAction(ViewAction action, Scope scope) {
			return true;
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
			message.setName(this.evaluateArgument(0, scope, ""));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			if ("update".equals(message.message)) {
				this.component.setName(getDefaultAsString(message.arguments, this, scope));
			} else if ("waiting".equals(message.message)) {
				this.component.getAttributes().remove("error");
				this.component.getAttributes().remove("done");
				this.component.getAttributes().remove("computing");
				this.component.getAttributes().put("waiting", "true");
			} else if ("error".equals(message.message)) {
				this.component.getAttributes().remove("waiting");
				this.component.getAttributes().remove("done");
				this.component.getAttributes().remove("computing");
				this.component.getAttributes().put("error", "true");
			} else if ("done".equals(message.message)) {
				this.component.getAttributes().remove("waiting");
				this.component.getAttributes().remove("error");
				this.component.getAttributes().remove("computing");
				this.component.getAttributes().put("done", "true");
			} else if ("computing".equals(message.message)) {
				this.component.getAttributes().remove("waiting");
				this.component.getAttributes().remove("error");
				this.component.getAttributes().remove("done");
				this.component.getAttributes().put("computing", "true");
			} else if ("reset".equals(message.message)) {
				this.component.getAttributes().remove("waiting");
				this.component.getAttributes().remove("error");
				this.component.getAttributes().remove("done");
				this.component.getAttributes().remove("computing");
			}
			return this.component;
		}

		@Override
		public Object onViewAction(ViewAction action, Scope scope) {
			if (action.isBooleanValue()) {
				this.component.getAttributes().put("checked", "true");
			} else {
				this.component.getAttributes().remove("checked");
			}
			return action.isBooleanValue();
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
			message.setName(this.evaluateArgument(0, scope, ""));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			if ("update".equals(message.message)) {
				this.component.setName(getDefaultAsString(message.arguments, this, scope));
			}
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			if (action.isBooleanValue()) {
				this.component.getAttributes().put("checked", "true");
			} else {
				this.component.getAttributes().remove("checked");
			}
			return action.isBooleanValue();
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
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			if ("update".equals(message.message)) {
				this.component.setContent(getDefaultAsString(message.arguments, this, scope));
			}
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			/*
			 * this is on toggle. Should fire something else on hover.
			 */
			if (action.isBooleanValue()) {
				this.component.getAttributes().put("checked", "true");
			} else {
				this.component.getAttributes().remove("checked");
			}
			return action.isBooleanValue();
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
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			if ("update".equals(message.message)) {
				this.component.setContent(getDefaultAsString(message.arguments, this, scope));
			}
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			this.component.setContent(action.getStringValue());
			return action.getStringValue();
		}

	}

	/**
	 * Recover an id, a label and a value from a value passed as an item for a tree,
	 * combo or list component.
	 * 
	 * @param value
	 * @return
	 */
	public static Triple<String, String, IKActorsValue> getItem(IKActorsValue value) {

		String id = value.getTag();
		String label = null;
		IKActorsValue val = value;
		if (((KActorsValue) value).getType() == IKActorsValue.Type.LIST) {
			List<?> list = (List<?>) ((KActorsValue) value).getValue();
			if (list.size() == 2) {
				if (id == null) {
					id = ((KActorsValue) list.get(0)).getValue().toString();
				} else {
					label = ((KActorsValue) list.get(0)).getValue().toString();
				}
				val = ((KActorsValue) list.get(1));
			}
		} else {
			Map<String, String> map = ((KActorsValue) value).asMap();
			id = map.get("id");
			label = map.get("label");
		}

		return new Triple<>(id, label, val);
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
				Triple<String, String, IKActorsValue> value = getItem(arguments.get(argument, IKActorsValue.class));
				message.getChoices().add(new Pair<>(value.getFirst(), value.getSecond()));
				this.values.put(value.getFirst(), value.getThird());

			}
			message.getAttributes().putAll(getMetadata(arguments, scope));
			return message;
		}

		@Override
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			if ("update".equals(message.message)) {
				this.component.getAttributes().put("selected", getDefaultAsString(message.arguments, this, scope));
			}
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			// TODO set selection
			return action.getStringValue();
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
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			// TODO info on hover
			return true;
		}

	}

	@Action(id = "tree")
	public static class Tree extends KlabWidgetActionExecutor {

		private Map<String, IKActorsValue> values = new HashMap<>();

		public Tree(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		public ViewComponent createViewComponent(Scope scope) {
			ViewComponent message = new ViewComponent();
			message.setType(Type.Tree);
			message.setTree(
					getTree((KActorsValue) arguments.get(arguments.getUnnamedKeys().iterator().next()), values));
			message.getAttributes().putAll(getMetadata(arguments, scope));
			if (!message.getAttributes().containsKey("name")) {
				// tree "name" is the root element if it's a string
				message.setName(message.getTree().getValues().get(message.getTree().getRootId()).get("id"));
			}
			return message;
		}

		@Override
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			List<Object> ret = new ArrayList<>();
			if (action.getListValue() != null) {
				for (String choice : action.getListValue()) {
					String[] split = choice.split("\\-");
					// TODO review the split[1] with Enrico - should be split[0] or maybe not.
					ret.add(KlabActor.evaluate(values.get(split[1]), scope));
				}
			}
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
		protected ViewComponent setComponent(KActorsMessage message, Scope scope) {
			if ("update".equals(message.message)) {
				this.component.setContent(processTemplate(getDefaultAsString(message.arguments, this, scope), scope));
			}
			return this.component;
		}

		@Override
		protected Object onViewAction(ViewAction action, Scope scope) {
			/**
			 * TODO eventually handle links in the text; for now the Eclipse widget cannot
			 * use them, and the explorer can implement them directly but should be able to
			 * also fire the link when it's matched.
			 */
			return null;
		}
	}

	public static ViewComponent.Tree getTree(KActorsValue tree, Map<String, IKActorsValue> values) {
		@SuppressWarnings("unchecked")
		Graph<KActorsValue, DefaultEdge> graph = (Graph<KActorsValue, DefaultEdge>) tree.getValue();
		ViewComponent.Tree ret = new ViewComponent.Tree();
		String rootId = "";
		Map<KActorsValue, String> ids = new HashMap<>();
		for (KActorsValue value : graph.vertexSet()) {
			Triple<String, String, IKActorsValue> item = getItem(value);
			ids.put(value, item.getFirst());
			if (rootId.isEmpty() && graph.outgoingEdgesOf(value).isEmpty()) {
				rootId = item.getFirst();
			}
			values.put(item.getFirst(), item.getThird());
			ret.getValues().put(item.getFirst(), Maps.of("id", item.getFirst(), "label", item.getSecond()));
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
