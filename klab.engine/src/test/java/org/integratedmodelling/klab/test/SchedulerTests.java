package org.integratedmodelling.klab.test;

import java.util.concurrent.TimeUnit;

import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelMockTimer;
import org.integratedmodelling.klab.engine.runtime.scheduling.HashedWheelTimer;
import org.junit.Test;

public class SchedulerTests {

	@Test
	public void testMocktime() {
		
		HashedWheelTimer timer = new HashedWheelMockTimer(20000);
		timer.scheduleAtFixedRate((t)-> System.out.println("at 3 every 1: it's " + t + " seconds"), 3, 1, TimeUnit.SECONDS);
		timer.scheduleAtFixedRate((t)-> System.out.println("at 1 every 10: it's " + t), 1, 10, TimeUnit.SECONDS);
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
	
	@Test
	public void testRealtime() {
		
		HashedWheelTimer timer = new HashedWheelTimer();
		timer.scheduleAtFixedRate((t)-> System.out.println("at 3 every 1: it's " + t), 3, 1, TimeUnit.SECONDS);
		timer.scheduleAtFixedRate((t)-> System.out.println("at 1 every 10: it's " + t), 1, 10, TimeUnit.SECONDS);
		
		timer.startUntil(40000000);
		
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
