package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

/**
 * TODO switch to Java8 dates
 * 
 * @author Ferd
 *
 */
public class TimeInstant implements ITimeInstant {
	
	DateTime time;

	public static ITimeInstant create(int year, int month, int day) {
		return new TimeInstant(year, month, day);
	}

	public static ITimeInstant create(int year) {
		return new TimeInstant(year);
	}

	public static ITimeInstant create(DateTime time) {
		return new TimeInstant(time);
	}

	public static ITimeInstant create(long milliseconds) {
		return new TimeInstant(milliseconds);
	}

	public static ITimeInstant create() {
		return new TimeInstant();
	}
	
	public TimeInstant(int year) {
		time = new DateTime(year, 1, 1, 0, 0, DateTimeZone.UTC);
	}
	
	public TimeInstant(int year, int month, int day) {
		time = new DateTime(year, month, day, 0, 0, DateTimeZone.UTC);
	}

	public TimeInstant(long milliseconds) {
		time = new DateTime(milliseconds, DateTimeZone.UTC);
	}

	public TimeInstant(DateTime time) {
		this.time = time;
	}
	
	public TimeInstant() {
		this.time = DateTime.now(DateTimeZone.UTC);
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
		return DateTimeFormat.shortDateTime().print(time);
	}

	@Override
	public boolean isAfter(ITimeInstant t) {
		return this.time.isAfter(((TimeInstant)t).time);
	}

	@Override
	public int getDayOfYear() {
		return this.time.getDayOfYear() - 1;
	}
	
	@Override
	public boolean isBefore(ITimeInstant t) {
		return this.time.isBefore(((TimeInstant)t).time);
	}

	@Override
	public String getSpecification() {
		// TODO Auto-generated method stub
		return "todo";
	}

    @Override
    public int getYear() {
        return this.time.getYear();
    }

}
