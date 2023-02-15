package org.integratedmodelling.klab.components.runtime.actors;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.code.ObjectExpression;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.Utils;

import akka.actor.typed.ActorRef;

/**
 * Abstract class for system actions written in Java and defined within behavior
 * classes. System will store these based {@link Behavior} and {@link Action}
 * annotations. All must have the same constructor.
 * 
 * @author Ferd
 *
 */
public abstract class KlabActionExecutor {

	/**
	 * An action implementing this interface will be saved in the actor where the
	 * calling behavior is running and be enabled to receive later messages just
	 * like an actor.
	 * 
	 * @author Ferd
	 *
	 */
	public interface Actor {

		/**
		 * The actor name, normally established using a tag.
		 * 
		 * @return
		 */
		String getName();

		/**
		 * Done by the calling actor using arguments and/or metadata
		 * 
		 * @param name
		 */
		void setName(String name);

		/**
		 * Implement the response to a messages sent in k.Actors.
		 * 
		 * @param message
		 * @param scope
		 */
		void onMessage(KlabMessage message, IKActorsBehavior.Scope scope);

	}

	/**
	 * A component is an Actor that reacts through an MVC pattern.
	 * 
	 * @author Ferd
	 *
	 */
	public interface Component extends Actor {

		/**
		 * Return a descriptor of the view component that will provide the view for this
		 * actor.
		 * 
		 * @return
		 */
		public ViewComponent getViewComponent();

	}

	protected ActorRef<KlabMessage> sender;
	protected IParameters<String> arguments;
	@Deprecated // REMOVE! just leave the appId and the monitor, take the rest by passing the
				// scope
				// in context
	protected IKActorsBehavior.Scope scope;
	protected IActorIdentity<KlabMessage> identity;
	protected Session session;
	// the ID of the call that generated this action in the k.Actors code. May be
	// null when the action is create by the
	// scheduler or other API.
	protected String callId;
	ObjectExpression expression = null;
	private boolean synchronous = true;
	private Set<IKActorsValue.Type> fired = EnumSet.noneOf(IKActorsValue.Type.class);

	protected final Boolean DEFAULT_FIRE = Boolean.TRUE;

	public KlabActionExecutor(IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
	        IKActorsBehavior.Scope scope, ActorRef<KlabMessage> sender, String callId) {
		this.sender = sender;
		this.session = identity == null ? null : identity.getParentIdentity(Session.class);
		this.arguments = arguments;
		this.scope = scope;
		this.identity = identity;
		this.callId = callId;
	}

	/**
	 * Called at actor shutdown
	 */
	protected void dispose() {

	}

	public void fire(Object value, IKActorsBehavior.Scope scope) {
		if (scope.getListenerId() != null) {
			this.sender.tell(new Fire(scope.getListenerId(), value/* , isFinal */,
					/**
					 * FIXME passing a non-null appId loses messages even if there is one app (shows
					 * in components created using modal windows). Eventually try to understand all
					 * this and if appId is necessary at all.
					 */
					null /* this.scope.appId */, scope.getSemaphore(), scope.getSymbols(identity)));
		}
	}

	// @SuppressWarnings("unchecked")
	public void fail(IKActorsBehavior.Scope scope, Object... args) {
		// Semaphore semaphore = null;
		// if (args != null) {
		// for (Object arg : args) {
		// if (arg instanceof Semaphore) {
		// semaphore = (Semaphore) arg;
		// }
		// }
		// if (scope != null) {
		// scope.runtimeScope.getMonitor().error((args == null || args.length == 0) ?
		// "Actor
		// failure" : args);
		// }
		// }
		fire(args != null && args.length > 0 ? args[0] : false, scope);
	}

	protected Object evaluate(Object argument, IKActorsBehavior.Scope scope) {
		if (argument instanceof KActorsValue) {
			argument = ((KActorsValue) argument).evaluate(scope, identity, false);
		}
		return argument;
	}

	protected Object evaluateArgument(String argument, IKActorsBehavior.Scope scope) {
		Object arg = arguments.get(argument);
		if (arg instanceof KActorsValue) {
			arg = ((KActorsValue) arg).evaluate(scope, identity, false);
		}
		return arg;
	}

	public boolean isSynchronized() {
		return this.synchronous;
	}
	
	public Set<KActorsValue.Type> getFiredTypes() {
		return this.fired;
	}
	
	protected void error(String message) {
		// TODO actor-specific error management
		scope.getRuntimeScope().getMonitor().error(message);
	}
	
	protected <T> T evaluateArgument(String argument, IKActorsBehavior.Scope scope, T defaultValue) {
		Object arg = evaluateArgument(argument, scope);
		return arg == null ? defaultValue
				: Utils.asType(arg, defaultValue == null ? Object.class : defaultValue.getClass());
	}

	protected <T> T evaluateArgument(int argumentIndex, IKActorsBehavior.Scope scope, T defaultValue) {
		Object arg = evaluateArgument(argumentIndex, scope);
		return arg == null ? defaultValue
				: Utils.asType(arg, defaultValue == null ? Object.class : defaultValue.getClass());
	}

	protected Object evaluateArgument(int argumentIndex, IKActorsBehavior.Scope scope) {
		Object arg = null;
		if (arguments != null && arguments.getUnnamedKeys().size() > argumentIndex) {
			arg = arguments.get(arguments.getUnnamedKeys().get(argumentIndex));
			if (arg instanceof KActorsValue) {
				arg = ((KActorsValue) arg).evaluate(scope, identity, false);
			}
		}
		return arg;
	}

	public String getStatementId() {
		return callId;
	}

	/**
	 * May be called more than once, so pass the scope again.
	 * 
	 * @param scope
	 */
	abstract void run(IKActorsBehavior.Scope scope);

	/**
	 * Notify the class definition from the annotation, so that the object can be
	 * configured for special cases.
	 * 
	 * @param action
	 */
	public void notifyDefinition(Action action) {
		this.synchronous = action.synchronize();
		for (KActorsValue.Type type : action.fires()) {
			this.fired.add(type);
		}
	}

}
