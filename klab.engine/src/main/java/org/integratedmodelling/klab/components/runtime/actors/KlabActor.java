package org.integratedmodelling.klab.components.runtime.actors;

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
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.utils.Parameters;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.ReceiveBuilder;
import akka.actor.typed.scaladsl.Behaviors;

public class KlabActor extends AbstractBehavior<KlabActor.KlabMessage> {

	private IBehavior behavior;
	private IActorIdentity<KlabMessage> identity;

	/**
	 * The main message for anything sent through k.Actors
	 * 
	 * @author Ferd
	 *
	 */
	public interface KlabMessage {

		void initialize(Parameters<String> arguments);

		// unique ID to ensure reply and notification
		String getId();

	}

	/**
	 * Runtime scope for all k.Actors statements. Root scopes are for each action.
	 * Local class so that the identity is accessible.
	 * 
	 * @author Ferd
	 *
	 */
	class Scope extends Parameters<String> {

		Action action;
		boolean synchronous = false;
		Scope parent = null;

		public Scope(Action action) {
			this.action = action;
		}

		public Scope(Scope scope) {
			this.action = scope.action;
			this.synchronous = scope.synchronous;
			this.parent = scope;
			// TODO Auto-generated constructor stub
		}

		Scope get(IKActorsStatement statement) {
			return new Scope(this);
		}

		public Scope synchronous() {
			this.synchronous = true;
			return this;
		}

		public IIdentity getIdentity() {
			return identity;
		}
		
		/**
		 * Get the recipient actor for a named recipient. This will be matched to known
		 * behavior declarations and to any actors set into the context by previous
		 * actions. A null recipient will respond a reference to self.
		 * 
		 * @param recipient
		 * @return
		 */
		ActorRef<KlabMessage> getRecipient(String recipient) {
			if (recipient == null) {
				return getContext().getSelf();
			}
			return null;
		}

	}

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
				.onMessage(KActorsMessage.class, this::executeCall).onSignal(PostStop.class, signal -> onPostStop());
	}

	protected KlabActor onPostStop() {
		// TODO deactivate the underlying observation, send changes
		return this;
	}

	@Override
	final public Receive<KlabMessage> createReceive() {
		return configure().build();
	}

	protected Behavior<KlabMessage> loadBehavior(Load message) {
		this.behavior = Actors.INSTANCE.getBehavior(message.behavior);
		for (IBehavior.Action action : this.behavior.getActions("main", "@main")) {
			run(action);
		}
		return Behaviors.same();
	}

	protected void run(IBehavior.Action action) {
		execute(action.getStatement().getCode(), new Scope(action));
	}

	private void execute(IKActorsStatement code, Scope scope) {
		switch (code.getType()) {
		case ACTION_CALL:
			executeCall((IKActorsStatement.Call) code, scope.get(code));
			break;
		case ASSIGNMENT:
			executeAssignment((IKActorsStatement.Assignment) code, scope.get(code));
			break;
		case DO_STATEMENT:
			executeDo((IKActorsStatement.Do) code, scope.get(code));
			break;
		case FIRE_VALUE:
			executeFire((IKActorsStatement.FireValue) code, scope.get(code));
			break;
		case FOR_STATEMENT:
			executeFor((IKActorsStatement.For) code, scope.get(code));
			break;
		case IF_STATEMENT:
			executeIf((IKActorsStatement.If) code, scope.get(code));
			break;
		case CONCURRENT_GROUP:
			executeGroup((IKActorsStatement.ConcurrentGroup) code, scope.get(code));
			break;
		case SEQUENCE:
			executeSequence((IKActorsStatement.Sequence) code, scope.get(code));
			break;
		case TEXT_BLOCK:
			executeText((IKActorsStatement.TextBlock) code, scope.get(code));
			break;
		case WHILE_STATEMENT:
			executeWhile((IKActorsStatement.While) code, scope.get(code));
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
		// TODO Auto-generated method stub

	}

	private void executeDo(Do code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeAssignment(Assignment code, Scope scope) {
		// TODO Auto-generated method stub

	}

	private void executeCall(Call code, Scope scope) {
		// TODO Auto-generated method stub
		// TODO install
	}

	protected Behavior<KlabMessage> executeCall(KActorsMessage message) {
		return Behaviors.same();
	}

	private Object evaluate(IKActorsValue value, Scope scope) {
		return null;
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
