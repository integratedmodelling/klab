package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;

public interface ITimeService {

	/**
	 * Get a generic time extent tuned on the current time at the
	 * passed resolution.
	 * 
	 * @param resolution
	 * @return a generic time extent
	 */
	ITime getGenericCurrentExtent(ITime.Resolution.Type resolution);
	
}
