package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.rest.SpatialExtent;

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
				this.listenerId = scope.getMonitor().getIdentity().getParentIdentity(ISession.class)
						.addObservationListener(new ISession.ObservationListener() {
							@Override
							public void newContext(ISubject observation) {
								fire(observation, false);
							}

							@Override
							public void newObservation(IObservation observation, ISubject context) {
							}
						});
			} else {

				Object arg = evaluateArgument(0, scope);
				if (arg instanceof Urn) {
					try {
						Future<ISubject> future = ((Session) identity).observe(((Urn) arg).getUrn());
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
				scope.getMonitor().getIdentity().getParentIdentity(ISession.class)
						.removeObservationListener(this.listenerId);
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
			+ "When done, the correspondent artifact (or an error) will be fired.")

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
				identity.getParentIdentity(Session.class).getState()
						.submit(KlabActor.evaluate(arguments.get(arguments.getUnnamedKeys().get(0)), scope).toString(),
								(observation) -> {
									if (observation == null) {
										fire(Status.STARTED, false);
									} else {
										fire(observation, false);
									}
								},
								(exception) -> {
									fire(exception, false);
								});
			}
		}

		@Override
		public void dispose() {
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
				this.listenerId = scope.getMonitor().getIdentity().getParentIdentity(Session.class)
						.addROIListener(new Session.ROIListener() {

							@Override
							public void onChange(final SpatialExtent extent) {

								String strategy = session.getGeocodingStrategy();
								String geocoded = Geocoder.INSTANCE.geocode(extent, strategy,
										session.getRegionNameOfInterest(), scope.getMonitor());
								Map<String, Object> ret = new HashMap<>();
								ret.put("description", geocoded);
								ret.put("resolution", extent.getGridResolution());
								ret.put("unit", extent.getGridUnit());
								ret.put("envelope", new double[] { extent.getWest(), extent.getSouth(),
										extent.getEast(), extent.getNorth() });

								fire(ret, false);
							}
						});
			} else {
				// TODO set from a previously saved map
			}
		}

		@Override
		public void dispose() {
			if (this.listenerId != null) {
				scope.getMonitor().getIdentity().getParentIdentity(Session.class).removeROIListener(this.listenerId);
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
