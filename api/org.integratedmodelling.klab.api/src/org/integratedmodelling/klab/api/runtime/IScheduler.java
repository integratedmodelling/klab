package org.integratedmodelling.klab.api.runtime;

import java.util.function.BiConsumer;

/**
 * An observation-specific scheduler that can run temporal transitions over a
 * set of differently scaled observations in mock time or in real time, ensuring
 * dependency synchronization when necessary.
 * 
 * @author ferdinando.villa
 *
 */
public interface IScheduler<T> {

	enum Type {
		REAL_TIME, MOCK_TIME
	}

	/**
	 * Merge in an observation indicating another with the same view of time that
	 * must be notified before it.
	 * 
	 * @param temporalObservation
	 * @param requiredAntecedents
	 *            must have been merged in previously
	 * @throws IllegalArgumentException
	 *             if requiredAntecedent has not been merged before
	 */
	void merge(T temporalObject, T... requiredAntecedents);

	/**
	 * Start the scheduler, passing the function to handle each tick for each
	 * observation and the time of expiration of the tick. Exits immediately while
	 * the scheduler runs.
	 * 
	 * @param tickHandler
	 *            the function called with the object and the current time at each
	 *            matching tick.
	 * @param timingErrorHandler
	 *            the function called if the tickHandler is called when the previous
	 *            time step hasn't finished computing. This can only happen in real
	 *            time (the scheduler will wait in mock time).
	 */
	void start(BiConsumer<T, Long> tickHandler, BiConsumer<T, Long> timingErrorHandler);

	/**
	 * Stop the scheduler.
	 */
	void stop();

	/**
	 * Current absolute time. May have to switch to bigint for nanosecond resolution.
	 * 
	 * @return
	 */
    long getTime();
}
