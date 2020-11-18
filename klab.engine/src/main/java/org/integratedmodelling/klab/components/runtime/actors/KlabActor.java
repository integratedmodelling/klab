package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage.Semaphore;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.AppReset;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.BindUserAction;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Cleanup;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.ComponentFire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Stop;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.UserAction;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior.UnknownMessage;
import org.integratedmodelling.klab.components.runtime.actors.ViewBehavior.KlabWidgetActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior.Match;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.ObjectExpression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
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

/**
 * The base actor implementation for k.Actors. Linked to an identity and capable
 * of interpreting k.Actors code, whose interpreter is implemented here.
 * 
 * @author Ferd
 *
 */
public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	protected IBehavior behavior;
	/*
	 * this is set when a behavior is loaded and used to create proper actor paths
	 * for application components, so that user messages can be sent to the main
	 * application actor and directed to the actor that implements them.
	 */
	private String childActorPath = null;
	protected String appId;
	protected IActorIdentity<KlabMessage> identity;
	protected Map<Long, MatchActions> listeners = Collections.synchronizedMap(new HashMap<>());
	protected Map<String, MatchActions> componentFireListeners = Collections.synchronizedMap(new HashMap<>());
	private AtomicLong nextId = new AtomicLong(0);
	private Map<String, Long> actionBindings = Collections.synchronizedMap(new HashMap<>());
	private Map<String, ActorRef<KlabMessage>> receivers = Collections.synchronizedMap(new HashMap<>());
	private Map<String, List<ActorRef<KlabMessage>>> childInstances = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Object> symbolTable = Collections.synchronizedMap(new HashMap<>());
	private List<ActorRef<KlabMessage>> componentActors = Collections.synchronizedList(new ArrayList<>());
	
	/*
	 * This is the parent that generated us through a 'new' instruction, if any.
	 */
	private ActorRef<KlabMessage> parentActor = null;

	/*
	 * if we pre-build actions or we run repeatedly we cache them here. Important
	 * that their run() method is reentrant.
	 */
	protected Map<String, KlabActionExecutor> actionCache = Collections.synchronizedMap(new HashMap<>());

	/*
	 * actions that were created from system actions rather than actual actors, here
	 * so we can talk to them from k.Actors
	 */
	private Map<String, KlabActionExecutor.Actor> localActionExecutors = Collections.synchronizedMap(new HashMap<>());

	protected ActorRef<KlabMessage> getDispatcher() {
		if (this.appId == null) {
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
					break;
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
		 * Message may contain one or more of these, which trigger messages at the
		 * recipient's side which the sender can intercept to synchronize operations
		 * when requested or needed. Because concurrent message exchange is problematic
		 * during message execution, we use the Actors instance to organize these.
		 * 
		 * @author Ferd
		 *
		 */
		public interface Semaphore {

			public enum Type {
				/**
				 * if a LOAD semaphore is in a Load message, wait for the semaphore to go green
				 * before moving on.
				 */
				LOAD,

				/**
				 * if a FIRE semaphore is in a KActorsCall message, wait for the action to fire
				 * before moving on to the next instruction.
				 */
				FIRE
			}

			Type getType();
		}

		/**
		 * Get another message identical to this but with no application ID, intended
		 * for execution and not for dispatching.
		 * 
		 * @return
		 */
		KlabMessage direct();

		/**
		 * Add a semaphore for the recipient to honor after message execution and return
		 * self for fluency.
		 * 
		 * @param semaphor
		 */
		KlabMessage withSemaphore(Semaphore semaphor);

		/**
		 * Get all semaphors of the specified type.
		 * 
		 * @param type
		 * @return
		 */
		List<Semaphore> getSemaphores(Semaphore.Type type);
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
		public Map<String, Object> globalSymbols;
		ViewScope viewScope;
		ActorRef<KlabMessage> sender;
		private boolean initializing;

		public Scope(IActorIdentity<KlabMessage> identity, String appId, IRuntimeScope scope) {
			this.runtimeScope = scope;
			this.identity = identity;
			this.appId = appId;
			this.viewScope = new ViewScope(this);
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
			this.globalSymbols = scope.globalSymbols;
			this.synchronous = scope.synchronous;
			this.runtimeScope = scope.runtimeScope;
			this.parent = scope;
			this.listenerId = scope.listenerId;
			this.sender = scope.sender;
			this.symbolTable.putAll(scope.symbolTable);
			this.identity = scope.identity;
			this.viewScope = scope.viewScope;
			this.appId = scope.appId;
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

		public Object getValue(String string) {
			if (symbolTable.containsKey(string)) {
				return symbolTable.get(string);
			} else if (globalSymbols != null && globalSymbols.containsKey(string)) {
				return globalSymbols.get(string);
			}
			return identity.getState().get(string, Object.class);
		}

		/**
		 * Get a child scope for this action, which will create a panel viewscope if the
		 * action has a view.
		 * 
		 * @param appId
		 * @param action
		 * @return
		 */
		public Scope getChild(String appId, Action action) {
			Scope ret = new Scope(this);
			ret.viewScope = this.viewScope.getChild(action);
			return ret;
		}

		public Scope getChild(ConcurrentGroup code) {
			Scope ret = new Scope(this);
			if (!initializing) {
				ret.viewScope = this.viewScope.getChild(code);
			}
			return ret;
		}

		public Map<String, Object> getSymbols(IActorIdentity<?> identity) {
			Map<String, Object> ret = new HashMap<>();
			ret.putAll(identity.getState());
			if (globalSymbols != null) {
				ret.putAll(globalSymbols);
			}
			ret.putAll(symbolTable);
			return ret;
		}

		public Scope forInit() {
			Scope ret = new Scope(this);
			ret.initializing = true;
			return ret;
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
		return builder.onMessage(Load.class, this::handleLoadBehaviorMessage)
				.onMessage(Spawn.class, this::handleCreateChildMessage).onMessage(Fire.class, this::handleFireMessage)
				.onMessage(ComponentFire.class, this::handleComponentFireMessage)
				.onMessage(UserAction.class, this::handleUserActionMessage)
				.onMessage(AppReset.class, this::handleAppReset)
				.onMessage(BindUserAction.class, this::handleBindActionMessage)
				.onMessage(KActorsMessage.class, this::handleCallMessage).onMessage(Stop.class, this::stopChild)
				.onMessage(Cleanup.class, this::handleCleanupMessage).onSignal(PostStop.class, signal -> onPostStop());
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

	protected Behavior<KlabMessage> handleAppReset(AppReset message) {

//		if (message.appId != null) {
//			ActorRef<KlabMessage> receiver = receivers.get(message.appId);
//			if (receiver != null) {
//				receiver.tell(message.direct());
//			}
//		} else {
		KActorsMessage mes = new KActorsMessage(getContext().getSelf(), "reset", null, null, message.scope,
				message.scope.appId);

		/**
		 * FIXME not sure how this happens, but apparently it's only during debugging.
		 */
		if (this.behavior != null) {

			/*
			 * 1. call init and then reset if we are a component or an app and we have the
			 * actions
			 */
			if (this.behavior.getDestination() == Type.APP || this.behavior.getDestination() == Type.COMPONENT) {
				for (IBehavior.Action action : this.behavior.getActions("init", "@init")) {
					run(action, message.scope.getChild(this.appId, action));
				}
				for (IBehavior.Action action : this.behavior.getActions("reset", "@reset")) {
					run(action, message.scope.getChild(this.appId, action));
				}
			}
		}
		
		/*
		 * 2. send reset to all sub-actors that are components
		 */
		for (ActorRef<KlabMessage> actor : componentActors) {
			actor.tell(message);
		}

		/*
		 * 3. reset all UI components
		 */
		for (KlabActionExecutor executor : actionCache.values()) {
			if (executor instanceof KlabWidgetActionExecutor) {
				((KlabWidgetActionExecutor) executor).onMessage(mes, message.scope);
			}
		}
//		}
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> handleUserActionMessage(UserAction message) {

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
					KlabActionExecutor executor = actionCache.get(message.action.getComponent().getId());
					actions.match(executor instanceof KlabWidgetActionExecutor
							? ((KlabWidgetActionExecutor) executor).getFiredValue(message.action)
							: getActionValue(message.action));
				}
			} else if (message.action.getComponent().getActorPath() != null) {

				// dispatch to child actor
				String path = message.action.getComponent().getActorPath();
				String[] elements = path.split("\\.");
				if (elements.length > 0) {

					String actorId = elements[0];
					ActorRef<KlabMessage> receiver = receivers.get(actorId);
					if (receiver != null) {
						if (elements.length == 1) {
							message.action.getComponent().setActorPath(null);
						} else if (elements.length > 1) {
							message.action.getComponent().setActorPath(Path.getRemainder(path, "."));
						}
						receiver.tell(message);
					} else {
						message.scope.getMonitor()
								.error("unreferenced child actor " + actorId + " when handling message from UI");
					}
				}
			}
		}
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> handleCleanupMessage(Cleanup message) {

		/*
		 * this may intercept listeners
		 */
		for (KlabActionExecutor action : actionCache.values()) {
			action.dispose();
		}

		return this;
	}

	public static Object getActionValue(ViewAction action) {
		if (action.isBooleanValue() != null) {
			return action.isBooleanValue();
		} else if (action.getStringValue() != null) {
			return action.getStringValue();
		} else if (action.getDateValue() != null) {
			return action.getDoubleValue();
		} else if (action.getDoubleValue() != null) {
			return action.getDoubleValue();
		} else if (action.getIntValue() != null) {
			// ACHTUNG Jackson sticks a 0 in here even if the incoming JSON has null. MUST
			// keep this check last!
			return action.getIntValue();
		}
		return null;
	}

	@Override
	final public Receive<KlabMessage> createReceive() {
		return configure().build();
	}

	/*
	 * -----------------------------------------------------------------------
	 * k.Actors interpreter
	 * -----------------------------------------------------------------------
	 */

	protected void run(IBehavior.Action action, Scope scope) {
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
		 * use the actor name to install a listener for any actions that may be
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
		Map<String, Object> arguments = new HashMap<>();
		if (code.getArguments() != null) {
			/*
			 * TODO match the arguments to the correspondent names for the declaration of
			 * main()
			 */
			IBehavior childBehavior = Actors.INSTANCE.getBehavior(code.getBehavior());
			if (childBehavior == null) {
				this.getIdentity().getMonitor()
					.error("unreferenced child behavior: " + code.getBehavior() + " when execute instantiation");
				return;
			}
			Action main = childBehavior.getAction("main");
			int n = 0;
			for (int i = 0; i < main.getStatement().getArgumentNames().size(); i++) {
				String arg = main.getStatement().getArgumentNames().get(i);
				Object value = code.getArguments().get(arg);
				if (value == null && code.getArguments().getUnnamedKeys().size() > n) {
					value = code.getArguments().get(code.getArguments().getUnnamedKeys().get(n++));
					if (value instanceof KActorsValue) {
						value = evaluateInScope((KActorsValue) value, scope);
					}
				}
				arguments.put(arg, value);
			}
		}

		IBehavior actorBehavior = Actors.INSTANCE.getBehavior(code.getBehavior());
		if (actorBehavior != null) {

			/*
			 * AppID in message is null because this is run by the newly spawned actor; we
			 * communicate the overall appID through the specific field below.
			 */
			Load loadMessage = new Load(this.identity, code.getBehavior(), null, scope)
					.withChildActorPath(
							this.childActorPath == null ? actorName : (this.childActorPath + "." + actorName))
					.withActorBaseName(code.getActorBaseName()).withMainArguments(arguments)
					.withApplicationId(this.appId).withParent(getContext().getSelf());

			Semaphore semaphore = null;
			if (actorBehavior.getDestination() == Type.COMPONENT) {
				/*
				 * synchronize by default
				 */
				semaphore = Actors.INSTANCE.createSemaphore(Semaphore.Type.LOAD);
				loadMessage.withSemaphore(semaphore);
				componentActors.add(actor);
			}

			actor.tell(loadMessage);

			if (semaphore != null) {
				waitForGreen(semaphore);
			}

			receivers.put(actorName, actor);

			if (actors == null) {
				actors = new ArrayList<>();
				this.childInstances.put(actorName, actors);
			}

			actors.add(actor);
		}
	}

	private void waitForGreen(Semaphore semaphore) {

		while (!Actors.INSTANCE.expired(semaphore)) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				return;
			}
		}
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
		Scope groupScope = scope.getChild(code);
		for (IKActorsStatement statement : code.getStatements()) {
			execute(statement, groupScope);
		}
	}

	private void executeIf(If code, Scope scope) {

		Object check = evaluateInScope((KActorsValue) code.getCondition(), scope);
		if (KActorsValue.isTrue(check)) {
			if (code.getThen() != null) {
				execute(code.getThen(), scope);
			}
		} else {
			for (Pair<IKActorsValue, IKActorsStatement> conditions : code.getElseIfs()) {
				check = evaluateInScope((KActorsValue) conditions.getFirst(), scope);
				if (KActorsValue.isTrue(check)) {
					execute(conditions.getSecond(), scope);
					return;
				}
			}
			if (code.getElse() != null) {
				execute(code.getElse(), scope);
			}
		}

	}

	private void executeFor(For code, Scope scope) {
		// TODO Auto-generated method stub
	}

	private void executeFire(FireValue code, Scope scope) {

		if (scope.getNotifyId() != null) {
			// TODO don't think this is handled properly. Should catch its own fire in the
			// behavior - right?
			System.err.println("NOTIFY ID NON-NULL IN FIRE FROM ACTION - ENSURE IT'S HANDLED");
		}

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
			 * Fart in space: nothing is listening from the behavior being executed.
			 * 
			 * TODO - an actor firing with no action listening and no parent should just
			 * send to either the user actor or (maybe) its parent identity?
			 * 
			 * TODO - the outer group may be listening.
			 */

		}
	}

	private void executeDo(Do code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeAssignment(Assignment code, Scope scope) {
		if (code.getRecipient() != null) {
			if ("self".equals(code.getRecipient())) {
				this.identity.getState().put(code.getVariable(),
						evaluateInScope((KActorsValue) code.getValue(), scope));
			} else {
				// TODO find the actor reference and send it an internal message to set the
				// state. Should be subject to scope and authorization
				throw new KlabUnimplementedException("klab actor state setting is unimplemented");
			}
		} else {
			// set goes into the actor's symbol table, only parameters can override it.
			this.symbolTable.put(code.getVariable(), evaluateInScope((KActorsValue) code.getValue(), scope));
		}
	}

	protected static Object evaluate(Object value, Scope scope) {
		if (value instanceof KActorsValue) {
			return evaluateInScope((KActorsValue) value, scope, scope.identity);
		}
		return value;
	}

	protected Object evaluateInScope(KActorsValue arg, Scope scope) {
		return evaluateInScope(arg, scope, this.identity);
	}

	public static Object evaluateInScope(KActorsValue arg, Scope scope, IActorIdentity<?> identity) {
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
					Parameters.create(scope.getSymbols(identity)));

		case BOOLEAN:
		case CLASS:
		case DATE:
		case NUMBER:
		case RANGE:
		case STRING:
		case OBSERVABLE:
		case QUANTITY:
		case CONSTANT:
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
		case EMPTY:
			break;
		default:
			break;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void executeCall(Call code, Scope scope) {

		Long notifyId = scope.listenerId;

		if (code.getActions().size() > 0) {

			notifyId = nextId.incrementAndGet();
			MatchActions actions = new MatchActions(scope);
			for (Pair<IKActorsValue, IKActorsStatement> adesc : code.getActions()) {
				actions.matches.add(new Pair<Match, IKActorsStatement>(new Match(adesc.getFirst()), adesc.getSecond()));
			}
			this.listeners.put(notifyId, actions);
		}

		if (code.getGroup() != null) {
			// TODO finish handling group actions
			execute(code.getGroup(), scope.withNotifyId(notifyId));
			return;
		}

		String receiverName = "self";
		String messageName = code.getMessage();
		if (messageName.contains(".")) {
			receiverName = Path.getLeading(messageName, '.');
			messageName = Path.getLast(messageName, '.');
		}

		if (!"self".equals(receiverName)) {

			/*
			 * Check if the name corresponds to the tag of an executor created using new. If
			 * so, the actor (or component) has priority over a possible actor of the same
			 * name or a variable containing an actor.
			 */
			if (this.localActionExecutors.containsKey(receiverName)) {
				KActorsMessage m = new KActorsMessage(getContext().getSelf(), messageName, code.getCallId(),
						code.getArguments(), scope.withNotifyId(notifyId), appId);
				this.localActionExecutors.get(receiverName).onMessage(m, scope);
				return;
			}

			/*
			 * Otherwise, an actor reference with this local name may have been passed as a
			 * parameter or otherwise set in the symbol table as a variable.
			 */
			ActorRef<KlabMessage> recipient = null;
			if (scope.symbolTable.get(receiverName) instanceof IActorIdentity) {
				try {
					recipient = ((IActorIdentity<KlabMessage>) scope.symbolTable.get(receiverName)).getActor();
				} catch (Throwable t) {
					// TODO do something with the failed call, the actor should probably remember
					if (this.identity instanceof IRuntimeIdentity) {
						((IRuntimeIdentity) this.identity).getMonitor()
								.error("error executing actor call " + messageName + ": " + t.getMessage());
					}
					return;
				}
			} else {
				/*
				 * Only remaining choice for an explicit actor name must be in the recipient
				 * table.
				 */
				recipient = receivers.get(receiverName);
			}

			if (recipient == null) {
				/*
				 * No recipient, we just set this to the user actor which will turn the message
				 * into whatever is set for unknown messages. This not returning null guarantees
				 * that the message will arrive.
				 */
				recipient = identity.getParentIdentity(EngineUser.class).getActor();
			}

			recipient.tell(new KActorsMessage(getContext().getSelf(), messageName, code.getCallId(),
					code.getArguments(), scope.withNotifyId(notifyId), appId));

			return;

		}

		/*
		 * If we get here, the message is directed to self and it may specify an
		 * executor or a k.Actors behavior action. A coded action takes preference over
		 * a system behavior executor.
		 */
		Action actionCode = behavior.getAction(messageName);
		if (actionCode != null) {
			run(actionCode, scope.withNotifyId(notifyId));
			return;
		}

		String executorId = (this.childActorPath == null ? "" : (this.childActorPath + "_")) + code.getCallId();

		/*
		 * Remaining option is a code action executor installed through a system
		 * behavior. The executor cache is populated at every execution of the same
		 * call, so this will be instantiated only if the call has been executed before
		 * (in a loop or upon repeated calls of the same action).
		 */
		KlabActionExecutor executor = actionCache.get(executorId);

		if (executor == null) {
			Class<? extends KlabActionExecutor> actionClass = Actors.INSTANCE.getActionClass(messageName);
			if (actionClass != null) {

				executor = Actors.INSTANCE.getSystemAction(messageName, this.getIdentity(), code.getArguments(),
						scope.withNotifyId(notifyId), getContext().getSelf(), executorId);

				if (executor != null) {

					actionCache.put(executorId, executor);

					if (executor instanceof KlabActionExecutor.Actor) {

						/*
						 * if it has a tag, store for later reference.
						 */
						if (code.getArguments().containsKey("tag")) {
							Object t = code.getArguments().get("tag");
							if (t instanceof KActorsValue) {
								t = ((KActorsValue) t).getValue();
							}
							((KlabActionExecutor.Actor) executor).setName(t.toString());
							this.localActionExecutors.put(((KlabActionExecutor.Actor) executor).getName(),
									(KlabActionExecutor.Actor) executor);
						}
					}

					/*
					 * if there are actions, set the bindings
					 */
					if (code.getActions().size() > 0) {
						this.actionBindings.put(executorId, notifyId);
					}
				}
			}
		}

		if (executor instanceof KlabWidgetActionExecutor) {
			/*
			 * the run() method in these is never called: they act through their view
			 * components
			 */
			ViewComponent viewComponent = ((KlabWidgetActionExecutor) executor).getViewComponent();
			scope.viewScope.setViewMetadata(viewComponent, executor.arguments);
			viewComponent.setIdentity(this.identity.getId());
			viewComponent.setApplicationId(this.appId);
			viewComponent.setParentId(code.getCallId()); // check - seems
															// wrong
			viewComponent.setId(executorId);
			viewComponent.setActorPath(this.childActorPath);
			((KlabWidgetActionExecutor) executor).setInitializedComponent(viewComponent);
			
			scope.viewScope.currentComponent.getComponents().add(viewComponent);
		} else if (executor != null) {
			executor.run(scope.withNotifyId(notifyId).withSender(getContext().getSelf(), appId));
		}

	}

	/*
	 * -----------------------------------------------------------------------
	 * k.Actors behavior
	 * -----------------------------------------------------------------------
	 */

	protected Behavior<KlabMessage> handleBindActionMessage(BindUserAction message) {
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

	protected Behavior<KlabMessage> handleComponentFireMessage(ComponentFire message) {
		if (message.listenerId != null) {
			MatchActions actions = componentFireListeners.get(message.listenerId);
			if (actions != null) {
				actions.match(message.value);
			}
		}
		return Behaviors.same();
	}

	protected Behavior<KlabMessage> handleFireMessage(Fire message) {

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

	protected Behavior<KlabMessage> handleLoadBehaviorMessage(Load message) {

		this.parentActor = message.parent;
		message.scope.globalSymbols = this.symbolTable;

		if (message.forwardApplicationId != null) {
			/*
			 * spawn another actor and load the behavior in it.
			 */
			ActorRef<KlabMessage> child = getContext().spawn(
					SessionActor.create((Session) identity, message.forwardApplicationId),
					message.forwardApplicationId);
			this.receivers.put(message.forwardApplicationId, child);
			child.tell(message.direct());

		} else {

			boolean rootView = message.scope.viewScope.layout == null;

			/*
			 * preload system actors. We don't add "self" which should be factored out by
			 * the interpreter.
			 */
			if (!receivers.containsKey("user")) {
				// these three are the same. TODO check
				receivers.put("session", identity.getParentIdentity(Session.class).getActor());
				receivers.put("view", identity.getParentIdentity(Session.class).getActor());
				receivers.put("system", identity.getParentIdentity(Session.class).getActor());
				// user actor
				receivers.put("user", identity.getParentIdentity(EngineUser.class).getActor());
				// TODO modeler actor - which can create and modify projects and code
			}

			this.behavior = Actors.INSTANCE.getBehavior(message.behavior);
			this.listeners.clear();
			this.actionBindings.clear();
			this.actionCache.clear();
			this.childActorPath = message.childActorPath;
			if (message.applicationId != null) {
				// this only happens when we're spawning a component from a top application
				// using new; in that case, the appId is communicated here and the appId in
				// the message (which does not come from an application action) is null. This
				// ensures that all component actors have the same appId.
				this.appId = message.applicationId;
			}
			/*
			 * Init action called no matter what and before the behavior is set; the onLoad
			 * callback intervenes afterwards. Do not create UI (use raw scope).
			 */
			for (IBehavior.Action action : this.behavior.getActions("init", "@init")) {
				run(action, message.scope.forInit());
			}

			/*
			 * run any main actions. This is the only action that may create a UI.
			 */
			for (IBehavior.Action action : this.behavior.getActions("main", "@main")) {
				Scope scope = message.scope.getChild(this.appId, action);
				if (message.arguments != null) {
					scope.symbolTable.putAll(message.arguments);
				}
				run(action, scope);
			}

			/*
			 * send the view AFTER running main and collecting all components that generate
			 * views.
			 */
			if (rootView && message.scope.viewScope.layout != null) {
				System.out.println(Actors.INSTANCE.dumpView(message.scope.viewScope.layout));
				this.identity.setLayout(message.scope.viewScope.layout);
				this.identity.getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.SetupInterface,
						message.scope.viewScope.layout);
			}
		}

		/*
		 * move on, you waiters
		 */
		for (Semaphore semaphore : message.getSemaphores(Semaphore.Type.LOAD)) {
			Actors.INSTANCE.expire(semaphore);
		}

		return Behaviors.same();
	}

	/**
	 * Execute action through actor message. Either references one of our k.Actors
	 * action or has an appId to forward to. Unknown actions are sent to either a
	 * specialized method (TODO) or to the user actor with a specific message.
	 * 
	 * @param message
	 * @return
	 */
	protected Behavior<KlabMessage> handleCallMessage(KActorsMessage message) {

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
			Action action = this.behavior == null ? null : this.behavior.getAction(message.message);
			if (action != null) {
				run(action, message.scope.withSender(message.sender, this.appId));
			} else {
				/*
				 * unknown: send to the user actor which may implement this for logging or
				 * redirection. TODO bind to an action to intercept unknown messages if defined
				 * in our own behavior first.
				 */
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
	protected Behavior<KlabMessage> handleCreateChildMessage(Spawn message) {

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

	@Override
	public String toString() {
		return "{" + getContext().getSelf() + " - " + behavior.getName() + "}";
	}

}
