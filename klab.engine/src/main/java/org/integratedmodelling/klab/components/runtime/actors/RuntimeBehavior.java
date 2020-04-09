package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

/**
 * 
 * Messages:
 * <ul>
 * <li><b>maybe[(probability, value)]</b> fire value (default TRUE) with
 * probability (default 0.5)</li>
 * <li><b>choose(v1, v2, ..., vn)</b> fire one of the values with same
 * probability; if first value is a distribution and vals are same number, use
 * that to choose</li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@Behavior(id = "session", version = Version.CURRENT)
public class RuntimeBehavior {

	/**
	 * For the root context. TODO/FIXME check if this is a good ID - there should be
	 * a predicate to create a new object within a context, and it should be a
	 * different one, maybe "observe" is better for that and this should be
	 * suggestive of what it does.
	 * 
	 * @author Ferd
	 *
	 */
	@Action(id = "observe")
	public static class Observe extends KlabAction {

		public Observe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			// TODO Auto-generated method stub

		}
	}

	@Action(id = "maybe")
	public static class Maybe extends KlabAction {

		Random random = new Random();

		double probability = 0.5;
		Object fired = null;

		public Maybe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
			boolean pdef = false;
			for (String key : arguments.getUnnamedKeys()) {
				Object o = arguments.get(key);
				if (o instanceof Double && !pdef && ((Double) o) <= 1 && ((Double) o) >= 0) {
					probability = (Double) o;
					pdef = true;
				} else {
					fired = o;
				}
			}
		}

		@Override
		void run() {
			if (random.nextDouble() < probability) {
				fire(fired == null ? DEFAULT_FIRE : fired, true);
			} else {
				// fire anyway so that anything that's waiting can continue
				fire(false, true);
			}
		}
	}

	@Action(id = "info")
	public static class Info extends KlabAction {

		public Info(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue)arg) : arg);
			}
			scope.runtimeScope.getMonitor().info(args.toArray());
		}
	}
	
	@Action(id = "warn")
	public static class Warning extends KlabAction {

		public Warning(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue)arg) : arg);
			}
			scope.runtimeScope.getMonitor().warn(args.toArray());
		}
	}
	
	@Action(id = "error")
	public static class Error extends KlabAction {

		public Error(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue)arg) : arg);
			}
			scope.runtimeScope.getMonitor().error(args.toArray());
		}
	}
	
	@Action(id = "debug")
	public static class Debug extends KlabAction {

		public Debug(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue)arg) : arg);
			}
			scope.runtimeScope.getMonitor().debug(args.toArray());
		}
	}
}
