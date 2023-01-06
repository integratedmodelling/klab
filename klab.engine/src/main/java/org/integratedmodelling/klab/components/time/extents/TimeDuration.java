package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.utils.Range;
import org.joda.time.Duration;
import org.springframework.util.StringUtils;

public class TimeDuration implements ITimeDuration {

	// may be anchored to a start point or not
	private ITimeInstant start = null;
	private Duration period = null;
	private Resolution.Type resolution = null;
	boolean regular = true;

	private TimeDuration(Duration period, ITimeInstant start) {
		this.period = period;
		this.start = start;
	}

	private TimeDuration(Duration period, ITimeInstant start, Resolution.Type resolution) {
		this.period = period;
		this.start = start;
		this.resolution = resolution;
	}

	private TimeDuration() {
	}

	@Override
	public Resolution.Type getResolution() {

		if (this.resolution == null) {

			// order of magnitude
			Range order = Range.create(1, 9.999, false);

			if (order.contains(getMilliseconds() / Resolution.Type.MILLENNIUM.getMilliseconds())) {
				this.resolution = Resolution.Type.MILLENNIUM;
			} else if (order.contains(getMilliseconds() / Resolution.Type.CENTURY.getMilliseconds())) {
				this.resolution = Resolution.Type.CENTURY;
			} else if (order.contains(getMilliseconds() / Resolution.Type.DECADE.getMilliseconds())) {
				this.resolution = Resolution.Type.DECADE;
			} else if (order.contains(getMilliseconds() / Resolution.Type.YEAR.getMilliseconds())) {
				this.resolution = Resolution.Type.YEAR;
			} else if (order.contains(getMilliseconds() / Resolution.Type.MONTH.getMilliseconds())) {
				this.resolution = Resolution.Type.MONTH;
			} else if (order.contains(getMilliseconds() / Resolution.Type.WEEK.getMilliseconds())) {
				this.resolution = Resolution.Type.WEEK;
			} else if (order.contains(getMilliseconds() / Resolution.Type.DAY.getMilliseconds())) {
				this.resolution = Resolution.Type.DAY;
			} else if (order.contains(getMilliseconds() / Resolution.Type.HOUR.getMilliseconds())) {
				this.resolution = Resolution.Type.HOUR;
			} else if (order.contains(getMilliseconds() / Resolution.Type.MINUTE.getMilliseconds())) {
				this.resolution = Resolution.Type.MINUTE;
			} else if (order.contains(getMilliseconds() / Resolution.Type.SECOND.getMilliseconds())) {
				this.resolution = Resolution.Type.SECOND;
			} else {
				this.resolution = Resolution.Type.MILLISECOND;
			}
		}
		return this.resolution;
	}

	public static TimeDuration create(ITimeInstant start, ITimeInstant end, boolean anchor) {
		Duration period = new Duration(end.getMilliseconds() - start.getMilliseconds());
		return new TimeDuration(period, anchor ? start : null);
	}

	public static TimeDuration create(ITimeInstant start, ITimeInstant end, Resolution.Type resolution) {
		Duration period = new Duration(end.getMilliseconds() - start.getMilliseconds());
		return new TimeDuration(period, start, resolution);
	}

	public static TimeDuration create(long start, long end, Resolution.Type resolution) {
		Duration period = new Duration(end - start);
		return new TimeDuration(period, TimeInstant.create(start), resolution);
	}

	@Override
	public int compareTo(ITimeDuration o) {
		return Long.compare(getMilliseconds(), o.getMilliseconds());
	}

	@Override
	public long getMilliseconds() {
		if (start == null) {
			return period.getMillis();
		}
		return ((TimeInstant) start).asDate().plus(this.period).getMillis() - start.getMilliseconds();
	}

	@Override
	public IPair<ITimeInstant, ITimeInstant> localize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		return getMilliseconds() == 0;
	}

	public Duration asDuration() {
		return period;
	}

	public String getDescription() {
		return StringUtils.capitalize(resolution.name().toLowerCase()) + " " + period.toString();
	}

	@Override
	public String toString() {
		return period.toString();
	}

	@Override
	public ITimeDuration anchor(ITimeInstant instant) {
		return new TimeDuration(period, instant);
	}

	@Override
	public boolean isAnchored() {
		return start != null;
	}

	@Override
	public ITimeInstant getStart() {
		return start;
	}

	public static ITimeDuration create(long milliseconds, Type type) {
		TimeDuration ret = new TimeDuration();
		ret.resolution = type;
		ret.period = new Duration(milliseconds);
		return ret;
	}

	@Override
	public boolean isRegular() {
		return resolution.isRegular();
	}

	@Override
	public long getMaxMilliseconds() {
		return regular ? getMilliseconds() : 0;
	}

	@Override
	public long getCommonDivisorMilliseconds() {
		// TODO Auto-generated method stub
		return regular ? getMilliseconds() : 0;
	}

	@Override
	public String getSpecification() {
		// TODO Auto-generated method stub
		return "todo";
	}

}
