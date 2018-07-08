package org.integratedmodelling.klab.engine.runtime.scheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A timer that just pretends to wait and is configured with a mock start time
 * in milliseconds.
 * 
 * @author ferdinando.villa
 *
 */
public class HashedWheelMockTimer extends HashedWheelTimer {

	long startTime;

	static class MockWait implements WaitStrategy {
		@Override
		public void waitUntil(long deadlineNanoseconds) throws InterruptedException {
			// just return
		}
	}

	public HashedWheelMockTimer(long startTimeMs) {
		super(DEFAULT_TIMER_NAME + "-mocktime", DEFAULT_RESOLUTION, DEFAULT_WHEEL_SIZE, new MockWait(),
				Executors.newFixedThreadPool(1));
		this.startTime = startTimeMs;
	}

	public HashedWheelMockTimer(long startTimeMs, long resolution) {
		super(DEFAULT_TIMER_NAME + "-mocktime", resolution, DEFAULT_WHEEL_SIZE, new MockWait(),
				Executors.newFixedThreadPool(1));
		this.startTime = startTimeMs;
	}

	public HashedWheelMockTimer(long startTimeMs, long res, int wheelSize, WaitStrategy waitStrategy) {
		super(DEFAULT_TIMER_NAME + "-mocktime", res, wheelSize, waitStrategy, Executors.newFixedThreadPool(1));
		this.startTime = startTimeMs;
	}

	public HashedWheelMockTimer(long startTimeMs, String name, long res, int wheelSize, WaitStrategy strategy,
			ExecutorService exec) {
		super(name, res, wheelSize, strategy, exec);
		this.startTime = startTimeMs;
	}

	@Override
	protected long getInitialTime() {
		return TimeUnit.MILLISECONDS.toNanos(startTime);
	}

}
