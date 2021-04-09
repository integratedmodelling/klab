package org.integratedmodelling.klab.engine.debugger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.utils.Triple;
import org.joda.time.Period;

public enum Debug {

	INSTANCE;

	Map<Long, Triple<Long, String, Consumer<Period>>> timers = Collections.synchronizedMap(new HashMap<>());
	AtomicLong timerIDs = new AtomicLong(0L);
	private volatile Map<String, Debugger> debuggers = new HashMap<>();
	
	public boolean isDebug() {
		return Configuration.INSTANCE.getProperty("debug", null) != null;
	}
	
	public boolean isDebugging() {
		return debuggers.size() > 0;
	}

	public void summarize(Object object) {
		if (object instanceof RescalingState) {
			((RescalingState) object).summarize();
		} else if (object instanceof State) {

		}
	}

	/**
	 * Start a timer. The description is saved and printed (sysout for now). When
	 * endTimer is called with the handle returned from this, the callback (if not
	 * null) will be called passing the period expired.
	 * 
	 * @param description
	 * @param callback
	 * @return
	 */
	public long startTimer(String description, Consumer<Period> callback) {
		long ret = timerIDs.incrementAndGet();
		this.timers.put(ret, new Triple<>(System.currentTimeMillis(), description, callback));
		System.out.println("TIMER " + description + " started");
		return ret;
	}

	/**
	 * End the timer. If a callback was passed, call it.
	 * 
	 * @param handle
	 */
	public void endTimer(long handle) {
		Period period = null;
		Triple<Long, String, Consumer<Period>> data = timers.remove(handle);
		if (data != null) {
			period = Period.millis((int) (System.currentTimeMillis() - data.getFirst()));
			System.out.println("TIMER " + data.getSecond() + " ended after " + period);
			if (data.getThird() != null) {
				data.getThird().accept(period);
			}
		}
	}
	
	public void locate(ILocator locator, IObservation observation, Object value) {
		for (Debugger debugger : debuggers.values()) {
			debugger.focus(locator, observation);
		}
	}

//	public void newDebugger(ISession session) {
//		Debugger.create(session, debuggers);
//	}
}
