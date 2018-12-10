package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.joda.time.DateTime;

public class TimeInstant implements ITimeInstant {
	
	DateTime time;

	@Override
	public int compareTo(ITimeInstant arg0) {
		return time.compareTo(((TimeInstant)arg0).time);
	}

	@Override
	public long getMillis() {
		return time.getMillis();
	}

	public DateTime asDate() {
		return time;
	}
	
}
