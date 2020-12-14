package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.AppReset;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;

import akka.actor.typed.ActorRef;

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
 * <p>
 * All these messages must be quick to execute, as all observations will queue
 * them here!
 * 
 * @author Ferd
 *
 */
@Behavior(id = "session", version = Version.CURRENT)
public class RuntimeBehavior {

	/**
	 * Set the root context
	 */
	@Action(id = "context", fires = Type.OBSERVATION, description = "Used with a URN as parameter, creates the context from an observe statement. If used without"
			+ " parameters, fire the observation when a new context is established")
	public static class Context extends KlabActionExecutor {

		String listenerId = null;

		public Context(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (arguments.getUnnamedKeys().isEmpty()) {
				this.listenerId = scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState()
						.addApplicationListener(new ISessionState.Listener() {
							@Override
							public void newContext(ISubject observation) {
								fire(observation, false);
							}

							@Override
							public void newObservation(IObservation observation, ISubject context) {
							}

							@Override
							public void scaleChanged(ScaleReference scale) {
							}

							@Override
							public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity) {
							}

						}, scope.appId);
			} else {

				Object arg = evaluateArgument(0, scope);
				if (arg instanceof Urn) {
					try {
						Future<IArtifact> future = ((Session) identity).getState().submit(((Urn) arg).getUrn());
						fire(future.get(), true);
					} catch (Throwable e) {
						fail();
					}
				}
			}
		}

