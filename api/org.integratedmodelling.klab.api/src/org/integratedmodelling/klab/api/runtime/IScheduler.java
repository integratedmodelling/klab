package org.integratedmodelling.klab.api.runtime;

import java.util.function.BiConsumer;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IObservation;

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

	/**
	 * Merge in an observation that will be notified of ticks at its own resolution.
	 * 
	 * @param time
	 */
	void merge(IObservation temporalObservation);

	/**
	 * Merge in an observation indicating another with the same view of time that
	 * must be notified before it.
	 * 
	 * @param temporalObservation
	 * @param requiredAntecedent
	 *            must have been merged in previously
	 * @throws IllegalArgumentException
	 *             if requiredAntecedent has not been merged before
	 */
	void merge(IObservation temporalObservation, IObservation requiredAntecedent);

	/**
	 * Start the scheduler, passing the function to handle each tick for each
	 * observation. Exits immediately while the scheduler runs.
	 * 
	 * @param tickHandler
	 */
	void start(BiConsumer<IObservation, ILocator> tickHandler);

	/**
	 * Stop the scheduler.
	 */
	void stop();
}
