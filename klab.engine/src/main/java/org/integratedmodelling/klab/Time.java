package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Type;
import org.integratedmodelling.klab.api.services.ITimeService;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;

public enum Time implements ITimeService {

	INSTANCE;

	private Time() {
		// all dates in UTC
		DateTimeZone.setDefault(DateTimeZone.UTC);
	}

	@Override
	public ITime getGenericCurrentExtent(Resolution.Type resolution) {
		DateTime now = new DateTime();
		DateTime begin = null;
		DateTime end = null;
		switch (resolution) {
		case CENTURY:
			begin = new DateTime(now.getYear() - (now.getYear() % 100), 1, 1, 0, 0, 0, 0);
			end = begin.plus(Years.years(100));
			break;
		case DAY:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0, 0);
			end = begin.plus(Days.ONE);
			break;
		case DECADE:
			begin = new DateTime(now.getYear() - (now.getYear() % 10), 1, 1, 0, 0, 0, 0);
			end = begin.plus(Years.years(10));
			break;
		case HOUR:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(), 0, 0, 0);
			end = begin.plus(Hours.ONE);
			break;
		case MILLENNIUM:
			begin = new DateTime(now.getYear() - (now.getYear() % 1000), 1, 1, 0, 0, 0, 0);
			end = begin.plus(Years.years(1000));
			break;
		case MILLISECOND:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(),
					now.getMinuteOfHour(), now.getSecondOfMinute(), 0);
			end = begin.plus(1);
			break;
		case MINUTE:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(),
					now.getMinuteOfHour(), 0, 0);
			end = begin.plus(Minutes.ONE);
			break;
		case MONTH:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), 1, 0, 0, 0, 0);
			end = begin.plus(Months.ONE);
			break;
		case SECOND:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(),
					now.getMinuteOfHour(), now.getSecondOfMinute(), 0);
			end = begin.plus(Seconds.ONE);
			break;
		case WEEK:
			// TODO if we really want it.
			break;
		case YEAR:
			begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0, 0);
			end = begin.plus(Years.ONE);
			break;
		default:
			break;

		}

		if (begin == null) {
			throw new KlabUnimplementedException(
					"generic extent of type " + resolution + " are not supported at the moment");
		}

		return org.integratedmodelling.klab.components.time.extents.Time.create(Type.GENERIC, resolution, 1,
				new TimeInstant(begin), new TimeInstant(end), null);
	}

}
