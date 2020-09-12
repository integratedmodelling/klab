package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.SchedulerNotification;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.joda.time.DateTime;

import com.google.common.collect.Sets;

import akka.actor.typed.ActorRef;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the
 * latter so we need to roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public class Scheduler implements IScheduler {

	private Type type;
	private long startTime = -1;
	private long endTime = -1;
	private Synchronicity synchronicity = Synchronicity.SYNCHRONOUS;
	private AtomicBoolean stopped = new AtomicBoolean(false);
	private int cursor = 0;
	private ExecutorService executor;
	private WaitStrategy waitStrategy;
	private Dataflow dataflow;

	/*
	 * used to track change in states that don't have processes connected
	 */
	class Dependencies {
		public ObservedConcept observable;
		public Set<ObservedConcept> precursors = new HashSet<>();
		public Actuator actuator;
	}

	/*
	 * Period of all subscribed actuators, to compute resolution.
	 */
	Set<Long> periods = new HashSet<>();

	class Registration implements Comparable<Registration> {

		Actuator actuator;
		List<Actuator.Computation> computations;
		IDirectObservation target;
		IScale scale;
		Function<IMonitor, Collection<ObservedConcept>> action;
		long tIndex = 0;
		long endTime;
		IRuntimeScope scope;
		// rounds around the wheel; ready to fire when rounds == 0
		int rounds;

		/*
		 * Used to sort by milliseconds from beginning of slot time so that temporal
		 * sequence is respected.
		 */
		long delayInSlot;
		private Observation recipient;
		private int dependencyOrder;
		public ITime time;

		/**
		 * Register a scheduled action from a behavior.
		 * 
		 * @param observation
		 * @param scheduled
		 * @param scale
		 * @param scope
		 * @param endtime
		 */
		public Registration(Observation observation, IBehavior.Action scheduled, IScale scale, IRuntimeScope scope,
				long endtime) {

			this.scale = scale;
			this.endTime = endtime;
			this.scope = scope;
			this.recipient = observation;

			action = new Function<IMonitor, Collection<ObservedConcept>>() {

				@Override
				public Collection<ObservedConcept> apply(IMonitor monitor) {

					Set<ObservedConcept> ret = new HashSet<>();

					/*
					 * If target is dead, return
					 */
					if (recipient instanceof IDirectObservation && !((IDirectObservation) recipient).isActive()) {
						return ret;
					}

					/*
					 * 2. Set the context at() the current time. This will also need to expose any
					 * affected outputs that move at a different (context) speed through a rescaling
					 * wrapper. Done within the context, which uses its current target to establish
					 * the specific view of the context.
					 */
					ILocator transitionScale = scale.at(time);
					IRuntimeScope transitionContext = scope.locate(transitionScale, monitor);
					Set<IObservation> changed = new HashSet<>();
					ActorRef<KlabMessage> sender = ((Observation) observation.getScope().getRootSubject()).getActor();

					/*
					 * RUN THE ACTION
					 */
					String appId = null /* TODO! */;
					recipient.getActor().tell(new KActorsMessage(sender, "self", scheduled.getId(), null, null,
							new KlabActor.Scope(observation, appId, transitionContext), appId));

					recipient.finalizeTransition((IScale) transitionScale);

					/*
					 * 4. TODO this will always be empty - notify whatever has changed.
					 */
					if (recipient instanceof IDirectObservation && !((IDirectObservation) recipient).isActive()) {
						// TODO target went MIA - notify relatives
					} else {

						for (IObservation observation : changed) {

							ret.add(new ObservedConcept(observation.getObservable(),
									observation instanceof ObservationGroup ? Mode.INSTANTIATION : Mode.RESOLUTION));

							IObservation parent = scope.getParentArtifactOf(observation);
							if (parent != null && scope.getWatchedObservationIds().contains(parent.getId())) {

								ObservationChange change = new ObservationChange();
								change.setContextId(scope.getRootSubject().getId());
								change.setId(observation.getId());
								change.setTimestamp(time.getEnd().getMilliseconds());

								if (observation instanceof IState) {
									change.setType(ObservationChange.Type.ValueChange);
								} else if (observation instanceof IDirectObservation
										&& !((IDirectObservation) observation).isActive()) {
									change.setType(ObservationChange.Type.Termination);
								}

								ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
								session.getMonitor()
										.send(Message.create(session.getId(),
												IMessage.MessageClass.ObservationLifecycle,
												IMessage.Type.ModifiedObservation, change));
							} else {
								((Observation) observation).setDynamic(true);
							}
						}
					}

					// TODO determine what has changed

					return ret;
				}
			};
		}

		/**
		 * Register a computation from an actuator
		 * 
		 * @param actuator
		 * @param computation
		 * @param target
		 * @param scale
		 * @param scope
		 * @param endtime
		 */
		public Registration(Actuator actuator, List<Actuator.Computation> computation, IDirectObservation target,
				IScale scale, IRuntimeScope scope, long endtime) {

			this.actuator = actuator;
			this.scale = scale;
			this.target = target;
			this.endTime = endtime;
			this.scope = scope;
			this.computations = computation;

			action = new Function<IMonitor, Collection<ObservedConcept>>() {

				@Override
				public Collection<ObservedConcept> apply(IMonitor monitor) {

					Set<ObservedConcept> ret = new HashSet<>();
					List<IObservation> changed = new ArrayList<>();

					/*
					 * If target is dead, return
					 */
					if (!target.isActive()) {
						return ret;
					}

					/*
					 * 2. Set the context at() the current time. This will also need to expose any
					 * affected outputs that move at a different (context) speed through a rescaling
					 * wrapper. Done within the context, which uses its current target to establish
					 * the specific view of the context.
					 */
					ILocator transitionScale = scale.at(time);
					IRuntimeScope transitionContext = scope.locate(transitionScale, monitor);

					/*
					 * TODO if the target is a group of events, it has been filtered to only contain
					 * those that occur in this transition. They must now be resolved in the
					 * localized scope (either using a cached dataset or from scratch) and be
					 * notified as they appear.
					 */
					if (target instanceof ObservationGroup && target.getObservable().is(IKimConcept.Type.EVENT)) {
						// TODO
					}

					// ensure we have the names we expect
					transitionContext = actuator.localizeNames(transitionContext);

					monitor.debug("running " + actuator + " at [" + new Date(time.getStart().getMilliseconds()) + " - "
							+ new Date(time.getEnd().getMilliseconds()) + "]");

					IArtifact artifact = null;

					/*
					 * 3. Run all contextualizers in the context that react to transitions; check
					 * for signs of life at each step. Anything enqueued here is active so no
					 * further check is necessary.
					 */
					for (Actuator.Computation computation : computations) {

						if (computation.variable != null) {
							transitionContext.getVariables().put(computation.targetId, computation.variable);
							continue;
						}

						if (artifact == null) {
							artifact = computation.target;
						}

						/*
						 * substitute the target for the next computation if we're using layers
						 */
						artifact = actuator.runContextualizer(computation.contextualizer, computation.observable,
								computation.resource, artifact, transitionContext, (IScale) transitionScale);

						if (computation.target instanceof IDirectObservation
								&& !((IDirectObservation) computation.target).isActive()) {
							changed.add((IObservation) computation.target);
							ret.add(new ObservedConcept(((IObservation) computation.target).getObservable(),
									((IObservation) computation.target) instanceof ObservationGroup ? Mode.INSTANTIATION
											: Mode.RESOLUTION));
							// TODO if in group, group has changed too
							break;
						}

						if (computation.target instanceof IProcess) {
							// report all changed states that were affected or created.
							for (IConcept affected : Observables.INSTANCE
									.getAffectedQualities(((IProcess) computation.target).getObservable().getType())) {
								IState state = scope.getArtifact(affected, IState.class);
								if (state != null) {
									// TODO only if changed!
									changed.add(state);
									ret.add(new ObservedConcept(state.getObservable(), Mode.RESOLUTION));
								}
							}
						}

						if (artifact instanceof Observation) {
							((Observation) artifact).finalizeTransition((IScale) transitionScale);
						}

					}

					/*
					 * report only states for now - must become discriminating and intelligent. If
					 * in folder...
					 *
					 */
					if (artifact instanceof IState /*
													 * TODO check if changes happened independent of type
													 */) {
						changed.add((IObservation) artifact);
						ret.add(new ObservedConcept(((IObservation) artifact).getObservable(),
								((IObservation) artifact) instanceof ObservationGroup ? Mode.INSTANTIATION
										: Mode.RESOLUTION));
					}

					/*
					 * 4. Notify whatever has changed.
					 */
					if (target instanceof IDirectObservation && !((IDirectObservation) target).isActive()) {
						// TODO target went MIA - notify relatives
					} else {

						for (IObservation observation : changed) {

							((Observation) observation).finalizeTransition((IScale) transitionScale);

							IObservation parent = scope.getParentArtifactOf(observation);
							if (parent != null && scope.getWatchedObservationIds().contains(parent.getId())) {

								ObservationChange change = new ObservationChange();
								change.setContextId(scope.getRootSubject().getId());
								change.setId(observation.getId());
								change.setTimestamp(time.getEnd().getMilliseconds());

								if (observation instanceof IState) {
									change.setType(ObservationChange.Type.ValueChange);
								} else if (observation instanceof IDirectObservation
										&& !((IDirectObservation) observation).isActive()) {
									change.setType(ObservationChange.Type.Termination);
								}

								ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
								session.getMonitor()
										.send(Message.create(session.getId(),
												IMessage.MessageClass.ObservationLifecycle,
												IMessage.Type.ModifiedObservation, change));
							} else {
								((Observation) observation).setDynamic(true);
							}
						}
					}

					return ret;
				}
			};
		}

		public Collection<ObservedConcept> run(IMonitor monitor) {

			if (synchronicity == Synchronicity.SYNCHRONOUS) {
				/*
				 * run in current thread, return when finished
				 */
				return action.apply(monitor);
			} else if (synchronicity == Synchronicity.ASYNCHRONOUS) {
				throw new KlabUnimplementedException("asynchronous scheduling not implemented");
			} else if (synchronicity == Synchronicity.TIME_SYNCHRONOUS) {
				throw new KlabUnimplementedException("time-synchronous scheduling not implemented");
			}

			return null;

		}

		@Override
		public int compareTo(Registration o) {

			if (o == this) {
				return 0;
			}

			/*
			 * Simultaneous actions happen in order of dependency
			 */
			if (delayInSlot == o.delayInSlot) {
				return Integer.compare(dependencyOrder, o.dependencyOrder);
			}

			return Long.compare(delayInSlot, o.delayInSlot);
		}

		public void setDependencyOrder(int i) {
			this.dependencyOrder = i;
		}
	}

	private List<Registration> registrations = new ArrayList<>();

	public static final long DEFAULT_RESOLUTION = TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS);
	public static final int DEFAULT_WHEEL_SIZE = 512;
	public static final int MAX_HASH_WHEEL_SIZE = 2048;

	private Set<Registration>[] wheel;
	private int wheelSize = 0;
	private IMonitor monitor;
	private long resolution;
	private String contextId;
	private ISession session;
	private IResolutionScope scope;
	private int activeRegistrations;

	public Scheduler(String contextId, ITime time, IMonitor monitor) {
		this.contextId = contextId;
		this.session = monitor.getIdentity().getParentIdentity(ISession.class);
		Date now = new Date();
		this.monitor = monitor;
		this.type = time.is(ITime.Type.REAL) ? Type.REAL_TIME : Type.MOCK_TIME;
		this.startTime = time.getStart() == null ? 0 : time.getStart().getMilliseconds();
		if (time.getEnd() != null) {
			this.endTime = time.getEnd().getMilliseconds();
		}
		if (this.type == Type.REAL_TIME) {
			this.waitStrategy = new WaitStrategy.YieldingWait();
			if (this.startTime > 0 && now.getTime() > this.startTime) {
				this.startTime = 0;
			}
		}
	}

	public Scheduler(String contextId, IResolutionScope scope, IMonitor monitor) {
		this.contextId = contextId;
		this.scope = scope;
		this.session = monitor.getIdentity().getParentIdentity(ISession.class);
		Date now = new Date();
		this.monitor = monitor;
		ITime time = scope.getScale().getTime();
		this.type = time.is(ITime.Type.REAL) ? Type.REAL_TIME : Type.MOCK_TIME;
		this.startTime = time.getStart() == null ? 0 : time.getStart().getMilliseconds();
		if (time.getEnd() != null) {
			this.endTime = time.getEnd().getMilliseconds();
		}
		if (this.type == Type.REAL_TIME) {
			this.waitStrategy = new WaitStrategy.YieldingWait();
			if (this.startTime > 0 && now.getTime() > this.startTime) {
				this.startTime = 0;
			}
		}
	}

	@Override
	public Synchronicity getSynchronicity() {
		return synchronicity;
	}

	public void schedule(Action action, Observation targetObservation, Time time, RuntimeScope runtimeScope) {

		/*
		 * make a scale from the scheduling specs and merge
		 */
		final IScale overall = runtimeScope.getDataflow().getResolutionScale();
		final IScale actionScale = Scale.substituteExtent(overall, time);
		IScale scale = actionScale.merge(overall);

		/*
		 * proceed as with an actuator
		 */
		ITimeInstant start = scale.getTime().getStart();
		ITimeInstant end = scale.getTime().getStart();
		ITimeDuration step = scale.getTime().getStep();

		if (start == null || (overall.getTime().getStart() != null && start.isBefore(overall.getTime().getStart()))) {
			start = overall.getTime().getStart();
		}
		if (end == null || (overall.getTime().getEnd() != null && start.isAfter(overall.getTime().getEnd()))) {
			end = overall.getTime().getEnd();
		}
		if (step == null) {
			step = overall.getTime().getStep();
		}

		if (step /* still */ == null) {
			/*
			 * nothing can occur, nothing to do (TODO: except maybe finalization)
			 */
			return;
		}

		final long endTime = overall.getTime().getEnd() == null ? -1 : overall.getTime().getEnd().getMilliseconds();

		registrations.add(new Registration(targetObservation, action, scale, runtimeScope, endTime));
	}

	public void schedule(final Actuator actuator, final List<Actuator.Computation> computations,
			final IRuntimeScope scope) {

		if (this.dataflow == null) {
			this.dataflow = actuator.getDataflow();
		}

		/*
		 * overall scale fills in any missing info.
		 */
		final IScale overall = actuator.getDataflow().getResolutionScale();

		/*
		 * model and individual computables determine the temporal aspects of the
		 * geometry. By now that should be entirely captured in the model coverage.
		 */
		IScale modelScale = actuator.getModel() == null ? null : actuator.getModel().getCoverage(scope.getMonitor());

		/*
		 * should not be the case if we get here at all, but who knows. TODO: if there
		 * is no explicit temporal nature, a contextualizer should check if any of the
		 * dependencies have changed OR has changed data at T (even if computed before)
		 * and exec again only if so. This should be done by using the provenance graph
		 * for each artifact and checking those, rather than using a value change
		 * resulting from the actual update.
		 */
		if (overall.getTime() == null || modelScale.getTime() == null) {
			return;
		}

		/*
		 * complete the actuator's scale with the overall one, adding extent boundaries
		 * and the like but keeping the resolution and representation.
		 */
		IScale scale = modelScale.merge(overall);

		ITimeInstant start = scale.getTime().getStart();
		ITimeInstant end = scale.getTime().getStart();

		if (start == null || (overall.getTime().getStart() != null && start.isBefore(overall.getTime().getStart()))) {
			start = overall.getTime().getStart();
		}
		if (end == null || (overall.getTime().getEnd() != null && start.isAfter(overall.getTime().getEnd()))) {
			end = overall.getTime().getEnd();
		}

		IObservation targetObservation = (IObservation) scope.getTargetArtifact();
		if (targetObservation.getObservable().is(IKimConcept.Type.PROCESS)
				|| targetObservation.getObservable().is(IKimConcept.Type.EVENT)
				|| targetObservation.getObservable().is(IKimConcept.Type.QUALITY)) {
			targetObservation = scope.getContextObservation();
		}

		final IDirectObservation target = (IDirectObservation) targetObservation;
		final long endTime = overall.getTime().getEnd() == null ? -1 : overall.getTime().getEnd().getMilliseconds();

		registrations.add(new Registration(actuator, computations, target, scale, scope, endTime));
	}

	/*
	 * one-shot scheduling, re-entrant
	 */
	@SuppressWarnings("unchecked")
	public void schedule() {

		long longest = 0;

		List<Number> periods = new ArrayList<>();

		List<Registration> regs = registrations;

		/*
		 * all registrations should be scheduled in order of dependency. As long as
		 * there is only one resolution this is also the order of registration, but if
		 * there are successive resolutions for change this no longer holds.
		 */
		if (this.dataflow != null) {
			regs = computeDynamicDependencyOrder(regs, dataflow.getDependencies());
		}

		/*
		 * Size the
		 */
		for (Registration registration : regs) {
			long interval = registration.scale.getTime().getResolution() == null
					? (registration.scale.getTime().getEnd().getMilliseconds()
							- registration.scale.getTime().getStart().getMilliseconds())
					: registration.scale.getTime().getResolution().getSpan();
			periods.add(interval);
			if (longest < interval) {
				longest = interval;
			}
		}

		this.resolution = NumberUtils.gcd(NumberUtils.longArrayFromCollection(periods));

		/*
		 * wheel size should accommodate the longest interval @the chosen resolution,
		 * but we cap it at MAX_HASH_WHEEL_SIZE. Keep it in powers of 2 for predictable
		 * performance and geek factor.
		 * 
		 * TODO review to choose a power of two between 2^(4-15) based on how small the
		 * resolution is compared to the scale
		 */
		this.wheelSize = DEFAULT_WHEEL_SIZE; // Math.min(NumberUtils.nextPowerOf2((int) (longest / resolution)),
												// MAX_HASH_WHEEL_SIZE);
		monitor.debug(
				"created scheduler hash wheel of size " + this.wheelSize + " for resolution = " + this.resolution);
		this.wheel = new Set[this.wheelSize];

		/*
		 * now for the actual scheduling of the first step of each registration
		 */
		int i = 0;
		for (Registration registration : regs) {
			registration.setDependencyOrder(i++);
			this.activeRegistrations++;
			reschedule(registration, true);
		}

	}

	private List<Registration> computeDynamicDependencyOrder(List<Registration> registrations,
			Graph<ObservedConcept, DefaultEdge> dependencies) {

		/*
		 * Create a new dependency graph taking the dependency relationships from the
		 * original dataflow, considering only the actuators involved in the schedule.
		 */
		Graph<ObservedConcept, DefaultEdge> dynamicDependencies = new DefaultDirectedGraph<>(DefaultEdge.class);
		for (Registration r : registrations) {
			if (r.actuator != null) {
				dynamicDependencies.addVertex(new ObservedConcept(r.actuator.getObservable(), r.actuator.getMode()));
			}
		}

		for (ObservedConcept oc : dynamicDependencies.vertexSet()) {
			for (DefaultEdge dc : dependencies.incomingEdgesOf(oc)) {
				if (dynamicDependencies.containsVertex(dependencies.getEdgeSource(dc))) {
					dynamicDependencies.addEdge(dependencies.getEdgeSource(dc), oc);
				}
			}
		}

		/*
		 * For any observable coming from a secondary dataflow, add any dependency
		 * coming from the rule: if change in X depends on Y and we have change in Y,
		 * then change in X depends on change in Y. If the dependency creates a cycle,
		 * don't add it and cross fingers.
		 */
		List<ObservedConcept> ccs = new ArrayList<>(dynamicDependencies.vertexSet());
		for (ObservedConcept oc : dynamicDependencies.vertexSet()) {

			/*
			 * may not have all the dependencies. Check if: 1) it's change in y; 2) we have
			 * y in the original dependencies; and 3) we have any scheduled observable X
			 * that depend on y. If so, try adding the dependency between change in X and
			 * change in Y. If the dependency screws things up, warn and don't add it.
			 */
			if (oc.getObservable().is(IKimConcept.Type.CHANGE)) {

				/*
				 * the concept another scheduled observable may depend on.
				 */
				ObservedConcept changing = new ObservedConcept(
						Observable.promote(Observables.INSTANCE.getDescribedType(oc.getObservable().getType())),
						Mode.RESOLUTION);

				for (ObservedConcept dc : ccs) {

					if (dc.equals(oc)) {
						continue;
					}

					if (dependencies.containsVertex(changing) && Graphs.dependsOn(dc, changing, dependencies)) {

						/*
						 * try adding the dependency on a clone. If that creates cycles, warn and move
						 * on.
						 */
						Graph<ObservedConcept, DefaultEdge> clone = Graphs.copy(dynamicDependencies,
								new DefaultDirectedGraph<>(DefaultEdge.class));
						clone.addEdge(oc, dc);
						CycleDetector<ObservedConcept, DefaultEdge> cycles = new CycleDetector<>(clone);
						if (cycles.detectCycles()) {
							monitor.warn("Implicit temporal dependency of "
									+ Concepts.INSTANCE.getDisplayLabel(oc.getObservable().getType()) + " on "
									+ Concepts.INSTANCE.getDisplayLabel(dc.getObservable().getType())
									+ " ignored because of circular dependencies: consider review of model logics");
							continue;
						}
						dynamicDependencies = clone;
					}
				}
			}
		}

		Map<ObservedConcept, Registration> index = new HashMap<>();

		for (Registration r : registrations) {
			if (r.actuator != null) {
				index.put(new ObservedConcept(r.actuator.getObservable(), r.actuator.getMode()), r);
			}
		}

		List<Registration> ret = new ArrayList<>();
		TopologicalOrderIterator<ObservedConcept, DefaultEdge> order = new TopologicalOrderIterator<ObservedConcept, DefaultEdge>(
				dynamicDependencies);

		while (order.hasNext()) {
			ret.add(index.get(order.next()));
		}

		return ret;
	}

	@Override
	public void run(IMonitor monitor) {

		if (this.registrations.size() < 1) {
			return;
		}

		schedule();

		if (startTime == 0 && type == Type.REAL_TIME) {
			startTime = DateTime.now().getMillis();
		}

		/*
		 * notify start
		 */
		SchedulerNotification notification = new SchedulerNotification();
		notification.setContextId(contextId);
		notification.setType(SchedulerNotification.Type.STARTED);
		notification.setResolution(resolution);

		/*
		 * build a list of observations that will need to be recomputed if their
		 * precursors change (because they may change but there is no explicit change
		 * process) and their actuators for quick scanning.
		 */
		List<Dependencies> trackedStates = new ArrayList<>();
		if (this.scope != null) {

			for (ObservedConcept tracked : ((ResolutionScope) scope).getImplicitlyChangingObservables()) {

				Dependencies tracker = new Dependencies();

				/*
				 * Recover all DIRECT precursors and check if they can change
				 */

				/*
				 * if 1+ precursors, complete and add the descriptor
				 */
				if (tracker.precursors.size() > 0) {
					trackedStates.add(tracker);
				}
			}
		}

		monitor.info("Temporal transitions starting");

		monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
				IMessage.Type.SchedulingStarted, notification));

		long time = startTime;
		long lastAdvanced = startTime;

		while (this.activeRegistrations > 0) {

			if (this.wheel[cursor] != null && !this.wheel[cursor].isEmpty()) {

				List<Registration> regs = new ArrayList<>(this.wheel[cursor]);
				this.wheel[cursor].clear();

				Set<ObservedConcept> changed = new HashSet<>();

				long delay = 0;
				for (Registration registration : regs) {

					if (monitor.isInterrupted()) {
						break;
					}

					if (registration.rounds == 0) {

						if (type == Type.REAL_TIME && registration.delayInSlot > delay) {
							// FIXME NO! Must run in batches all those starting at the same time, then wait
							waitStrategy.waitUntil(time + delay);
							delay += registration.delayInSlot;
						}

						changed.addAll(registration.run(monitor));
						if (lastAdvanced < registration.time.getEnd().getMilliseconds()) {
							lastAdvanced = registration.time.getEnd().getMilliseconds();
						}
						reschedule(registration, false);

					} else {
						registration.rounds--;
						this.wheel[cursor].add(registration);
					}
				}

				/*
				 * TODO scan the list of qualities that should be recomputed when their
				 * precursors do.
				 */
				for (Dependencies tracked : trackedStates) {
					Set<ObservedConcept> toUpdate = Sets.intersection(tracked.precursors, changed);
					if (toUpdate.size() > 0) {
						// TODO recompute actuator - run() may have to return an equipped object to
						// avoid another setup mess
					}
				}

				SchedulerNotification passed = new SchedulerNotification();
				passed.setType(SchedulerNotification.Type.TIME_ADVANCED);
				passed.setContextId(contextId);
				passed.setCurrentTime(lastAdvanced);
				monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
						IMessage.Type.ScheduleAdvanced, passed));

			}

			cursor = (cursor + 1) % wheelSize;
			time += resolution;

			if (endTime > 0 && lastAdvanced >= endTime) {
				break;
			}

			if (waitStrategy != null) {
				waitStrategy.waitUntil(time);
			}
		}

		/*
		 * notify end
		 */
		notification.setType(SchedulerNotification.Type.FINISHED);
		notification.setCurrentTime(time);
		monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
				IMessage.Type.SchedulingFinished, notification));
	}

	private void reschedule(Registration registration, boolean first) {

		this.activeRegistrations--;

		if (stopped.get()) {
			return;
		}

		/*
		 * first time interval in registration, skip initialization
		 */
		if (first) {
			registration.time = (ITime) ((AbstractExtent) registration.scale.getTime()).getExtent(1);
		} else {
			registration.time = registration.time.getNext();
		}

		if (registration.time == null || registration.time.getEnd() == null) {
			return;
		}

		long executionTime = registration.time.getEnd().getMilliseconds();

		if (registration.scale.getTime().size() != IGeometry.INFINITE_SIZE
				&& executionTime > registration.scale.getTime().getEnd().getMilliseconds()) {
			return;
		}

		long stepSize = executionTime - registration.time.getStart().getMilliseconds();

		// how many slots we span in the wheel
		int span = (int) (stepSize / resolution);
		// how many spins of the wheel we need to take before it's our turn
		registration.rounds = span / wheelSize;
		// milliseconds left to wait once our slot is being computed
		registration.delayInSlot = executionTime % resolution;

		int slot = (cursor + span + (first ? 0 : 1)) % wheelSize;

		if (this.wheel[slot] == null) {
			this.wheel[slot] = new ConcurrentSkipListSet<Registration>();
		}

		this.wheel[slot].add(registration);

		this.activeRegistrations++;

	}

	@Override
	public void start(final IMonitor monitor) {
		new Thread() {
			@Override
			public void run() {
				Scheduler.this.run(monitor);
			}
		}.start();
	}

	@Override
	public void stop() {
		this.stopped.set(true);
		if (executor != null && !executor.isTerminated()) {
			executor.shutdownNow();
		}
	}

	@Override
	public boolean isEmpty() {
		return registrations.size() < 1;
	}

}
