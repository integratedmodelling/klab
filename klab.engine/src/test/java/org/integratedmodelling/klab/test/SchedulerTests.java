package org.integratedmodelling.klab.test;

import java.util.concurrent.TimeUnit;

import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelMockTimer;
import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelTimer;
import org.junit.Test;

public class SchedulerTests {

	@Test
	public void testMocktime() {
		
		HashedWheelTimer timer = new HashedWheelMockTimer(20000);
		timer.scheduleAtFixedRate(()-> System.out.println("at 10 every 5"), 10, 5, TimeUnit.SECONDS);
		timer.scheduleAtFixedRate(()-> System.out.println("at 0 every 15"), 0, 15, TimeUnit.SECONDS);
		timer.startUntil(400000);
		
		while(true) {
			if (timer.isFinished()) {
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
