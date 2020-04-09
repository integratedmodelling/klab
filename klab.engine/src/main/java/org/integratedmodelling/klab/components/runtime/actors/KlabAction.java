package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.code.ObjectExpression;
import org.integratedmodelling.klab.exceptions.KlabException;
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
public abstract class KlabAction {

	protected ActorRef<KlabMessage> sender;
	protected IParameters<String> arguments;
	protected KlabActor.Scope scope;
	protected IActorIdentity<KlabMessage> identity;
	protected Session session;

	ObjectExpression expression = null;

	protected final Boolean DEFAULT_FIRE = Boolean.TRUE;

	public KlabAction(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
		this.sender = identity.getActor();
		this.session = identity.getParentIdentity(Session.class);
		this.arguments = arguments;
		this.scope = scope;
		this.identity = identity;
	}

	public void fire(Object value, boolean isFinal) {
		if (scope.listenerId != null) {
			this.sender.tell(new Fire(scope.listenerId, value, isFinal));
		}
	}

	protected Object evaluateArgument(String argument) {
		Object arg = arguments.get(argument);
		if (arg instanceof KActorsValue) {
			arg = evaluateInContext((KActorsValue) arg);
		}
		return arg;
	}

	protected void error(String message) {
		// TODO actor-specific error management
		scope.runtimeScope.getMonitor().error(message);
	}

	/**
	 * TODO call this - used to remove listeners etc. when an action has finished
	 * running and nothing should be fired.
	 */
	void dispose() {

	}

	protected Object evaluateInContext(KActorsValue arg) {
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
			return scope.symbolTable.get(arg.getValue().toString());

		case EXPRESSION:

			if (this.expression == null) {
				this.expression = new ObjectExpression((IKimExpression) arg.getValue(), scope.runtimeScope);
			}
			return this.expression.eval(scope.runtimeScope, identity);

		case BOOLEAN:
		case CLASS:
		case DATE:
		case NUMBER:
		case RANGE:
		case STRING:
			return arg.getValue();
		case LIST:
			// eval all args
			break;
		case MAP:
			break;
		case NODATA:
			return null;
		case OBSERVABLE:
			return Observables.INSTANCE.declare(arg.getValue().toString());
		case QUANTITY:
			break;
		case REGEXP:
			break;
		case TABLE:
			break;
		case TYPE:
			break;
		case URN:
			return new Urn(arg.getValue().toString());
		}
		return null;
	}

	protected <T> T evaluateArgument(String argument, T defaultValue) {
		Object arg = evaluateArgument(argument);
		return arg == null ? defaultValue
				: Utils.asType(arg, defaultValue == null ? Object.class : defaultValue.getClass());
	}

	protected <T> T evaluateArgument(int argumentIndex, T defaultValue) {
		Object arg = evaluateArgument(argumentIndex);
		return arg == null ? defaultValue
				: Utils.asType(arg, defaultValue == null ? Object.class : defaultValue.getClass());
	}

	protected Object evaluateArgument(int argumentIndex) {
		Object arg = null;
		if (arguments.getUnnamedKeys().size() > argumentIndex) {
			arg = arguments.get(arguments.getUnnamedKeys().get(argumentIndex));
			if (arg instanceof KActorsValue) {
				arg = evaluateInContext((KActorsValue) arg);
			}
		}
		return arg;
	}

	abstract void run();

}
