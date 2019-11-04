package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.scheduling.WaitStrategy;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.joda.time.DateTime;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the
 * latter so we need to roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public class Scheduler2 implements IScheduler {

	private Type type;
	private long startTime = -1;
	private long endTime = -1;
	private Synchronicity synchronicity = Synchronicity.SYNCHRONOUS;
	private AtomicBoolean stopped = new AtomicBoolean(false);
	private int cursor = 0;
	private ExecutorService executor;
	private WaitStrategy waitStrategy;

	/*
	 * Period of all subscribed actuators, to compute resolution.
	 */
	Set<Long> periods = new HashSet<>();

	class Registration {

		Actuator actuator;
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

		public Registration(Actuator actuator, IDirectObservation target, IScale scale, IRuntimeScope scope,
				long endtime) {

			this.actuator = actuator;
			this.scale = scale;
			this.target = target;
			this.endTime = endtime;
			this.scope = scope;

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

//					/*
//					 * 1. Turn the millisecond t into the correspondent T extent for the
//					 * observation's scale
//					 */
//					ITime transition = (ITime) scale.getTime().at(new TimeInstant(t));

					/*
					 * 2. Set the context at() the current time. This will also need to expose any
					 * affected outputs that move at a different (context) speed through a rescaling
					 * wrapper. Done within the context, which uses its current target to establish
					 * the specific view of the context.
					 */
					ILocator transitionScale = Scale.substituteExtent(scale, transition);
					IRuntimeScope transitionContext = scope.locate(transitionScale);

					/*
					 * 3. Run all contextualizers in the context that react to transitions; check
					 * for signs of life at each step.
					 */
					for (Actuator.Computation computation : actuator.getContextualizers()) {

						/*
						 * pick those that have transition trigger or whose geometry includes time. TODO
						 * condition should become clear and simple - for now it's nasty.
						 */
						if (computation.resource.getTrigger() == Trigger.TRANSITION || (computation.resource
								.getTrigger() == Trigger.RESOLUTION
								&& ((computation.observable.getArtifactType().isOccurrent()
										&& computation.resource.getGeometry().getDimension(Dimension.Type.TIME) == null)
										|| (computation.resource.getGeometry().getDimension(Dimension.Type.TIME) != null
												&& scale.getTime().intersects(computation.resource.getGeometry()
														.getDimension(Dimension.Type.TIME)))))) {

							actuator.runContextualizer(computation.contextualizer, computation.observable,
									computation.resource, transitionContext.getArtifact(computation.targetId),
									transitionContext, (IScale) transitionScale);

							if (!target.isActive()) {
								break;
							}
						}
					}

					/*
					 * 4. Notify whatever has changed.
					 */
					if (!target.isActive()) {
						// went missing in action, notify relatives
					} else {
						// TODO
					}

				}
			};
		}

		public void run(long time) {

			System.out.println("Running at " + new Date(time));
			
			// run the action; if synchronous, run in current thread and return when
			// finished
			if (synchronicity == Synchronicity.SYNCHRONOUS) {
				System.out.println("   " + transition + " FOR " + actuator);
				action.accept(time);
			} else if (synchronicity == Synchronicity.ASYNCHRONOUS) {
				throw new KlabUnimplementedException("asynchronous scheduling not implemented");
			} else if (synchronicity == Synchronicity.TIME_SYNCHRONOUS) {
				throw new KlabUnimplementedException("time-synchronous scheduling not implemented");
			}

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

	public Scheduler2(ITime time, IMonitor monitor) {
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

	public void schedule(final Actuator actuator, final IRuntimeScope scope) {

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
		 * should not be the case if we get here at all, but who knows.
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

		registrations.add(new Registration(actuator, target, scale, scope, endTime));

		System.out.println("SCHEDULED " + actuator + " to run every " + step.getMilliseconds());

	}

	/*
	 * one-shot scheduling, re-entrant
	 */
	@SuppressWarnings("unchecked")
	public void schedule() {

		long longest = 0;
		List<Number> periods = new ArrayList<>();

		/*
		 * figure out the MCD resolution
		 */
		for (Registration registration : registrations) {
			periods.add(registration.scale.getTime().getStep().getCommonDivisorMilliseconds());
			if (longest < registration.scale.getTime().getStep().getMaxMilliseconds()) {
				longest = registration.scale.getTime().getStep().getMaxMilliseconds();
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
			reschedule(registration, startTime);
		}

	}

	@Override
	public void run() {

		schedule();

		if (startTime == 0 && type == Type.REAL_TIME) {
			startTime = DateTime.now().getMillis();
		}

		long time = startTime;
		while (true) {

			// fence with checks
			if (stopped.get() || (time + resolution) > endTime) {
				break;
			}

			if (this.wheel[cursor] != null) {

				List<Registration> regs = new ArrayList<>(this.wheel[cursor]);
				this.wheel[cursor].clear();
				
				for (Registration registration : regs) {
					if (registration.rounds == 0) {
						registration.run(time + registration.delayInSlot);
						reschedule(registration, time + resolution);
					} else {
						registration.rounds--;
						this.wheel[cursor].add(registration);
					}
				}
			}

			cursor = (cursor + 1) % wheelSize;
			time += resolution;

			if (waitStrategy != null) {
				try {
					waitStrategy.waitUntil(time);
				} catch (InterruptedException e) {
					monitor.error("scheduler interrupted");
					break;
				}
			}

			// check again
			if (stopped.get() || (time + resolution) > endTime) {
				break;
			}
		}
	}

	private void reschedule(Registration registration, long startTime) {
 
		if (stopped.get()) {
			return;
		}
		
		if (registration.scale.getTime().size() != IGeometry.INFINITE_SIZE
				&& registration.tIndex >= registration.scale.getTime().size()) {
			return;
		}

		/*
		 * schedule only the first shot, will reschedule itself at execution. Choose the
		 * slot and set the offset from the beginning.
		 */
		long start = startTime;
		if (registration.scale.getTime().getStart() != null) {
			start = registration.scale.getTime().getStart().getMilliseconds();
			if (start < startTime) {
				start = startTime;
			}
		}

		long stepSize = registration.scale.getTime().getStep().getMilliseconds();
		ITime step = ((ITime) ((Extent) registration.scale.getTime()).getExtent(registration.tIndex++));
		if (!registration.scale.getTime().isRegular()) {
			stepSize = step.getEnd().getMilliseconds() - step.getStart().getMilliseconds();
		}
		// store for efficiency when running
		registration.transition = step;

		// remove a millisecond to stay to the right edge of our slot
		stepSize --;
		
//		long offset = startTime - start + ((registration.tIndex - 1) * stepSize);
		// how many slots we span in the wheel
		int span = (int) (stepSize / resolution);
		// how many rounds we need to take before it's our turn
		registration.rounds = span / wheelSize;
		// milliseconds left to wait once in our own slot
		registration.delayInSlot = stepSize % resolution;

		int slot = (cursor + span + 1) % wheelSize;

		if (this.wheel[slot] == null) {
			this.wheel[slot] = new ConcurrentSkipListSet<Registration>(
					new Comparator<Registration>() {
						@Override
						public int compare(Registration o1, Registration o2) {
							return Long.compare(o1.delayInSlot, o2.delayInSlot);
						}
					});
		}

		this.wheel[slot].add(registration);

		System.out.println("   Rescheduled " + registration.actuator + " in slot " + slot + " after " + registration.rounds + " rounds");

		
	}

	@Override
	public void start() {
		new Thread() {
			@Override
			public void run() {
				Scheduler2.this.run();
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
