package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.services.ITimeService;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Triple;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

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
        switch(resolution) {
        case CENTURY:
            begin = new DateTime(now.getYear() - (now.getYear() % 100), 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
            end = begin.plus(Years.years(100));
            break;
        case DAY:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0, 0, DateTimeZone.UTC);
            end = begin.plus(Days.ONE);
            break;
        case DECADE:
            begin = new DateTime(now.getYear() - (now.getYear() % 10), 1, 1, 0, 0, 0, 0);
            end = begin.plus(Years.years(10));
            break;
        case HOUR:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(), 0, 0, 0,
                    DateTimeZone.UTC);
            end = begin.plus(Hours.ONE);
            break;
        case MILLENNIUM:
            begin = new DateTime(now.getYear() - (now.getYear() % 1000), 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
            end = begin.plus(Years.years(1000));
            break;
        case MILLISECOND:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(),
                    now.getMinuteOfHour(), now.getSecondOfMinute(), 0, DateTimeZone.UTC);
            end = begin.plus(1);
            break;
        case MINUTE:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(),
                    now.getMinuteOfHour(), 0, 0, DateTimeZone.UTC);
            end = begin.plus(Minutes.ONE);
            break;
        case MONTH:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), 1, 0, 0, 0, 0, DateTimeZone.UTC);
            end = begin.plus(Months.ONE);
            break;
        case SECOND:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(),
                    now.getMinuteOfHour(), now.getSecondOfMinute(), 0, DateTimeZone.UTC);
            end = begin.plus(Seconds.ONE);
            break;
        case WEEK:
            // TODO if we really want it.
            break;
        case YEAR:
            begin = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0, 0, DateTimeZone.UTC);
            end = begin.plus(Years.ONE);
            break;
        default:
            break;

        }

        if (begin == null) {
            throw new KlabUnimplementedException("generic extent of type " + resolution + " are not supported at the moment");
        }

        return org.integratedmodelling.klab.components.time.extents.Time.create(Type.LOGICAL, resolution, 1,
                new TimeInstant(begin), new TimeInstant(end), null);
    }

    public static enum Frequency {
        HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY
    }

    public boolean isTimePattern(String string) {
        try {
            DateTimeFormat.forPattern(string);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Return true if the passed day/year is a time point at the specified frequency. Use the end of
     * the month and Sunday for week.
     * 
     * @param day
     * @param year
     * @param frequency
     * @return true if passed day/year clicks at given frequency
     */
    public static boolean isTimePoint(int day, int year, Frequency frequency) {
        // TODO finish
        switch(frequency) {
        case DAILY:
        case HOURLY:
            return true;
        case MONTHLY:
            // TODO day is last of month
            return false;
        case WEEKLY:
            // TODO day is sunday
            return false;
        case YEARLY:
            return day == daysInYear(year);
        }
        return false;
    }

    /**
     * Return all the years overlapping the period between the two times passed.
     * 
     * @param start
     * @param end
     * @return all years between the two dates
     */
    public static int[] yearsBetween(long start, long end) {

        DateTime ds = new DateTime(start, DateTimeZone.UTC);
        DateTime de = new DateTime(end, DateTimeZone.UTC);

        int ny = de.getYear() - ds.getYear() + 1;
        int[] ret = new int[ny];

        int i = 0;
        for (int y = ds.getYear(); y <= de.getYear(); y++) {
            ret[i++] = y;
        }
        return ret;
    }

    public static DateTime dateAt(int year, int dayInYear) {
        DateTime ret = new DateTime(year, 1, 1, 0, 0, DateTimeZone.UTC);
        return ret.plusDays(dayInYear);
    }

    /**
     * Return an iterable of the days (indexed within the year starting a 0) that overlap the passed
     * interval in the given year.
     * 
     * @param start
     * @param end
     * @param year
     * @return all days between dates
     */
    public static Iterable<Integer> daysBetween(long start, long end, int year) {

        List<Integer> ret = new ArrayList<>(366);
        DateTime ds = new DateTime(start);
        ds = new DateTime(ds.getYear(), ds.getMonthOfYear(), ds.getDayOfMonth(), 0, 0, DateTimeZone.UTC);
        DateTime de = new DateTime(end);
        de = new DateTime(de.getYear(), de.getMonthOfYear(), de.getDayOfMonth(), 23, 59, DateTimeZone.UTC);

        for (DateTime d = ds; d.compareTo(de) <= 0; d = d.plusDays(1)) {
            if (d.getYear() > year) {
                break;
            }
            if (d.getYear() == year) {
                ret.add(d.getDayOfYear() - 1);
            }
        }

        return ret;
    }

    public static int daysInYear(int year) {
        LocalDate ld = new LocalDate(year, 1, 1);
        return Days.daysBetween(ld, ld.plusYears(1)).getDays();
    }

    static final int FEB29 = 60;
    static final public long MS_IN_A_DAY = 86400000l;

    /**
     * Adjust the passed array describing a daily value for olderYear to describe a value for
     * oldYear, duplicating or removing values as required.
     * 
     * @param data
     * @param year the year we adapt to
     * @param olderYear the year represented in the data
     * @return adjusted array
     */
    public static double[] adjustLeapDays(double[] data, int year, int olderYear) {

        double[] ret = data;
        if (data != null) {

            int cdays = daysInYear(year);
            int odays = daysInYear(olderYear);

            if (odays > cdays) {
                ret = new double[cdays];
                // just remove the day
                for (int i = 0; i < cdays; i++) {
                    ret[i] = i < FEB29 ? data[i] : data[i + 1];
                }
            } else if (odays < cdays) {
                // add a day
                ret = new double[cdays];
                for (int i = 0; i < cdays; i++) {
                    ret[i] = i < FEB29 ? data[i] : data[i - 1];
                }
            }

        }
        return ret;
    }

    /**
     * Given the string specification of something that encodes time, return the inferred begin, end
     * and resolution of the period it stands for, or null if not parseable.
     * 
     * @param specification
     * @return
     */
    public Triple<ITimeInstant, ITimeInstant, ITime.Resolution> analyzeTimepoint(String specification) {

        ITimeInstant start = null;
        ITimeInstant end = null;
        ITime.Resolution resolution = null;

        // assume milliseconds if > 10000
        if (NumberUtils.encodesLong(specification) && Long.parseLong(specification) > 10000) {
            start = TimeInstant.create(Long.parseLong(specification));
            end = TimeInstant.create(start.getMilliseconds() + 1);
            resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(1,
                    ITime.Resolution.Type.MILLISECOND);
        } else if (NumberUtils.encodesInteger(specification)) {
            start = TimeInstant.create(Integer.parseInt(specification));
            resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(1,
                    ITime.Resolution.Type.YEAR);
            end = start.plus(1, resolution);
        } else {
            // nah for now
        }

        if (start != null && end != null && resolution != null) {
            return new Triple<>(start, end, resolution);
        }

        return null;
    }

    public static int getYear(ITimeInstant time) {
        DateTime ds = new DateTime(time.getMilliseconds(), DateTimeZone.UTC);
        return ds.getYear();
    }

    public static int getYear(long start) {
        DateTime ds = new DateTime(start, DateTimeZone.UTC);
        return ds.getYear();
    }

}
