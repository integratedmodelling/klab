package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Observables;
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
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.SchedulerNotification;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.joda.time.DateTime;

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
	private AtomicLong regId = new AtomicLong(0);

	/*
	 * Period of all subscribed actuators, to compute resolution.
	 */
	Set<Long> periods = new HashSet<>();

	class Registration implements Comparable<Registration> {

		long id = regId.incrementAndGet();

		Actuator actuator;
		List<Actuator.Computation> computations;
		IDirectObservation target;
		IScale scale;
		Consumer<Long> action;
		long tIndex = 0;
		long endTime;
		IRuntimeScope scope;
		// rounds around the wheel; ready to fire when rounds == 0
		int rounds;

		// if time is irregular, we compute this in advance and store here to avoid
		// wasting time
		// doing it again later.
		ITime transition;
		/*
		 * Used to sort by milliseconds from beginning of slot time so that temporal
		 * sequence is respected.
		 */
		long delayInSlot;

		public Registration(Actuator actuator, List<Actuator.Computation> computation, IDirectObservation target,
				IScale scale, IRuntimeScope scope, long endtime) {

			this.actuator = actuator;
			this.scale = scale;
			this.target = target;
			this.endTime = endtime;
			this.scope = scope;
			this.computations = computation;

			action = new Consumer<Long>() {

				@Override
				public void accept(Long t) {

					if (endTime > 0 && t > endTime) {
						return;
					}

					/*
					 * If target is dead, return
					 */
					if (!target.isActive()) {
						return;
					}

					/*
					 * 2. Set the context at() the current time. This will also need to expose any
					 * affected outputs that move at a different (context) speed through a rescaling
					 * wrapper. Done within the context, which uses its current target to establish
					 * the specific view of the context.
					 */
					ILocator transitionScale = scale.at(transition);
					IRuntimeScope transitionContext = scope.locate(transitionScale);

					Set<IObservation> changed = new HashSet<>();

					/*
					 * TODO if the target is a group of events, it has been filtered to only contain
					 * those that occur in this transition. They must now be resolved in the
					 * localized scope (either using a cached dataset or from scratch) and be
					 * notified as they appear.
					 */
					if (target instanceof ObservationGroup && target.getObservable().is(IKimConcept.Type.EVENT)) {
						System.out.println("SOCCMEL resolve the events");
					}

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

						actuator.runContextualizer(computation.contextualizer, computation.observable,
								computation.resource, computation.target, transitionContext, (IScale) transitionScale);

						if (computation.target instanceof IDirectObservation
								&& !((IDirectObservation) computation.target).isActive()) {
							changed.add((IObservation) computation.target);
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
								}
							}
						}

						/*
						 * report only states for now - must become discriminating and intelligent. If
						 * in folder...
						 *
						 */
						if (computation.target instanceof IState /*
																	 * TODO check if changes happened independent of
																	 * type
																	 */) {
							changed.add((IObservation) computation.target);
						}
					}

					/*
					 * 4. Notify whatever has changed.
					 */
					if (!target.isActive()) {
						// TODO target went MIA - notify relatives
					} else {

						for (IObservation observation : changed) {

							ObservationChange change = new ObservationChange();
							change.setContextId(scope.getRootSubject().getId());
							change.setId(observation.getId());
							change.setTimestamp(t);

							// TODO fill in
							if (observation instanceof IState) {
								change.setNewValues(true);
							} else if (observation instanceof IDirectObservation
									&& !((IDirectObservation) observation).isActive()) {
								change.setTerminated(true);
							}

							ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
							session.getMonitor()
									.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
											IMessage.Type.ModifiedObservation, change));
						}
					}

				}
			};
		}

		public void run(long time) {

//			System.out.println("Running at " + new Date(time));

			// run the action; if synchronous, run in current thread and return when
			// finished
			if (synchronicity == Synchronicity.SYNCHRONOUS) {
//				System.out.println("   " + transition + " FOR " + actuator);
				action.accept(time);
			} else if (synchronicity == Synchronicity.ASYNCHRONOUS) {
				throw new KlabUnimplementedException("asynchronous scheduling not implemented");
			} else if (synchronicity == Synchronicity.TIME_SYNCHRONOUS) {
				throw new KlabUnimplementedException("time-synchronous scheduling not implemented");
			}

		}

