package org.integratedmodelling.klab.engine.runtime;

import java.util.Deque;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.Pair;

/**
 * An observation task queue for use in applications and sessions. Uses the
 * session's state to find or create the observation context and any options,
 * such as roles or scenarios.
 * 
 * @author Ferd
 *
 */
public class ObservationQueue {

	ISession session;
	TimerTask timer;
	Deque<Pair<String, Consumer<IArtifact>>> tasks = new LinkedList<>();

	/**
	 * Starts immediately. Call stop() to stop.
	 * 
	 * @param session
	 */
	public ObservationQueue(ISession session) {

		this.session = session;
		this.timer = new TimerTask() {

			@Override
			public void run() {
				Pair<String, Consumer<IArtifact>> job = tasks.pollLast();
				if (job != null) {
					
					Future<ISubject> task = session.observe(job.getFirst() /* TODO scenarios from session state */);
				}
			}
		};

	}

	public void submit(String urn, Consumer<IArtifact> listener) {
	}

	/**
	 * Stop the timer (which cannot be restarted). Returns whether there were more
	 * observations pending.
	 * 
	 * @return
	 */
	public boolean stop() {
		return timer.cancel();
	}
}
