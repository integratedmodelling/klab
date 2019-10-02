package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelMockTimer;
import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelTimer;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the
 * latter so we need to roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class Scheduler<T> implements IScheduler<T> {

//	class DGraph extends DefaultDirectedGraph<T, DefaultEdge> {
//
//		public DGraph(T observation) {
//			super(DefaultEdge.class);
//			addVertex(observation);
//		}
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -7193783283781551257L;
//
//	}

	private HashedWheelTimer timer;
	private Type type;
//	private Map<Long, DGraph> reactors = new HashMap<>();
	private long startTime = -1;
	private long endTime = -1;
//	private long interval = -1;
//	private BiConsumer<T, Long> actionHandler;
//	private BiConsumer<T, Long> errorHandler;
	private ITime overallTime;

//	class TreeNode {
//		TreeNode(T element) {
//			this.element = element;
//		}
//
//		T element;
//		Deque<T> prerequisites = new LinkedList<>();
//	}

	protected abstract ITime getTime(T object);

	public Scheduler(ITime time) {
		Date now = new Date();
		this.overallTime = time;
		this.type = time.is(ITime.Type.REAL) ? Type.REAL_TIME : Type.MOCK_TIME;
		this.startTime = time.getStart() == null ? now.getTime() : time.getStart().getMilliseconds();
		this.timer = this.type == Type.REAL_TIME ? new HashedWheelTimer(this.startTime)
				: new HashedWheelMockTimer(this.startTime);
		if (time.getEnd() != null) {
			this.endTime = time.getEnd().getMilliseconds();
		}
	}

	public long getTime() {
		return timer.getCurrentTime();
	}

	public void schedule(final Actuator actuator, final IRuntimeScope scope) {

		/*
		 * model and individual computables determine the temporal aspects of the
		 * geometry. By now that should be entirely captured in the model coverage.
		 */
		final IScale scale = actuator.getModel() == null ? null : actuator.getModel().getCoverage(scope.getMonitor());
		/*
		 * overall scale fills in any missing info.
		 */
		final IScale overall = actuator.getDataflow().getResolutionScale();

		/*
		 * should not be the case if we get here at all, but who knows.
		 */
		if (overall.getTime() == null || scale.getTime() == null) {
			return;
		}

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

		/*
		 * holder for the lists that we include in the contextualizing action
		 */
		final List<IContextualizer> resources = new ArrayList<>();

		/*
		 * We have a step and (possibly) a start and an end. Enqueue actions for all
		 * contextualizers that are established to be temporal...
//		 */
//		for (IContextualizer contextualizer : actuator.getComputation()) {
//
//			if (contextualizer.getGeometry() != null
//					&& contextualizer.getGeometry().getDimension(Dimension.Type.TIME) != null) {
//				resources.add(resource.getSecond());
//				if (resource.getSecond().getTarget() != null) {
//					targets.add(resource.getSecond().getTarget().getName());
//				}
//			}
//
//		}

		/*
		 * enqueue actions for transition
		 */
		timer.scheduleAtFixedRate(new Consumer<Long>() {

			@Override
			public void accept(Long t) {

				/*
				 * 1. Turn the millisecond t into the correspondent T extent for the
				 * observation's scale
				 */
				

				/*
				 * 2. Set the context at() the current time.
				 */

				/*
				 * 3. Run all contextualizers in the context; check for signs of life at each
				 * step.
				 */
//				for (IContextualizable ctx : resources) {
////					actuator.run
//				}
				
				/*
				 * 4. Notify whatever has changed.
				 */
				
			}
		}, /* TODO */0, step.getMilliseconds(), TimeUnit.MILLISECONDS);

		System.out.println("HOSTIA");
	}

//	@SuppressWarnings("unchecked")
////	@Override
//	public void merge(T temporalObject, T... requiredAntecedents) {
//
//		ITime time = getTime(temporalObject);
//		if (time == null || time.getStep().isEmpty()) {
//			return;
//		}
//
//		if (startTime < 0 || startTime > time.getStart().getMilliseconds()) {
//			startTime = time.getStart().getMilliseconds();
//		}
//		if (endTime < 0 || endTime < time.getEnd().getMilliseconds()) {
//			endTime = time.getEnd().getMilliseconds();
//		}
//
//		DGraph graph = null;
//		if (reactors.containsKey(time.getStep().getMilliseconds())) {
//			(graph = reactors.get(time.getStep().getMilliseconds())).addVertex(temporalObject);
//		} else {
//			reactors.put(time.getStep().getMilliseconds(), (graph = new DGraph(temporalObject)));
//		}
//
//		if (requiredAntecedents != null) {
//			for (T antecedent : requiredAntecedents) {
//
//				// must have same period and phase
//
//				graph.addVertex(antecedent);
//				graph.addEdge(antecedent, temporalObject);
//			}
//		}
//	}
//
////	@Override
//	public void start(BiConsumer<T, Long> tickHandler, BiConsumer<T, Long> timingErrorHandler) {
//
//		this.actionHandler = tickHandler;
//		this.errorHandler = timingErrorHandler;
//
//		if (timer != null) {
//			throw new IllegalStateException("a scheduler can only be started once");
//		}
//
//		long[] spans = new long[reactors.size()];
//		int i = 0;
//		for (Long l : reactors.keySet()) {
//			spans[i++] = l;
//		}
//		this.interval = NumberUtils.lcm(spans);
//
//		if (type == Type.REAL_TIME) {
//			// for logging
//			startTime = System.currentTimeMillis();
//		}
//
//		// adjust the start time to start in phase with the interval (CHECK)
//		long remainder = startTime % interval;
//		if (remainder != 0) {
//			startTime -= (interval - remainder);
//		}
//
//		timer = new HashedWheelTimer(
//				type == Type.MOCK_TIME ? TimeUnit.NANOSECONDS.convert(interval, TimeUnit.MILLISECONDS)
//						: TimeUnit.MILLISECONDS.toNanos(10),
//				512, type == Type.MOCK_TIME ? new WaitStrategy() {
//					@Override
//					public void waitUntil(long deadlineNanoseconds) throws InterruptedException {
//						// TODO sleep if necessary until the current time hasn't been reached by all
//						// observations
//					}
//				} : new WaitStrategy.SleepWait());
//
//		/*
//		 * schedule the main task at the smallest interval
//		 */
//		timer.scheduleWithFixedDelay(() -> {
//			handleTick();
//		}, 0, interval, TimeUnit.MILLISECONDS);
//	}
//
//	private void handleTick() {
//		this.startTime += this.interval;
//		for (Long key : reactors.keySet()) {
//			if ((this.startTime % key) == 0) {
//				callReactors(reactors.get(key));
//			}
//		}
//	}

//	private void callReactors(DGraph graph) {
//
//		/*
//		 * TODO check that previous executor has finished and wait (if mock time) or
//		 * throw a specific exception (real time) for all the actors that have not
//		 * finished computing, unless configured otherwise, so that it can be caught and
//		 * the actor can be deactivated.
//		 */
//
//		// call all nodes concurrently, each calling its antecedents in order. The same
//		// antecedent
//		// could be in more than one node and should only be called once.
//		// NO must use an actual dependency graph
////		for (TreeNode node : list) {
////			if (getTime(node.element).getStart().getMillis() >= (this.startTime - this.interval)) {
////				// TODO use topological sort. Should be able to enqueue groups in dependency
////				// order as soon as all deps are done.
////				System.out.println("Calling for " + this.startTime + ": " + node.element);
////			}
////		}
//
//		// TODO schedule all tasks immediately
//	}

	@Override
	public void start() {
		if (endTime < 0) {
			timer.start();
		} else {
			timer.startUntil(endTime);
		}
	}

	@Override
	public void stop() {

		if (timer != null) {
			timer.shutdownNow();
			try {
				timer.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Logging.INSTANCE.error(e);
			}
		}
	}

}
