package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.joda.time.Period;

public class TimeDuration implements ITimeDuration {

	private Period period;
	
	@Override
	public int compareTo(ITimeDuration o) {
		return Integer.compare(period.getMillis(), ((TimeDuration)o).period.getMillis());
	}

	@Override
	public long getMilliseconds() {
		return period.getMillis();
	}

	@Override
	public IPair<ITimeInstant, ITimeInstant> localize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return period.getMillis() == 0;
	}
	
	public Period asPeriod() {
		return period;
	}
	
	@Override
	public String toString() {
		return period.toString();
	}
	
}
