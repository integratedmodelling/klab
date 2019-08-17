package org.integratedmodelling.klab.components.time.extents;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelTimer;
import org.integratedmodelling.klab.engine.runtime.scheduling.WaitStrategy;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the
 * latter so we need to roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class Scheduler<T> implements IScheduler<T> {

	class DGraph extends DefaultDirectedGraph<T, DefaultEdge> {

		public DGraph(T observation) {
			super(DefaultEdge.class);
			addVertex(observation);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -7193783283781551257L;

	}

	private HashedWheelTimer timer;
	private Type type;
	private Map<Long, DGraph> reactors = new HashMap<>();
	private long startTime = -1;
	private long endTime = -1;
	private long interval = -1;
	private BiConsumer<T, Long> actionHandler;
	private BiConsumer<T, Long> errorHandler;
	private ITime time;

	class TreeNode {
		TreeNode(T element) {
			this.element = element;
		}

		T element;
		Deque<T> prerequisites = new LinkedList<>();
	}

	protected abstract ITime getTime(T object);

	public Scheduler(ITime time) {
		this.time = time;
		this.type = time.is(ITime.Type.REAL) ? Type.REAL_TIME : Type.MOCK_TIME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void merge(T temporalObject, T... requiredAntecedents) {

		ITime time = getTime(temporalObject);
		if (time == null || time.getStep().isEmpty()) {
			return;
		}

		if (startTime < 0 || startTime > time.getStart().getMillis()) {
			startTime = time.getStart().getMillis();
		}
		if (endTime < 0 || endTime < time.getEnd().getMillis()) {
			endTime = time.getEnd().getMillis();
		}

		DGraph graph = null;
		if (reactors.containsKey(time.getStep().getMilliseconds())) {
			(graph = reactors.get(time.getStep().getMilliseconds())).addVertex(temporalObject);
		} else {
			reactors.put(time.getStep().getMilliseconds(), (graph = new DGraph(temporalObject)));
		}

		if (requiredAntecedents != null) {
			for (T antecedent : requiredAntecedents) {

				// must have same period and phase

				graph.addVertex(antecedent);
				graph.addEdge(antecedent, temporalObject);
			}
		}
	}

	@Override
	public void start(BiConsumer<T, Long> tickHandler, BiConsumer<T, Long> timingErrorHandler) {

		this.actionHandler = tickHandler;
		this.errorHandler = timingErrorHandler;

		if (timer != null) {
			throw new IllegalStateException("a scheduler can only be started once");
		}

		long[] spans = new long[reactors.size()];
		int i = 0;
		for (Long l : reactors.keySet()) {
			spans[i++] = l;
		}
		this.interval = NumberUtils.lcm(spans);

		if (type == Type.REAL_TIME) {
			// for logging
			startTime = System.currentTimeMillis();
		}

		// adjust the start time to start in phase with the interval (CHECK)
		long remainder = startTime % interval;
		if (remainder != 0) {
			startTime -= (interval - remainder);
		}

		timer = new HashedWheelTimer(
				type == Type.MOCK_TIME ? TimeUnit.NANOSECONDS.convert(interval, TimeUnit.MILLISECONDS)
						: TimeUnit.MILLISECONDS.toNanos(10),
				512, type == Type.MOCK_TIME ? new WaitStrategy() {
					@Override
					public void waitUntil(long deadlineNanoseconds) throws InterruptedException {
						// TODO sleep if necessary until the current time hasn't been reached by all
						// observations
					}
				} : new WaitStrategy.SleepWait());

		/*
		 * schedule the main task at the smallest interval
		 */
		timer.scheduleWithFixedDelay(() -> {
			handleTick();
		}, 0, interval, TimeUnit.MILLISECONDS);
	}

	private void handleTick() {
		this.startTime += this.interval;
		for (Long key : reactors.keySet()) {
			if ((this.startTime % key) == 0) {
				callReactors(reactors.get(key));
			}
		}
	}

	private void callReactors(DGraph graph) {

		/*
		 * TODO check that previous executor has finished and wait (if mock time) or
		 * throw a specific exception (real time) for all the actors that have not
		 * finished computing, unless configured otherwise, so that it can be caught and
		 * the actor can be deactivated.
		 */

		// call all nodes concurrently, each calling its antecedents in order. The same
		// antecedent
		// could be in more than one node and should only be called once.
		// NO must use an actual dependency graph
//		for (TreeNode node : list) {
//			if (getTime(node.element).getStart().getMillis() >= (this.startTime - this.interval)) {
//				// TODO use topological sort. Should be able to enqueue groups in dependency
//				// order as soon as all deps are done.
//				System.out.println("Calling for " + this.startTime + ": " + node.element);
//			}
//		}

		// TODO schedule all tasks immediately
	}

	@Override
	public void stop() {

		if (timer != null) {
			timer.shutdownNow();
			try {
				timer.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Logging.INSTANCE.error(e);
			}
		}
	}

}
