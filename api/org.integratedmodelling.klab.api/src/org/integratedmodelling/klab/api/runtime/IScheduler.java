package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

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
		 * Fully asynchronous tasks, started on time and left alone to complete in their
		 * own time while the scheduler moves on.
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

	/**
	 * Get the synchronicity mode. The default mode should be SYNCHRONOUS or
	 * TIME_SYNCHRONOUS.
	 * 
	 * @return
	 */
	public Synchronicity getSynchronicity();

	/**
	 * Start, run to completion (if possible) and return.
	 */
	void run(IMonitor monitor);

	/**
	 * Return true if nothing has been scheduled.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * True if the scheduler has run to the end and an end time is specified, or was
	 * interrupted.
	 * 
	 * @return
	 */
	boolean isFinished();
}
