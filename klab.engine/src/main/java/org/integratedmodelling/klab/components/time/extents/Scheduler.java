package org.integratedmodelling.klab.components.time.extents;

import java.util.function.BiConsumer;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IScheduler;

import com.ifesdjeen.timer.HashedWheelTimer;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the
 * latter so we need to roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public class Scheduler implements IScheduler {

	HashedWheelTimer timer;
	Type type;


	public Scheduler(Type type) {
		this.type = type;
	}

	@Override
	public void merge(IObservation temporalObservation) {

	}

	@Override
	public void merge(IObservation temporalObservation, IObservation requiredAntecedent) {

	}


	@Override
	public void start(BiConsumer<IObservation, ILocator> tickHandler) {

	}
	
	@Override
	public void stop() {
		
	}

}
