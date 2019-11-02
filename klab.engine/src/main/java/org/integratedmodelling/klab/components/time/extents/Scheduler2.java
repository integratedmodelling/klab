package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.scale.Scale;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the
 * latter so we need to roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class Scheduler2 implements IScheduler {

//	private HashedWheelTimer timer;
	private Type type;
	private long startTime = -1;
	private long endTime = -1;
	private Synchronicity synchronicity = Synchronicity.SYNCHRONOUS;
	private ITime overallTime; 
	
	/*
	 * Period of all subscribed actuators, to compute resolution.
	 */
	Set<Long> periods = new HashSet<>();
	class Registration {
		ITime time;
		Consumer<Long> action;
	}
	
	private List<Registration> registrations = new ArrayList<>();
	
	public static final long DEFAULT_RESOLUTION = TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS);
	public static final int DEFAULT_WHEEL_SIZE = 512;
	protected static final String DEFAULT_TIMER_NAME = "hashed-wheel-timer";

	private Set<Registration>[] wheel;
	private int wheelSize = 0;
	
	public Scheduler2(ITime time) {
		Date now = new Date();
		this.overallTime = time;
		this.type = time.is(ITime.Type.REAL) ? Type.REAL_TIME : Type.MOCK_TIME;
		this.startTime = time.getStart() == null ? now.getTime() : time.getStart().getMilliseconds();
		if (time.getEnd() != null) {
			this.endTime = time.getEnd().getMilliseconds();
		}
	}

	
	public void waitUntilEnd() {

		if (endTime < 0) {
			throw new IllegalStateException("Scheduler has no set endtime: can't wait until end");
		}
		
		for (;;) {
//			if (timer.getCurrentTime() >= endTime) {
//				return;
//			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// poh
			}
		}
	}
	
	
	@Override
	public Synchronicity getSynchronicity() {
		return synchronicity;
	}

//	private <V> Registration<V> scheduleFixedRate(long recurringTimeout, long firstDelay, Function<Long, V> callable) {
//
//		isTrue(recurringTimeout >= resolution, "Cannot schedule tasks for amount of time less than timer precision.");
//
//		int offset = (int) (recurringTimeout / resolution);
//		int rounds = offset / wheelSize;
//
//		int firstFireOffset = (int) (firstDelay / resolution);
//		int firstFireRounds = firstFireOffset / wheelSize;
//
//		Registration<V> r = new FixedRateRegistration<>(firstFireRounds, callable, recurringTimeout, rounds, offset);
//		wheel[idx(cursor + firstFireOffset + 1)].add(r);
//		return r;
//	}
	
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

		// save targets that were enqueued here
		Set<String> targets = new HashSet<>();

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

		Registration registration = new Registration();
		
		registration.time = scale.getTime();
		registration.action = new Consumer<Long>() {

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
				 * 1. Turn the millisecond t into the correspondent T extent for the
				 * observation's scale
				 */
				ITime transition = (ITime) scale.getTime().at(new TimeInstant(t));

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
					 * pick those that have transition trigger or whose geometry includes time.
					 * TODO condition should become clear and simple - for now it's nasty.
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
		
		registrations.add(registration);
		

		System.out.println("SCHEDULED " + actuator + " to run every " + step.getMilliseconds());
		
	}

	@Override
	public void run() {
		start();
		if (endTime > 0) {
			waitUntilEnd();
		}
	}
	
	@Override
	public void start() {
//		if (endTime < 0) {
//			timer.start();
//		} else {
//			timer.startUntil(endTime);
//		}
	}

	@Override
	public void stop() {

//		if (timer != null) {
//			timer.shutdownNow();
//			try {
//				timer.awaitTermination(1, TimeUnit.SECONDS);
//			} catch (InterruptedException e) {
//				Logging.INSTANCE.error(e);
//			}
//		}
	}

}
