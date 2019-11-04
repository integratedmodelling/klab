package org.integratedmodelling.klab.api.runtime;

/**
 * An observation-specific scheduler that can run temporal transitions over a
 * set of differently scaled observations in mock time or in real time, ensuring
 * dependency synchronization when necessary.
 * 
 * @author ferdinando.villa
 *
 */
public interface IScheduler {

	enum Type {
		REAL_TIME, MOCK_TIME
	}

	enum Synchronicity {
		/**
		 * Fully asynchronous tasks, started on time and left to complete.
		 */
		ASYNCHRONOUS,

		/**
		 * Fully synchronous tasks, only one will run at a time.
		 */
		SYNCHRONOUS,

		/**
		 * Tasks are started without waiting for others to finish, but no task with
		 * start = t will be started until all tasks with end <= t have finished.
		 */
		TIME_SYNCHRONOUS
	}

//	/**
//	 * Merge in an observation indicating another with the same view of time that
//	 * must be notified before it.
//	 * 
//	 * @param temporalObservation
//	 * @param requiredAntecedents
//	 *            must have been merged in previously
//	 * @throws IllegalArgumentException
//	 *             if requiredAntecedent has not been merged before
//	 */
//	void merge(T temporalObject, T... requiredAntecedents);
//
//	/**
//	 * Start the scheduler, passing the function to handle each tick for each
//	 * observation and the time of expiration of the tick. Exits immediately while
//	 * the scheduler runs.
//	 * 
//	 * @param tickHandler
//	 *            the function called with the object and the current time at each
//	 *            matching tick.
//	 * @param timingErrorHandler
//	 *            the function called if the tickHandler is called when the previous
//	 *            time step hasn't finished computing. This can only happen in real
//	 *            time (the scheduler will wait in mock time).
//	 */
//	void start(BiConsumer<T, Long> tickHandler, BiConsumer<T, Long> timingErrorHandler);

	/**
	 * Start scheduling.
	 */
	void start();

	/**
	 * Stop the scheduler.
	 */
	void stop();

	/**
	 * Get the synchronicity mode. The default mode should be SYNCHRONOUS or TIME_SYNCHRONOUS.
	 * 
	 * @return
	 */
	public Synchronicity getSynchronicity();

	/**
	 * Start, run to completion (if possible) and return.
	 */
	void run();
}