		@Override
		public void dispose() {
			if (this.listenerId != null) {
				scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState()
						.removeListener(this.listenerId);
			}
		}
	}

	/**
	 * Make an observation, setting the context according to current preferences and
	 * session state, or set the context itself if the observation is a subject and
	 * the current context is not set.
	 */
	@Action(id = "submit", fires = Type.OBSERVATION, description = "Submit a URN for observation, either in the current context or creating one from the "
			+ " current preferences. The session will add it to the observation queue and make the observation when possible. "
			+ "When done, the correspondent artifact (or an error) will be fired. Before then, the action will fire WAITING when the task is "
			+ "queued, STARTED when it starts computing, and ABORTED in case of error.")

	public static class Submit extends KlabActionExecutor {

		String listenerId = null;

		public Submit(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (!arguments.getUnnamedKeys().isEmpty()) {
				fire(Status.WAITING, false);
				identity.getParentIdentity(Session.class).getState().submit(
						getUrnValue(KlabActor.evaluate(arguments.get(arguments.getUnnamedKeys().get(0)), scope)),
						(task, observation) -> {
							if (observation == null) {
								fire(Status.STARTED, false);
							} else {
								fire(observation, false);
							}
						}, (task, exception) -> {
							fire(Status.ABORTED, false);
						});
			}
		}

		private String getUrnValue(Object object) {
			if (object instanceof IConcept) {
				return ((IConcept) object).getDefinition();
			} else if (object instanceof IObservable) {
				return ((IObservable) object).getDefinition();
			}
			// TODO other situations?
			return object.toString();
		}

		@Override
		public void dispose() {
		}
	}

	/**
	 * Reset roles to the passed arguments. Pass individual observables/roles or
	 * collections thereof.
	 */
	@Action(id = "roles", fires = Type.EMPTY, description = "Apply a set of roles to one or more observables or observations. Any previously set roles are deactivated.")
	public static class SetRole extends KlabActionExecutor {

		String listenerId = null;

		public SetRole(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			Set<IConcept> roles = new HashSet<>();
			Set<IConcept> observables = new HashSet<>();

			for (Object arg : arguments.getUnnamedArguments()) {
				Object value = KlabActor.evaluate(arg, scope);
				if (value instanceof IObservable) {
					IConcept c = ((IObservable) value).getType();
					if (c.is(IKimConcept.Type.ROLE)) {
						roles.add(c);
					} else {
						observables.add(c);
					}
				} else if (value instanceof Collection) {
					for (Object cc : ((Collection<?>) value)) {
						if (cc instanceof IObservable) {
							IConcept c = ((IObservable) cc).getType();
							if (c.is(IKimConcept.Type.ROLE)) {
								roles.add(c);
							} else {
								observables.add(c);
							}
						}
					}
				}
			}

			session.getState().resetRoles();
			for (IConcept role : roles) {
				for (IConcept target : observables) {
					session.getState().addRole(role, target);
				}
			}
		}

		@Override
		public void dispose() {
		}
	}

	/**
	 * Make an observation, setting the context according to current preferences and
	 * session state, or set the context itself if the observation is a subject and
	 * the current context is not set.
	 */
	@Action(id = "scenarios", fires = Type.EMPTY, description = "Apply a session-specific role to one or more observables or observations.")
	public static class SetScenarios extends KlabActionExecutor {

		String listenerId = null;

		public SetScenarios(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			Set<String> scenarios = new HashSet<>();
			for (String key : arguments.getUnnamedKeys()) {
				scenarios.add(KlabActor.evaluate((IKActorsValue) arguments.get(key), scope).toString());
			}
			session.getState().setActiveScenarios(scenarios);
		}

		@Override
		public void dispose() {
			session.getState().getActiveScenarios().clear();
		}
	}

	/**
	 * Set the root context
	 */
	@Action(id = "locate", fires = Type.MAP, description = "Listens to setting of spatial extent outside of a context")
	public static class Locate extends KlabActionExecutor {

		String listenerId = null;

		public Locate(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {

			if (arguments == null || arguments.getUnnamedKeys().isEmpty()) {
				this.listenerId = scope.getMonitor().getIdentity().getParentIdentity(Session.class).getState()
						.addApplicationListener(new ISessionState.Listener() {

							@Override
							public void scaleChanged(ScaleReference scale) {
								Map<String, Object> ret = new HashMap<>();
								ret.put("description", scale.getName());
								ret.put("resolution", scale.getSpaceResolution());
								ret.put("unit", scale.getSpaceUnit());
								ret.put("envelope", new double[] { scale.getWest(), scale.getSouth(), scale.getEast(),
										scale.getNorth() });
								fire(ret, false);
							}

							@Override
							public void newContext(ISubject context) {
							}

							@Override
							public void newObservation(IObservation observation, ISubject context) {
							}

							@Override
							public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity) {
							}

							
						}, scope.appId);
			} else {
				// TODO set from a previously saved map
			}
		}

		@Override
		public void dispose() {
			if (this.listenerId != null) {
				scope.getMonitor().getIdentity().getParentIdentity(Session.class).getState()
						.removeListener(this.listenerId);
			}
		}
	}

	@Action(id = "maybe", fires = Type.BOOLEAN)
	public static class Maybe extends KlabActionExecutor {

		Random random = new Random();

		double probability = 0.5;
		Object fired = null;

		public Maybe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
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
		void run(KlabActor.Scope scope) {
			if (random.nextDouble() < probability) {
				fire(fired == null ? DEFAULT_FIRE : fired, true);
			} else {
				// fire anyway so that anything that's waiting can continue
				fire(false, true);
			}
		}
	}

	@Action(id = "reset", fires = {})
	public static class Reset extends KlabActionExecutor {

		public Reset(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			scope.sender.tell(new AppReset(scope, scope.appId));
		}
	}

	@Action(id = "info", fires = {})
	public static class Info extends KlabActionExecutor {

		public Info(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue) arg, scope) : arg);
			}
			scope.runtimeScope.getMonitor().info(args.toArray());
		}
	}

	@Action(id = "warning", fires = {})
	public static class Warning extends KlabActionExecutor {

		public Warning(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue) arg, scope) : arg);
			}
			scope.runtimeScope.getMonitor().warn(args.toArray());
		}
	}

	@Action(id = "error", fires = {})
	public static class Error extends KlabActionExecutor {

		public Error(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue) arg, scope) : arg);
			}
			scope.runtimeScope.getMonitor().error(args.toArray());
		}
	}

	@Action(id = "debug", fires = {})
	public static class Debug extends KlabActionExecutor {

		public Debug(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
				ActorRef<KlabMessage> sender, String callId) {
			super(identity, arguments, scope, sender, callId);
		}

		@Override
		void run(KlabActor.Scope scope) {
			List<Object> args = new ArrayList<>();
			for (Object arg : arguments.values()) {
				args.add(arg instanceof KActorsValue ? evaluateInContext((KActorsValue) arg, scope) : arg);
			}
			scope.runtimeScope.getMonitor().debug(args.toArray());
		}
	}
}
