package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.joda.time.DateTime;

/**
 * TODO switch to Java8 dates
 * 
 * @author Ferd
 *
 */
public class TimeInstant implements ITimeInstant {
	
	DateTime time;

	public TimeInstant(int year) {
		time = new DateTime(year, 1, 1, 0, 0);
	}

	public TimeInstant(long milliseconds) {
		time = new DateTime(milliseconds);
	}

	public TimeInstant(DateTime time) {
		this.time = time;
	}
	
	@Override
	public int compareTo(ITimeInstant arg0) {
		return time.compareTo(((TimeInstant)arg0).time);
	}

	@Override
	public long getMilliseconds() {
		return time.getMillis();
	}

	public DateTime asDate() {
		return time;
	}
	
	public String toString() {
		return time.toString();
	}
	
}
