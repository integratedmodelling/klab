package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assignment;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.api.IKActorsStatement.Do;
import org.integratedmodelling.kactors.api.IKActorsStatement.FireValue;
import org.integratedmodelling.kactors.api.IKActorsStatement.For;
import org.integratedmodelling.kactors.api.IKActorsStatement.If;
import org.integratedmodelling.kactors.api.IKActorsStatement.Instantiation;
import org.integratedmodelling.kactors.api.IKActorsStatement.Sequence;
import org.integratedmodelling.kactors.api.IKActorsStatement.TextBlock;
import org.integratedmodelling.kactors.api.IKActorsStatement.While;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsActionCall;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.BindUserAction;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Cleanup;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.ComponentFire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.SetView;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Stop;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.UserAction;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior.UnknownMessage;
import org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior.Match;
import org.integratedmodelling.klab.components.runtime.actors.behavior.BehaviorAction;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.ObjectExpression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	protected IBehavior behavior;
	protected String appId;
	protected IActorIdentity<KlabMessage> identity;
	protected Map<Long, MatchActions> listeners = Collections.synchronizedMap(new HashMap<>());
	protected Map<String, MatchActions> componentFireListeners = Collections.synchronizedMap(new HashMap<>());
	private AtomicLong nextId = new AtomicLong(0);
	private Map<String, Long> actionBindings = Collections.synchronizedMap(new HashMap<>());
	private Map<String, ActorRef<KlabMessage>> receivers = Collections.synchronizedMap(new HashMap<>());
	private Map<String, List<ActorRef<KlabMessage>>> childInstances = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Consumer<Object>> stateChangeListeners = Collections.synchronizedMap(new HashMap<>());

	/*
	 * This is the parent that generated us through a 'new' instruction, if any.
	 */
	private ActorRef<KlabMessage> parentActor = null;

	/*
	 * if we pre-build actions or we run repeatedly we cache them here. Important
	 * that their run() method is reentrant.
	 */
	protected Map<String, KlabAction> actionCache = Collections.synchronizedMap(new HashMap<>());

	/*
	 * actions that were created from system actions rather than actual actors, here
	 * so we can talk to them from k.Actors
	 */
	private Map<String, KlabAction.Actor> localActions = Collections.synchronizedMap(new HashMap<>());

	/*
	 * if we have a view (either a layout for an app or a component for a component)
	 * we put it here at initialization.
	 */
	private ViewComponent view;

	protected ActorRef<KlabMessage> getDispatcher() {
		if (appId == null) {
			return getContext().getSelf();
		}
		return identity.getActor();
	}

	/**
	 * Descriptor for actions to be taken when a firing is recorded with the ID used
	 * as key in matchActions.
	 * 
	 * @author Ferd
	 *
	 */
	class MatchActions {

		ActorRef<KlabMessage> caller;
		List<Pair<Match, IKActorsStatement>> matches = new ArrayList<>();

		// this is the original calling scope, to use when the listening action is
		// executed upon a match.
		Scope scope;

		public void match(Object value) {

			for (Pair<Match, IKActorsStatement> match : matches) {
				if (match.getFirst().matches(value, scope)) {
					execute(match.getSecond(), scope.withMatch(match.getFirst(), value));
				}
			}
		}

		public MatchActions(Scope scope) {
			this.scope = scope;
		}
	}

	/**
	 * The main message for anything sent through k.Actors. Dispatched to receivers
	 * running applications, unless direct (application ID is null, i.e. it's for
	 * this actor).
	 * 
	 * @author Ferd
	 *
	 */
	public interface KlabMessage {

		/**
		 * Get another message identical to this but with no application ID, intended
		 * for execution and not for dispatching.
		 * 
		 * @return
		 */
		KlabMessage direct();
	}

	/**
	 * Runtime scope for all k.Actors statements. Root scopes are for each action.
	 * Local class so that the identity is accessible.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Scope extends Parameters<String> {

		boolean synchronous = false;
		Scope parent = null;
		IRuntimeScope runtimeScope;
		Long listenerId;
		IActorIdentity<KlabMessage> identity;
		Object match;
		String appId;
		public Map<String, Object> symbolTable = new HashMap<>();

		/**
		 * Set when the action being run is tagged to have a specific panel (footer,
		 * header, modal etc), which is reported to the view before the action is run
		 * and its ID set in the scope so that components created by view messages know
		 * where to go.
		 */
		String viewId;
		ActorRef<KlabMessage> sender;

		public Scope(IActorIdentity<KlabMessage> identity, String appId, IRuntimeScope scope) {
			this.runtimeScope = scope;
			this.identity = identity;
			this.appId = appId;
		}

		public Scope withMatch(Match match, Object value) {
			Scope ret = new Scope(this);
			/*
			 * if we have identifiers either as key or in list key, match them to the
			 * values. Otherwise match to $, $1, ... #n
			 */
			if (match.isIdentifier(ret)) {
				ret.symbolTable.put(match.getIdentifier(), value);
			} else if (match.isImplicit()) {
				ret.symbolTable.put("$", value);
				if (value instanceof Collection) {
					int n = 1;
					for (Object o : ((Collection<?>) value)) {
						ret.symbolTable.put("$" + (n++), o);
					}
				}
			}
			ret.match = value;
			return ret;
		}

		public Scope(Scope scope) {
			this.synchronous = scope.synchronous;
			this.runtimeScope = scope.runtimeScope;
			this.parent = scope;
			this.listenerId = scope.listenerId;
			this.sender = scope.sender;
			this.viewId = scope.viewId;
			this.symbolTable.putAll(scope.symbolTable);
			this.identity = scope.identity;
		}

		public String toString() {
			return "{S " + listenerId + "}";
		}

		public Scope synchronous() {
			Scope ret = new Scope(this);
			ret.synchronous = true;
			return ret;
		}

		public Scope withNotifyId(Long id) {
			Scope ret = new Scope(this);
			ret.listenerId = id;
			return ret;
		}

		public IIdentity getIdentity() {
			return identity;
		}

		public Long getNotifyId() {
			return listenerId;
		}

		public IMonitor getMonitor() {
			return this.runtimeScope == null ? null : this.runtimeScope.getMonitor();
		}

		public Scope withSender(ActorRef<KlabMessage> sender, String appId) {
			Scope ret = new Scope(this);
			ret.sender = sender;
			ret.appId = appId;
			return ret;
		}

		public Scope withViewId(String viewId) {
			Scope ret = new Scope(this);
			ret.viewId = viewId;
			return ret;
		}

		public Object getValue(String string) {
			if (symbolTable.containsKey(string)) {
				return symbolTable.get(string);
			}
			return identity.getState(string, Object.class);
		}

	}

	protected void waitForCompletion(KlabMessage message) {

	}

	protected IActorIdentity<KlabMessage> getIdentity() {
		return this.identity;
	}

	protected KlabActor(ActorContext<KlabMessage> context, IActorIdentity<KlabMessage> identity, String appId) {
		super(context);
		this.identity = identity;
		this.appId = appId;
	}

	/**
	 * Basic messages. Redefine extending the result of super() to add.
	 * 
	 * @return a builder for the behavior
	 */
	protected ReceiveBuilder<KlabMessage> configure() {
		ReceiveBuilder<KlabMessage> builder = newReceiveBuilder();
		return builder.onMessage(Load.class, this::loadBehavior).onMessage(Spawn.class, this::createChild)
				.onMessage(Fire.class, this::reactToFire).onMessage(ComponentFire.class, this::reactToComponentFiring)
				.onMessage(UserAction.class, this::reactToViewAction)
				.onMessage(BindUserAction.class, this::bindViewAction).onMessage(SetView.class, this::setView)
				.onMessage(KActorsMessage.class, this::executeCall).onMessage(Stop.class, this::stopChild)
				.onMessage(Cleanup.class, this::cleanup).onSignal(PostStop.class, signal -> onPostStop());
	}

	protected Behavior<KlabMessage> cleanup(Cleanup message) {
		/*
		 * this may intercept listeners
		 */
		for (KlabAction action : actionCache.values()) {
			action.dispose();
		}

		return this;
	}

	protected KlabActor onPostStop() {

		// TODO deactivate the underlying observation, send changes
		return this;
	}

	/**
	 * Stop a child after cleaning up. Do nothing if child does not exist (may be
	 * leftover IDs from a client connected to another engine).
	 * 
	 * @param message
	 * @return
	 */
	protected Behavior<KlabMessage> stopChild(Stop message) {

		if (message.appId != null && receivers.containsKey(message.appId)) {
			receivers.get(message.appId).tell(new Cleanup());
			getContext().stop(receivers.get(message.appId));
			receivers.remove(message.appId);
			return Behaviors.same();
		}

		return Behaviors.same();

	}

	protected Behavior<KlabMessage> reactToViewAction(UserAction message) {

		if (message.appId != null) {
			ActorRef<KlabMessage> receiver = receivers.get(message.appId);
			if (receiver != null) {
				receiver.tell(message.direct());
			}
		} else {
			Long notifyId = this.actionBindings.get(message.action.getComponent().getId());
			if (notifyId != null && message.action.getComponent().getActorPath() == null) {
				MatchActions actions = listeners.get(notifyId);
				if (actions != null) {
					actions.match(getActionValue(message.action));
				}
			} else if (message.action.getComponent().getActorPath() != null) {

				// dispatch to child actor
				String path = message.action.getComponent().getActorPath();
				String[] elements = path.split("\\.");
				if (elements.length > 0) {

					String actorId = elements[0];
					int idx = 0;
					int cut = actorId.indexOf('_');
					if (cut > 0) {
						idx = Integer.parseInt(actorId.substring(cut + 1));
						actorId = actorId.substring(0, cut);
					}
					List<ActorRef<KlabMessage>> actors = this.childInstances.get(actorId);

					if (actors != null && actors.size() > idx) {

						if (elements.length == 1) {
							message.action.getComponent().setActorPath(null);
						} else if (elements.length > 1) {
							message.action.getComponent().setActorPath(Path.getRemainder(path, "."));
						}
						actors.get(idx).tell(message);
					} else {
						System.out.println("HOSTIA unreferenced child actor " + elements[0]);
					}
				}
			}
		}
		return Behaviors.same();
	}

	private Object getActionValue(ViewAction action) {
		if (action.getStringValue() != null) {
			return action.getStringValue();
		} else if (action.getDateValue() != null) {
			return action.getDoubleValue();
		} else if (action.getIntValue() != null) {
			return action.getIntValue();
		} else if (action.getDoubleValue() != null) {
			return action.getDoubleValue();
		}
		return null;
	}

	protected Behavior<KlabMessage> bindViewAction(BindUserAction message) {
		if (message.appId != null) {
			ActorRef<KlabMessage> receiver = receivers.get(message.appId);
			if (receiver != null) {
				receiver.tell(message.direct());
			}
		} else {
			this.actionBindings.put(message.componentId, message.notifyId);
		}
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> setView(SetView message) {
		// TODO Set the action bindings for a component
		this.view = message.component;
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> reactToComponentFiring(ComponentFire message) {
		// TODO
		if (message.listenerId != null) {
			MatchActions actions = componentFireListeners.get(message.listenerId);
			if (actions != null) {
				actions.match(message.value);
			}
		}
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> reactToFire(Fire message) {

		if (message.appId != null) {
			ActorRef<KlabMessage> receiver = receivers.get(message.appId);
			if (receiver != null) {
				receiver.tell(message.direct());
			}
		} else {
			if (message.listenerId != null) {
				MatchActions actions = listeners.get(message.listenerId);
				if (actions != null) {
					actions.match(message.value);
					if (message.finalize) {
						listeners.remove(message.listenerId);
					}
				}
			}
		}
		return Behaviors.same();
	}

	@Override
	final public Receive<KlabMessage> createReceive() {
		return configure().build();
	}

	protected Behavior<KlabMessage> loadBehavior(Load message) {

		this.parentActor = message.parent;

		if (message.appId != null) {
			/*
			 * spawn another actor and load the behavior in it.
			 */
			ActorRef<KlabMessage> child = getContext().spawn(SessionActor.create((Session) identity, message.appId),
					message.appId);
			this.receivers.put(message.appId, child);
			child.tell(message.direct());

		} else {

			this.behavior = Actors.INSTANCE.getBehavior(message.behavior);
			this.listeners.clear();
			this.actionBindings.clear();
			this.actionCache.clear();

			if (behavior.getDestination() == Type.APP) {
				Layout view = Actors.INSTANCE.getView(behavior, identity, this.appId, null);
				if (!view.empty()) {
					this.view = view;
					this.identity.setLayout(view);
					this.identity.getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.SetupInterface,
							view);
				}
			}

			/*
			 * Init action called no matter what and before the behavior is set; the onLoad
			 * callback intervenes afterwards.
			 */
			for (IBehavior.Action action : this.behavior.getActions("init", "@init")) {
				run(action, new Scope(this.identity, appId, message.scope));
			}

			/*
			 * run any main actions
			 */
			for (IBehavior.Action action : this.behavior.getActions("main", "@main")) {
				run(action, new Scope(this.identity, appId, message.scope));
			}

		}

		return Behaviors.same();
	}

	protected void run(IBehavior.Action action, Scope scope) {
		/*
		 * TODO if this contains any of @panel, @modal, @header, @footer set the viewId
		 * in the scope to the ID of the component created upon loading the behavior.
		 * Look that up in viewIds using the name in the annotation or the annotation
		 * ID.
		 */
		if (((BehaviorAction) action).getViewId() != null) {
			scope = scope.withViewId(((BehaviorAction) action).getViewId());
		}
		execute(action.getStatement().getCode(), scope);
	}

	private void execute(IKActorsStatement code, Scope scope) {
		switch (code.getType()) {
		case ACTION_CALL:
			executeCall((IKActorsStatement.Call) code, scope);
			break;
		case ASSIGNMENT:
			executeAssignment((IKActorsStatement.Assignment) code, scope);
			break;
		case DO_STATEMENT:
			executeDo((IKActorsStatement.Do) code, scope);
			break;
		case FIRE_VALUE:
			executeFire((IKActorsStatement.FireValue) code, scope);
			break;
		case FOR_STATEMENT:
			executeFor((IKActorsStatement.For) code, scope);
			break;
		case IF_STATEMENT:
			executeIf((IKActorsStatement.If) code, scope);
			break;
		case CONCURRENT_GROUP:
			executeGroup((IKActorsStatement.ConcurrentGroup) code, scope);
			break;
		case SEQUENCE:
			executeSequence((IKActorsStatement.Sequence) code, scope);
			break;
		case TEXT_BLOCK:
			executeText((IKActorsStatement.TextBlock) code, scope);
			break;
		case WHILE_STATEMENT:
			executeWhile((IKActorsStatement.While) code, scope);
			break;
		case INSTANTIATION:
			executeInstantiation((IKActorsStatement.Instantiation) code, scope);
			break;
		default:
			break;
		}
	}

	private void executeInstantiation(Instantiation code, Scope scope) {

		Behavior<KlabMessage> child = null;
		if (this.identity instanceof Observation) {
			child = ObservationActor.create((Observation) this.identity, null);
		} else if (this.identity instanceof Session) {
			/**
			 * TODO if the actor has a view, use a behavior can address enable/disable/hide
			 * messages and the like.
			 */
			child = SessionActor.create((Session) this.identity, null);
		} else if (this.identity instanceof EngineUser) {
			child = UserActor.create((EngineUser) this.identity);
		}

		// existing actors for this behavior
		List<ActorRef<KlabMessage>> actors = this.childInstances.get(code.getActorBaseName());
		String actorName = code.getActorBaseName() + (actors == null ? "" : ("_" + (actors.size() + 1)));

		ActorRef<KlabMessage> actor = getContext().spawn(
				Behaviors.supervise(child).onFailure(SupervisorStrategy.resume().withLoggingEnabled(true)), actorName);

		/*
		 * TODO use the actor name to install a listener for any actions that may be
		 * connected to this instance; it will be used as listener ID for the
		 * ComponentFire message sent when the child fires.
		 */
		if (code.getActions().size() > 0) {

			MatchActions actions = new MatchActions(scope);
			for (Pair<IKActorsValue, IKActorsStatement> adesc : code.getActions()) {
				actions.matches.add(new Pair<Match, IKActorsStatement>(new Match(adesc.getFirst()), adesc.getSecond()));
			}
			this.componentFireListeners.put(actorName, actions);
		}

		// remove the appId for the children, otherwise their messages will be rerouted
		actor.tell(new Load(code.getBehavior(), null, scope.runtimeScope).withParent(getContext().getSelf()));

		/*
		 * if the new actor has a component associated, set its view to the component
		 * and prepare bindings.
		 */
		ViewComponent componentView = extractComponent(this.view, code.getActorBaseName());
		if (componentView != null) {
			actor.tell(new SetView(componentView));
		}

		if (actors == null) {
			actors = new ArrayList<>();
			this.childInstances.put(code.getActorBaseName(), actors);
		}

		actors.add(actor);

	}

	private ViewComponent extractComponent(ViewComponent component, String actorBaseName) {
		ViewComponent ret = null;
		if (component != null) {
			if (component instanceof Layout) {
				for (ViewPanel panel : Actors.INSTANCE.getPanels((Layout) component)) {
					if ((ret = extractComponent(panel, actorBaseName)) != null) {
						return ret;
					}
				}
			}
			for (ViewComponent c : component.getComponents()) {
				if (c.getActorPath() != null && c.getActorPath().startsWith(actorBaseName)) {
					ret = c;
					break;
				}
				ret = extractComponent(c, actorBaseName);
				if (ret != null) {
					return ret;
				}
			}
		}
		return ret;
	}

	private void executeWhile(While code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeText(TextBlock code, Scope scope) {
		executeCall(new KActorsActionCall(code), scope);
	}

	private void executeSequence(Sequence code, Scope scope) {
		if (code.getStatements().size() == 1) {
			execute(code.getStatements().get(0), scope);
		} else {
			for (IKActorsStatement statement : code.getStatements()) {
				execute(statement, scope.synchronous());
				// TODO waitForCompletion(message);
			}
		}
	}

	private void executeGroup(ConcurrentGroup code, Scope scope) {
		for (IKActorsStatement statement : code.getStatements()) {
			execute(statement, scope);
		}
	}

	private void executeIf(If code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeFor(For code, Scope scope) {
		// TODO Auto-generated method stub
	}

	private void executeFire(FireValue code, Scope scope) {

		if (scope.sender != null) {

			/*
			 * this should happen when a non-main action executes the fire. Must be checked
			 * first. Fire may happen if the action firing is called again, so don't remove
			 * the listener.
			 */
			scope.sender.tell(new Fire(scope.listenerId, code.getValue().getValue(), false, scope.appId));

		} else if (parentActor != null) {

			/*
			 * No sender = the fire is not coming from an internal action but goes out to
			 * the world, which in this case is the parent actor. Let our parent know we've
			 * fired with a message carrying the name it knows us by, so that the value can
			 * be matched to what is caught after the 'new' verb. Listener ID is the actor's
			 * name.
			 */
			parentActor.tell(new ComponentFire(getContext().getSelf().path().name(), code.getValue().getValue(),
					getContext().getSelf()));

		} else {

			/*
			 * Scureggia nello spazio: nothing is listening from the behaviors being
			 * executed.
			 * 
			 * TODO - an actor firing with no action listening and no parent should just
			 * send to either the user actor or (maybe) its parent identity?
			 */

		}
	}

	private void executeDo(Do code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeAssignment(Assignment code, Scope scope) {
		if (code.getRecipient() != null) {
			if ("self".equals(code.getRecipient())) {
				this.identity.setState(code.getVariable(), evaluateInScope((KActorsValue) code.getValue(), scope));
			} else {
				// TODO find the actor reference and send it an internal message to set the
				// state. Should be subject to scope and authorization
				throw new KlabUnimplementedException("klab actor state setting is unimplemented");
			}
		} else {
			scope.symbolTable.put(code.getVariable(), evaluateInScope((KActorsValue) code.getValue(), scope));
		}
	}

	protected Object evaluateInScope(KActorsValue arg, Scope scope) {
		switch (arg.getType()) {
		case ANYTHING:
		case ANYVALUE:
			break;
		case ANYTRUE:
			return true;
		case ERROR:
			throw arg.getValue() instanceof Throwable ? new KlabException((Throwable) arg.getValue())
					: new KlabException(arg.getValue() == null ? "Unspecified actor error from error value"
							: arg.getValue().toString());

		case NUMBERED_PATTERN:
		case IDENTIFIER:

			// TODO check for recipient in ID
			return scope.getValue(arg.getValue().toString());

		case EXPRESSION:

			if (arg.getData() == null) {
				arg.setData(new ObjectExpression((IKimExpression) arg.getValue(), scope.runtimeScope));
			}
			return ((ObjectExpression) arg.getData()).eval(scope.runtimeScope, identity,
					Parameters.create(scope.symbolTable));

		case BOOLEAN:
		case CLASS:
		case DATE:
		case NUMBER:
		case RANGE:
		case STRING:
		case OBSERVABLE:
		case QUANTITY:
			return arg.getValue();
		case OBSERVATION:
			// TODO
			break;
		case SET:
			// eval all args
			break;
		case LIST:
			// eval all args
			break;
		case TREE:
			// eval all args
			break;
		case MAP:
			break;
		case NODATA:
			return null;
//			return Observables.INSTANCE.declare(arg.getValue().toString());
		case REGEXP:
			break;
		case TABLE:
			break;
		case TYPE:
			break;
		case URN:
			return new Urn(arg.getValue().toString());
//		default:
//			break;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void executeCall(Call code, Scope scope) {

		/*
		 * TODO message reply ID for listeners must be same for every internal message
		 * if there is a group: in that case, set the ID in the scope.
		 */

		Long notifyId = scope.listenerId;

		if (code.getActions().size() > 0) {

			notifyId = nextId.incrementAndGet();
			MatchActions actions = new MatchActions(scope);
			for (Pair<IKActorsValue, IKActorsStatement> adesc : code.getActions()) {
				actions.matches.add(new Pair<Match, IKActorsStatement>(new Match(adesc.getFirst()), adesc.getSecond()));
			}
			this.listeners.put(notifyId, actions);
		}

		if (code.getGroup() == null) {

			String receiver = "self";
			String message = code.getMessage();
			int index = 0;
			if (message.contains(".")) {
				receiver = Path.getLeading(message, '.');
				message = Path.getLast(message, '.');
			}

			/*
			 * if the name has _nnn appended, remove it to get the index
			 */
			if (receiver.contains("_")) {
				int rn = receiver.lastIndexOf('_');
				String num = receiver.substring(rn + 1);
				if (NumberUtils.encodesInteger(num)) {
					index = Integer.parseInt(num);
					receiver = receiver.substring(0, rn - 1);
				}
			}

			/*
			 * "incarnated" local action receivers have priority
			 */
			if (this.localActions.containsKey(receiver)) {
				KActorsMessage m = new KActorsMessage(getDispatcher(), receiver, message,
						((KActorsActionCall) code).getInternalId(), code.getArguments(), scope.withNotifyId(notifyId),
						appId);
				this.localActions.get(receiver).onMessage(m, scope);
				return;
			}

			/*
			 * handle actor (possibly a family) in childInstances
			 */
			if (this.childInstances.containsKey(receiver)) {
				List<ActorRef<KlabMessage>> children = this.childInstances.get(receiver);
				if (children.size() < index) {
					children.get(index)
							.tell(new KActorsMessage(getDispatcher(), receiver, message,
									((KActorsActionCall) code).getInternalId(), code.getArguments(),
									scope.withNotifyId(notifyId), appId));
					return;
				}
			}

			ActorRef<KlabMessage> recipient = null;

			if (!"self".equals(receiver) && scope.symbolTable.get(receiver) instanceof IActorIdentity) {
				try {
					recipient = ((IActorIdentity<KlabMessage>) scope.symbolTable.get(receiver)).getActor();
				} catch (Throwable t) {
					// TODO do something with the failed call, the actor should probably remember
					if (this.identity instanceof IRuntimeIdentity) {
						((IRuntimeIdentity) this.identity).getMonitor()
								.error("error executing actor call " + message + ": " + t.getMessage());
					}
					return;
				}
			} else {

				// if we have no action for this message, send to user actor. Code should have
				// been fixed to
				// include the recipient when it is known.
				switch (receiver) {
				case "self":
					recipient = getDispatcher();
					break;
				case "view":
				case "session":
					// FIXME this will get the wrong actor - must install the runtime in the spawned
					// observation and
					// take it from there using the IActorIdentity API - getRuntimeActor() or
					// something, return self
					// in a runtime.
					recipient = identity.getParentIdentity(Session.class).getActor();
					break;
				case "user":
					recipient = identity.getParentIdentity(EngineUser.class).getActor();
				}
			}

			if (recipient == null || ("self".equals(receiver) && this.behavior.getActions(message) == null)) {
				Pair<String, ActorRef<KlabMessage>> resolved = Actors.INSTANCE.lookupRecipient(message, this.identity);
				if (resolved != null) {
					receiver = resolved.getFirst();
					recipient = resolved.getSecond();
				}
			}

			if (recipient == null) {
				// this not returning null guarantees that the message will be understood
				recipient = identity.getParentIdentity(EngineUser.class).getActor();
			}

			recipient.tell(
					new KActorsMessage(getDispatcher(), receiver, message, ((KActorsActionCall) code).getInternalId(),
							code.getArguments(), scope.withNotifyId(notifyId), appId));

		} else {

			execute(code.getGroup(), scope.withNotifyId(notifyId));

		}
	}

	protected Behavior<KlabMessage> executeCall(KActorsMessage message) {

		/**
		 * Route only those messages whose appID is recognized, meaning they are
		 * directed through us to one of our app executors. Others with appId will come
		 * from an application but our agent doesn't have its own behavior loaded, so
		 * continue assuming it's for us.
		 */
		if (message.appId != null && receivers.containsKey(message.appId)) {
			ActorRef<KlabMessage> receiver = receivers.get(message.appId);
			if (receiver != null) {
				receiver.tell(message.direct());
			}
		} else {

			/*
			 * it's for us: our appId should be in the scope.
			 */

			// message can be one of ours or a system one.
			boolean ran = false;
			Action action = this.behavior == null ? null : this.behavior.getAction(message.message);
			if (action != null) {
				ran = true;
				run(action, message.scope.withSender(message.sender, appId));
			} else {

				/*
				 * Use cache only for system actions for now. The cache may have been pre-filled
				 * (only by view actions for the time being).
				 */
				KlabAction a = null;
				if (message.actionInternalId != null) {
					a = actionCache.get(message.actionInternalId);
				}

				if (a == null) {
					a = Actors.INSTANCE.getSystemAction(message.message, this.getIdentity(), message.arguments,
							message.scope, getContext().getSelf(), message.actionInternalId);
					if (a != null && message.actionInternalId != null) {
						actionCache.put(message.actionInternalId, a);
					}
				}

				if (a != null) {

					if (a instanceof KlabAction.Actor) {
						/*
						 * assign ID and store for later use
						 */
						if (message.arguments.containsKey("tag")) {
							Object t = message.arguments.get("tag");
							if (t instanceof KActorsValue) {
								t = ((KActorsValue) t).getValue();
							}
							((KlabAction.Actor) a).setName(t.toString());
							this.localActions.put(((KlabAction.Actor) a).getName(), (KlabAction.Actor) a);
						}
					}

					ran = true;
					a.run(message.scope.withSender(message.sender, appId));
				}

			}

			if (!ran) {
				this.identity.getParentIdentity(EngineUser.class).getActor().tell(new UnknownMessage(message, null));
			}
		}

		return Behaviors.same();
	}

	/**
	 * Set the appropriate actor in the identity. Asking end may wait until that is
	 * done but we do not reply otherwise.
	 * 
	 * @param message
	 * @return
	 */
	protected Behavior<KlabMessage> createChild(Spawn message) {

		if (message.appId != null) {
			ActorRef<KlabMessage> receiver = receivers.get(message.appId);
			if (receiver != null) {
				receiver.tell(message.direct());
			}
		} else {

			Behavior<KlabMessage> behavior = null;
			// TODO potentially more differentiation according to host
			if (message.identity instanceof Observation) {
				behavior = ObservationActor.create((Observation) message.identity, message.appId);
			} else if (message.identity instanceof Session) {
				behavior = SessionActor.create((Session) message.identity, message.appId);
			}
			ActorRef<KlabMessage> actor = getContext().spawn(
					Behaviors.supervise(behavior).onFailure(SupervisorStrategy.resume().withLoggingEnabled(true)),
					message.identity.getId());
			message.identity.instrument(actor);
		}

		return Behaviors.same();
	}

}
