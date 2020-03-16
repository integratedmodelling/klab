package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assignment;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.api.IKActorsStatement.Do;
import org.integratedmodelling.kactors.api.IKActorsStatement.FireValue;
import org.integratedmodelling.kactors.api.IKActorsStatement.For;
import org.integratedmodelling.kactors.api.IKActorsStatement.If;
import org.integratedmodelling.kactors.api.IKActorsStatement.Sequence;
import org.integratedmodelling.kactors.api.IKActorsStatement.TextBlock;
import org.integratedmodelling.kactors.api.IKActorsStatement.While;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.components.runtime.actors.UserBehavior.UnknownMessage;
import org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior.Match;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;
import akka.actor.typed.scaladsl.Behaviors;

public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	protected IBehavior behavior;
	protected IActorIdentity<KlabMessage> identity;
	protected Map<Long, MatchActions> matchActions = Collections.synchronizedMap(new HashMap<>());

	AtomicLong nextId = new AtomicLong(0);

	/**
	 * Descriptor for actions to be taken when a firing is recorded with the ID used
	 * as key in matchActions.
	 * 
	 * @author Ferd
	 *
	 */
	class MatchActions {

		Long notifyId;
		List<Pair<Match, IKActorsStatement>> matches = new ArrayList<>();

		public void match(Object value, Scope scope) {

			Scope s = scope.withNotifyId(notifyId);

			for (Pair<Match, IKActorsStatement> match : matches) {
				if (match.getFirst().matches(value)) {
					execute(match.getSecond(), s);
				}
			}
		}

		public MatchActions(Long notifyId) {
			this.notifyId = notifyId;
		}
	}

	/**
	 * The main message for anything sent through k.Actors
	 * 
	 * @author Ferd
	 *
	 */
	public interface KlabMessage {
	}

	/**
	 * Runtime scope for all k.Actors statements. Root scopes are for each action.
	 * Local class so that the identity is accessible.
	 * 
	 * @author Ferd
	 *
	 */
	public class Scope extends Parameters<String> {

		Action action;
		boolean synchronous = false;
		Scope parent = null;
		IRuntimeScope runtimeScope;
		Long notifyId;
		private ActorRef<KlabMessage> sender;

		public Scope(Action action, IRuntimeScope scope) {
			this.action = action;
			this.runtimeScope = scope;
		}

		public Scope(Scope scope) {
			this.action = scope.action;
			this.synchronous = scope.synchronous;
			this.runtimeScope = scope.runtimeScope;
			this.parent = scope;
			this.notifyId = scope.notifyId;
			this.sender = scope.sender;
		}

		public String toString() {
			return "{S " + notifyId + " " + action + "}";
		}

		public Scope synchronous() {
			Scope ret = new Scope(this);
			ret.synchronous = true;
			return ret;
		}

		public Scope withNotifyId(Long id) {
			Scope ret = new Scope(this);
			ret.notifyId = id;
			return ret;
		}

		public IIdentity getIdentity() {
			return identity;
		}

		public Long getNotifyId() {
			return notifyId;
		}

		public IMonitor getMonitor() {
			return this.runtimeScope == null ? null : this.runtimeScope.getMonitor();
		}

		public Scope withSender(ActorRef<KlabMessage> sender) {
			Scope ret = new Scope(this);
			ret.sender = sender;
			return ret;
		}

	}