//		private Scheduler getEnclosingInstance() {
//			return Scheduler.this;
//		}

		@Override
		public int compareTo(Registration o) {
			if (o.id == id) {
				return 0;
			}
			if (delayInSlot == o.delayInSlot) {
				/*
				 * order of registration if running at same time. Without this, the concurrent
				 * set won't include the registration.
				 */
				return Long.compare(id, o.id);
			}
			return Long.compare(delayInSlot, o.delayInSlot);
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

	@Override
	public Synchronicity getSynchronicity() {
		return synchronicity;
	}

	public void schedule(final Actuator actuator, final List<Actuator.Computation> computations,
			final IRuntimeScope scope) {

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

		IObservation targetObservation = (IObservation) scope.getTargetArtifact();
		if (targetObservation.getObservable().is(IKimConcept.Type.PROCESS)
				|| targetObservation.getObservable().is(IKimConcept.Type.EVENT)
				|| targetObservation.getObservable().is(IKimConcept.Type.QUALITY)) {
			targetObservation = scope.getContextObservation();
		}

		final IDirectObservation target = (IDirectObservation) targetObservation;
		final long endTime = overall.getTime().getEnd() == null ? -1 : overall.getTime().getEnd().getMilliseconds();

		registrations.add(new Registration(actuator, computations, target, scale, scope, endTime));

//		System.out.println("SCHEDULED " + actuator + " to run every " + step.getMilliseconds());

	}

	/*
	 * one-shot scheduling, re-entrant
	 */
	@SuppressWarnings("unchecked")
	public void schedule() {

		long longest = 0;
		List<Number> periods = new ArrayList<>();

		/*
		 * figure out the MCD resolution. TODO this must change to reflect irregular
		 * intervals
		 */
		for (Registration registration : registrations) {
			long interval = registration.scale.getTime().getStep() == null
					? (registration.scale.getTime().getEnd().getMilliseconds()
							- registration.scale.getTime().getStart().getMilliseconds())
					: registration.scale.getTime().getStep().getCommonDivisorMilliseconds();
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
		 */
		this.wheelSize = Math.min(NumberUtils.nextPowerOf2((int) (longest / resolution)), MAX_HASH_WHEEL_SIZE);
		monitor.debug(
				"created scheduler hash wheel of size " + this.wheelSize + " for resolution = " + this.resolution);
		this.wheel = new Set[this.wheelSize];

		/*
		 * now for the actual scheduling of the first step
		 */
		for (Registration registration : this.registrations) {
			reschedule(registration, startTime, true);
		}

	}

	@Override
	public void run() {

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
		monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
				IMessage.Type.SchedulingStarted, notification));

		long time = startTime;
		while (true) {

			// fence with checks
			if (stopped.get() || (time + resolution) > endTime) {
				break;
			}

			if (this.wheel[cursor] != null && !this.wheel[cursor].isEmpty()) {

//				System.out.println("---- Running slot " + cursor + " -------------------");

				List<Registration> regs = new ArrayList<>(this.wheel[cursor]);
				this.wheel[cursor].clear();

				long delay = 0;
				for (Registration registration : regs) {
					if (registration.rounds == 0) {

						if (type == Type.REAL_TIME && registration.delayInSlot > delay) {
							waitStrategy.waitUntil(time + delay);
							delay += registration.delayInSlot;
						}

						registration.run(time + registration.delayInSlot);
						reschedule(registration, time, false);

					} else {
						registration.rounds--;
						this.wheel[cursor].add(registration);
					}
				}
			}

			cursor = (cursor + 1) % wheelSize;
			time += resolution;

			if (waitStrategy != null) {
				waitStrategy.waitUntil(time);
			}

			// check again
			if (stopped.get() || (time + resolution) > endTime) {
				break;
			}
		}

		/*
		 * notify end
		 */
		notification.setType(SchedulerNotification.Type.FINISHED);
		monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
				IMessage.Type.SchedulingFinished, notification));
	}

	private void reschedule(Registration registration, long startTime, boolean first) {

		/*
		 * FIXME this must become the next() time, adjusted to fit the resolution if
		 * necessary (should not be necessary).
		 */
		startTime = startTime + resolution;

		if (stopped.get()) {
			return;
		}

		if (registration.scale.getTime().size() != IGeometry.INFINITE_SIZE
				&& (registration.tIndex + 1) >= (registration.scale.getTime().size())) {
			return;
		}

		/*
		 * schedule only the first shot, will reschedule itself at execution. Choose the
		 * slot and set the offset from the beginning.
		 */
//		long start = startTime;
//		if (registration.scale.getTime().getStart() != null) {
//			start = registration.scale.getTime().getStart().getMilliseconds();
//			if (start < startTime) {
//				start = startTime;
//			}
//		}

		long stepSize = registration.scale.getTime().getStep().getMilliseconds();
		ITime step = ((ITime) ((Extent) registration.scale.getTime()).getExtent(++registration.tIndex));
		if (!registration.scale.getTime().isRegular()) {
			stepSize = step.getEnd().getMilliseconds() - step.getStart().getMilliseconds();
		}
		// store for efficiency when running
		registration.transition = step;

		// remove a millisecond to stay to the right edge of our slot
		stepSize--;

		// how many slots we span in the wheel
		int span = (int) (stepSize / resolution);
		// how many rounds we need to take before it's our turn
		registration.rounds = span / wheelSize;
		// milliseconds left to wait once in our own slot
		registration.delayInSlot = (startTime + stepSize) % resolution;

		int slot = (cursor + span + (first ? 0 : 1)) % wheelSize;

		if (this.wheel[slot] == null) {
			this.wheel[slot] = new ConcurrentSkipListSet<Registration>();
		}

		this.wheel[slot].add(registration);

//		System.out.println("   Rescheduled " + registration.actuator + " in slot " + slot + " after " + registration.rounds + " rounds");

	}

	@Override
	public void start() {
		new Thread() {
			@Override
			public void run() {
				Scheduler.this.run();
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
}
