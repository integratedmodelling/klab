package org.integratedmodelling.klab.engine.debugger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.utils.Triple;
import org.joda.time.Period;

public enum Debug {

	INSTANCE;

	TextIO textIO = null;
	TextTerminal<?> terminal = null;
	Map<Long, Triple<Long, String, Consumer<Period>>> timers = Collections.synchronizedMap(new HashMap<>());
	AtomicLong timerIDs = new AtomicLong(0L);

	public boolean isDebug() {
		return Configuration.INSTANCE.getProperty("debug", null) != null;
	}

	public void summarize(Object object) {
		if (object instanceof RescalingState) {
			((RescalingState) object).summarize();
		} else if (object instanceof State) {

		}
	}

	public void say(String string) {
		if (textIO == null) {
			textIO = TextIoFactory.getTextIO();
			terminal = textIO.getTextTerminal();
		}
		terminal.println(string);
	}

	public String ask(String prompt) {
		if (textIO == null) {
			textIO = TextIoFactory.getTextIO();
		}
		return textIO.newStringInputReader().read(prompt);
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

}