//	/**
//	 * Get the recipient actor for a named recipient. This will be matched to known
//	 * behavior declarations and to any actors set into the context by previous
//	 * actions. A null recipient will respond a reference to self.
//	 * 
//	 * @param recipient
//	 * @return
//	 */
//	ActorRef<KlabMessage> getRecipient(String recipient) {
//		if (recipient == null || "self".equals(recipient)) {
//			return getContext().getSelf();
//		} else if ("session".equals(recipient) || "view".equals(recipient)) {
//
//		} else if ("user".equals(recipient)) {
//
//		}
//		return null;
//	}

	protected void waitForCompletion(KlabMessage message) {

	}

	protected IIdentity getIdentity() {
		return this.identity;
	}

	protected KlabActor(ActorContext<KlabMessage> context, IActorIdentity<KlabMessage> identity) {
		super(context);
		this.identity = identity;
	}

	/**
	 * Basic messages. Redefine extending the result of super() to add.
	 * 
	 * @return a builder for the behavior
	 */
	protected ReceiveBuilder<KlabMessage> configure() {
		ReceiveBuilder<KlabMessage> builder = newReceiveBuilder();
		return builder.onMessage(Load.class, this::loadBehavior).onMessage(Spawn.class, this::createChild)
				.onMessage(Fire.class, this::reactToFire).onMessage(KActorsMessage.class, this::executeCall)
				.onSignal(PostStop.class, signal -> onPostStop());
	}

	protected KlabActor onPostStop() {
		// TODO deactivate the underlying observation, send changes
		return this;
	}

	protected Behavior<KlabMessage> reactToFire(Fire message) {
		if (message.scope.notifyId != null) {
			MatchActions actions = matchActions.get(message.scope.notifyId);
			if (actions != null) {
				actions.match(message.value, message.scope);
				if (message.finalize) {
					matchActions.remove(message.scope.notifyId);
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
		this.behavior = Actors.INSTANCE.getBehavior(message.behavior);
		for (IBehavior.Action action : this.behavior.getActions("main", "@main")) {
			run(action, new Scope(action, message.scope));
		}
		return Behaviors.same();
	}

	protected void run(IBehavior.Action action, Scope scope) {
		execute(action.getStatement().getCode(), scope);
	}

	private void execute(IKActorsStatement code, Scope scope) {
		switch (code.getType()) {
		case ACTION_CALL:
			executeCall((IKActorsStatement.Call) code, scope/* .get(code) */);
			break;
		case ASSIGNMENT:
			executeAssignment((IKActorsStatement.Assignment) code, scope/* .get(code) */);
			break;
		case DO_STATEMENT:
			executeDo((IKActorsStatement.Do) code, scope/* .get(code) */);
			break;
		case FIRE_VALUE:
			executeFire((IKActorsStatement.FireValue) code, scope/* .get(code) */);
			break;
		case FOR_STATEMENT:
			executeFor((IKActorsStatement.For) code, scope/* .get(code) */);
			break;
		case IF_STATEMENT:
			executeIf((IKActorsStatement.If) code, scope/* .get(code) */);
			break;
		case CONCURRENT_GROUP:
			executeGroup((IKActorsStatement.ConcurrentGroup) code, scope/* .get(code) */);
			break;
		case SEQUENCE:
			executeSequence((IKActorsStatement.Sequence) code, scope/* .get(code) */);
			break;
		case TEXT_BLOCK:
			executeText((IKActorsStatement.TextBlock) code, scope/* .get(code) */);
			break;
		case WHILE_STATEMENT:
			executeWhile((IKActorsStatement.While) code, scope/* .get(code) */);
			break;
		default:
			break;
		}
	}

	private void executeWhile(While code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeText(TextBlock code, Scope scope) {
		// TODO Auto-generated method stub

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
			scope.sender.tell(new Fire(code.getValue().getValue(), /* TODO FIXME boh */true, scope.parent));
		}
	}

	private void executeDo(Do code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeAssignment(Assignment code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeCall(Call code, Scope scope) {

		/*
		 * TODO establish message reply ID for listeners. Must be same for every
		 * internal message if there is a group: in that case, set the ID in the scope.
		 */

		Long notifyId = scope.notifyId;

		if (code.getActions().size() > 0) {

			notifyId = nextId.incrementAndGet();

			/*
			 * TODO install own action listeners
			 */
			MatchActions actions = new MatchActions(notifyId);
			for (Pair<IKActorsValue, IKActorsStatement> adesc : code.getActions()) {
				actions.matches.add(new Pair<Match, IKActorsStatement>(new Match(adesc.getFirst()), adesc.getSecond()));
			}
			this.matchActions.put(notifyId, actions);
		}

		if (code.getGroup() == null) {

			String receiver = "self";
			String message = code.getMessage();
			if (message.contains(".")) {
				receiver = Path.getLeading(message, '.');
				message = Path.getLast(message, '.');
			}

			// if we have no action for this message, send to user actor. Code should have
			// been fixed to
			// include the recipient when it is known.
			ActorRef<KlabMessage> recipient = null;
			switch (receiver) {
			case "self":
				recipient = getContext().getSelf();
				break;
			case "view":
			case "session":
				recipient = identity.getParentIdentity(Session.class).getActor();
				break;
			case "user":
				recipient = identity.getParentIdentity(EngineUser.class).getActor();
			}

			if (recipient == null || ("self".equals(receiver) && this.behavior.getActions(message) == null)) {
				Pair<String, ActorRef<KlabMessage>> resolved = Actors.INSTANCE.lookupRecipient(message, this.identity);
				receiver = resolved.getFirst();
				recipient = resolved.getSecond();
			}

			if (recipient == null) {
				// this not returning null guarantees that the message will be understood
				recipient = identity.getParentIdentity(EngineUser.class).getActor();
			}

			recipient.tell(new KActorsMessage(getContext().getSelf(), receiver, message, code.getArguments(),
					scope.withNotifyId(notifyId)));

		} else {

			execute(code.getGroup(), scope.withNotifyId(notifyId));

		}
	}

	protected Behavior<KlabMessage> executeCall(KActorsMessage message) {
		// message can be one of ours or a system one.
		boolean ran = false;
		Action action = this.behavior == null ? null : this.behavior.getAction(message.message);
		if (action != null) {
			ran = true;
			run(action, message.scope.withSender(message.sender));
		} else {
			KlabAction a = Actors.INSTANCE.getSystemAction(message.message, message.sender, message.arguments,
					message.scope);
			if (a != null) {
				ran = true;
				a.run();
			}
		}

		if (!ran) {
			this.identity.getParentIdentity(EngineUser.class).getActor().tell(new UnknownMessage(message));
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

		Behavior<KlabMessage> behavior = null;
		// TODO potentially more differentiation according to host
		if (message.identity instanceof Observation) {
			behavior = ObservationActor.create((Observation) message.identity);
		} else if (message.identity instanceof Session) {
			behavior = SessionActor.create((Session) message.identity);
		}
		ActorRef<KlabMessage> actor = getContext().spawn(behavior, message.identity.getId());
		message.identity.instrument(actor);
		return Behaviors.same();
	}

}
