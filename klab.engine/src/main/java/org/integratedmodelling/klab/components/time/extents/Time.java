package org.integratedmodelling.klab.components.time.extents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimDate;
import org.integratedmodelling.kim.model.KimQuantity;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Encoding;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.components.localstorage.impl.TimesliceLocator;
import org.integratedmodelling.klab.components.time.extents.mediators.TimeIdentity;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.YearMonth;

public class Time extends Extent implements ITime {

	ITime.Type extentType;
	ITimeInstant start;
	ITimeInstant end;
	ITimeDuration step;
	boolean realtime = false;
	Resolution resolution;
	Resolution coverageResolution;
	long coverageStart;
	long coverageEnd;
	long multiplicity = 1;
	boolean partial = false;
	boolean regular = true;
	// flag that irregular intervals must be computed to obtain size
	boolean irregintervals = false;
	long __id = nextId.incrementAndGet();
	Time parentExtent = null;
	int timeSlice = -1;

	/*
	 * if this is not null, time has recorded events besides its original
	 * definition, and the timeline takes over
	 */
	TemporalExtension extension = null;

	/**
	 * Observations distributed over irregular time extents can't use offsets to
	 * locate a particular state, so we add a "focus" time instant to enable them to
	 * focus on a given timepoint.
	 */
	ITimeInstant focus;

	private static AtomicLong nextId = new AtomicLong(Long.MIN_VALUE);

	private static class ResolutionImpl implements Resolution {

		private Type type;
		private double multiplier;

		public ResolutionImpl(Type type, double multiplier) {
			this.type = type;
			this.multiplier = multiplier;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public double getMultiplier() {
			return multiplier;
		}

		@Override
		public long getSpan() {
			return (long) (type.getMilliseconds() * multiplier);
		}

		public Resolution copy() {
			return new ResolutionImpl(type, multiplier);
		}

		@Override
		public double getMultiplier(ITimeInstant start, ITimeInstant end) {
			if (start == null || end == null) {
				return multiplier;
			}
			double span = end.getMilliseconds() - start.getMilliseconds();
			return span / type.getMilliseconds();
		}

		public void setMultiplier(double multiplier) {
			this.multiplier = multiplier;
		}

		public String toString() {
			return multiplier + " " + type;
		}

		@Override
		public int hashCode() {
			return Objects.hash(multiplier, type);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResolutionImpl other = (ResolutionImpl) obj;
			return Double.doubleToLongBits(multiplier) == Double.doubleToLongBits(other.multiplier)
					&& type == other.type;
		}

	}

	private Time() {
	}

	protected Time(ITimeInstant start, ITimeInstant end) {
		this.start = start;
		this.end = end;
		this.multiplicity = 1;
		this.resolution = resolution(start, end);
	}

	/**
	 * DOES NOT (intentionally) copy the extension.
	 * 
	 * @param time
	 */
	private Time(Time time) {
		this.baseDimension = time.baseDimension;
		this.end = time.end;
		this.extentType = time.extentType;
		this.multiplicity = time.multiplicity;
		this.realtime = time.realtime;
		this.resolution = time.resolution == null ? null : ((ResolutionImpl) time.resolution).copy();
		this.start = time.start;
		this.step = time.step;
		this.timeSlice = time.timeSlice;
	}

	private static Time initialization(Scale scale) {
		Time ret = new Time();
		ret.extentType = ITime.Type.INITIALIZATION;
		ret.start = new TimeInstant(0);
		ret.end = new TimeInstant(0);
		ret.multiplicity = 1;
		ret.locatedLinearOffset = 0;
		ret.locatedOffsets = new long[] { 0 };
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, 0.0);
		if (scale != null) {
			ret.setScaleId(scale.getScaleId());
		}
		return ret;
	}

	private static Time termination(Scale scale) {
		Time ret = new Time((Time) scale.getTime());
		ret.extentType = ITime.Type.TERMINATION;
		ret.start = new TimeInstant(0);
		ret.end = new TimeInstant(0);
		ret.multiplicity = 1;
		ret.locatedLinearOffset = 0;
		ret.locatedOffsets = new long[] { 0 };
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, 0.0);
		if (scale != null) {
			ret.setScaleId(scale.getScaleId());
		}
		return ret;
	}

	public static Time initialization(ITime time) {
		Time ret = new Time((Time) time);
		ret.extentType = ITime.Type.INITIALIZATION;
		ret.multiplicity = 1;
		ret.locatedOffsets = new long[] { 0 };
		ret.locatedLinearOffset = 0;
		ret.parentExtent = (Time) time;
		return ret;
	}

	public static Time termination(ITime time) {
		Time ret = new Time((Time) time);
		ret.extentType = ITime.Type.TERMINATION;
		ret.multiplicity = 1;
		ret.locatedOffsets = new long[] { 0 };
		ret.locatedLinearOffset = 0;
		ret.parentExtent = (Time) time;
		return ret;
	}

	public static Time partial() {
		Time ret = new Time();
		ret.extentType = ITime.Type.LOGICAL;
		ret.partial = true;
		return ret;
	}

	public static Time create(int year) {
		Time ret = new Time();
		ret.extentType = ITime.Type.PHYSICAL;
		ret.start = new TimeInstant(year);
		ret.end = new TimeInstant(new DateTime(year + 1, 1, 1, 0, 0, DateTimeZone.UTC));
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, 1.0);
		return ret;
	}

	public static Time create(int startYear, int endYear) {
		Time ret = new Time();
		ret.extentType = ITime.Type.PHYSICAL;
		ret.start = new TimeInstant(startYear);
		ret.end = new TimeInstant(endYear);
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, endYear - startYear);
		return ret;
	}

	public static Time create(long startMillis, long endMillis) {
		Time ret = new Time();
		ret.extentType = ITime.Type.PHYSICAL;
		ret.start = new TimeInstant(startMillis);
		ret.end = new TimeInstant(endMillis);
		ret.resolution = resolution(ret.start, ret.end);
		return ret;
	}

	public static Time create(ITime.Type type, Resolution.Type resolutionType, Double resolutionMultiplier,
			ITimeInstant start, ITimeInstant end, ITimeDuration period, Resolution.Type coverageUnit,
			Long coverageStart, Long coverageEnd) {

		Time ret = new Time();
		ret.extentType = type;
		ret.start = start;
		ret.end = end;
		if (resolutionType != null) {
			ret.resolution = new ResolutionImpl(resolutionType, resolutionMultiplier);
		}
		ret.step = period;
		if (ret.step != null) {
			if (type == ITime.Type.REAL && ret.end == null) {
				ret.multiplicity = Geometry.INFINITE_SIZE;
			} else if (start != null && end != null) {
				ret.multiplicity = (long) (ret.getCoveredExtent() / ret.step.getMilliseconds()) + 1;
			} else {
				ret.partial = true;
				ret.multiplicity = 0;
			}
		} else if (ret.extentType == ITime.Type.GRID) {
			ret.setupExtents();
		}

		if (coverageUnit != null) {
			ret.coverageResolution = new ResolutionImpl(coverageUnit, 1);
			ret.coverageStart = coverageStart;
			ret.coverageEnd = coverageEnd;
		}

		return ret;
	}

	private void setupExtents() {
		if (step == null) {
			if (resolution != null) {
				if (resolution.getType().isRegular()) {
					this.multiplicity = (long) ((end.getMilliseconds() - start.getMilliseconds())
							/ (resolution.getType().getMilliseconds() * resolution.getMultiplier())) + 1;
				} else {
					// compute on request
					this.irregintervals = true;
				}
			}
		}
	}

	public static Time create(ITime.Type type, Resolution.Type resolutionType, double resolutionMultiplier,
			ITimeInstant start, ITimeInstant end, ITimeDuration period) {
		return create(type, resolutionType, resolutionMultiplier, start, end, period, null, null, null);
	}

	protected void setTimeType(ITime.Type type) {
		this.extentType = type;
	}

	/**
	 * Return the timepoints for all events starting and ending at the passed
	 * resolution in the time period we represent. If ordinal is true, return
	 * 0-based indices based on the next higher resolution (e.g. month or week
	 * number within a year, even if our own resolution is a decade); otherwise pass
	 * the actual start timestamps in milliseconds. If we don't span any of those
	 * events, return an empty array.
	 * 
	 * @param resolution
	 * @param ordinal
	 * @return
	 */
	public long[] getEvents(Resolution.Type resolution, boolean ordinal) {
		return null;
	}

	public static Time create(IAnnotation timeAnnotation) {

		if (timeAnnotation.containsKey(IServiceCall.DEFAULT_PARAMETER_NAME)) {
			if (timeAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME) instanceof Integer) {
				timeAnnotation.put("year", timeAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME));
			}
		}

		return (Time) new org.integratedmodelling.klab.components.time.services.Time()
				.eval(new Expression.SimpleScope(Klab.INSTANCE.getRootMonitor()), timeAnnotation);
	}

	public static ITimeInstant instant(KimDate date) {
		DateTime dtime = new DateTime(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMin(),
				date.getSec(), date.getMs(), DateTimeZone.UTC);
		return new TimeInstant(dtime);
	}

	public static ITimeInstant instant(int year) {
		return instant(KimDate.asDate(year));
	}

	public static ITimeDuration duration(IQuantity spec) {
		Resolution res = new ResolutionImpl(Resolution.Type.parse(spec.getUnit()), spec.getValue().doubleValue());
		return TimeDuration.create((long) (res.getMultiplier() * res.getType().getMilliseconds()), res.getType());
	}

	public static ITimeDuration duration(Quantity spec) {
		Resolution res = new ResolutionImpl(Resolution.Type.parse(spec.getUnit().toString()),
				spec.getValue().doubleValue());
		return TimeDuration.create((long) (res.getMultiplier() * res.getType().getMilliseconds()), res.getType());
	}

	public static ITimeDuration duration(String string) {
		return duration(KimQuantity.parse(string));
	}

	public static ITimeDuration duration(Number number, Resolution.Type type) {
		return TimeDuration.create(number.longValue(), type);
	}

	public static Resolution resolution(IQuantity spec) {
		return new ResolutionImpl(Resolution.Type.parse(spec.getUnit()), spec.getValue().doubleValue());
	}

	public static Resolution resolution(Quantity spec) {
		return new ResolutionImpl(Resolution.Type.parse(spec.getUnit().toString()), spec.getValue().doubleValue());
	}

	public static Resolution resolution(ITimeInstant start, ITimeInstant end) {
		TimeDuration duration = TimeDuration.create(start, end, false);
		Resolution.Type res = duration.getResolution();
		return new ResolutionImpl(res, (double) duration.getMilliseconds() / (double) res.getMilliseconds());
	}

	public static Resolution resolution(double value, Resolution.Type type) {
		return new ResolutionImpl(type, value);
	}

	public static Resolution resolution(String string) {
		return resolution(KimQuantity.parse(string));
	}

	@Override
	public int getScaleRank() {
		return resolution.getType().getRank();
	}

	private Range getRange() {
		return Range.create(start, end);
	}

	@Override
	public ITime mergeContext(IExtent extent) throws KlabException {

		if (extent instanceof ITime) {

			ITime other = (ITime) extent;

			/*
			 * boundaries
			 */
			ITimeInstant astart = start == null ? other.getStart() : start;
			ITimeInstant ostart = other.getStart() == null ? start : other.getStart();
			ITimeInstant start = ITimeInstant.max(astart, ostart);

			ITimeInstant aend = end == null ? other.getEnd() : end;
			ITimeInstant oend = other.getEnd() == null ? end : other.getEnd();
			ITimeInstant end = ITimeInstant.min(aend, oend);

			/*
			 * type
			 */
			ITime.Type type = other.getTimeType() == ITime.Type.GRID ? ITime.Type.GRID : getTimeType();
			if (start == null || end == null) {
				type = ITime.Type.LOGICAL;
			}

			/*
			 * resolution
			 */
			Resolution resolution = other.getResolution();
			if (this.getTimeType() == ITime.Type.GRID && this.getResolution() != null) {
				resolution = this.getResolution();
			}

			// public static Time create(ITime.Type type, Resolution.Type resolutionType,
			// Double
			// resolutionMultiplier,
			// ITimeInstant start, ITimeInstant end, ITimeDuration period, Resolution.Type
			// coverageUnit,
			// Long coverageStart, Long coverageEnd) {

			return create(type, resolution == null ? null : resolution.getType(),
					resolution == null ? null : resolution.getMultiplier(), start, end, null, null, null, null);
			//
			// Time ret = copy();
			//
			// // schiaff in the unknowns
			// if (start == null && other.getStart() != null) {
			// ret.start = other.getStart();
			// }
			// if (end == null && other.getEnd() != null) {
			// ret.end = other.getEnd();
			// }
			//
			// // shouldn't change representation, step and the like
			// if (this.resolution == null) {
			// ret.resolution = other.getResolution();
			// }
			//
			// ret.multiplicity = 1;
			// if (ret.start != null && ret.end != null && ret.step != null) {
			// ret.multiplicity = (ret.end.getMilliseconds() - ret.start.getMilliseconds())
			// / ret.step.getMilliseconds() + 1;
			// } else if (ret.getTimeType() == ITime.Type.GRID) {
			// ret.setupExtents();
			// }
			//
			// return ret;

		}
		return this;
	}

	@Override
	public double getCoveredExtent() {
		return end.getMilliseconds() - start.getMilliseconds();
	}

	@Override
	public IScaleMediator getMediator(IExtent extent) {
		// TODO Auto-generated method stub
		return new TimeIdentity();
	}

	@Override
	public ITime merge(ITopologicallyComparable<?> other, LogicalConnector how, MergingOption... options) {
		if (how == LogicalConnector.UNION) {
			ITimeInstant s = TimeInstant
					.create(Long.min(this.start.getMilliseconds(), ((ITime) other).getStart().getMilliseconds()));
			ITimeInstant e = TimeInstant
					.create(Long.max(this.end.getMilliseconds(), ((ITime) other).getEnd().getMilliseconds()));
			return create(s, e, this.resolution);
		}
		return copy();
	}

	// @SuppressWarnings("unchecked")
	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		// if (Long.class.isAssignableFrom(cls)) {
		// return (T) Long.valueOf(getLocatedOffset() < 0 ? 0 : getLocatedOffset());
		// } else if (Long[].class.isAssignableFrom(cls)) {
		// return (T) new Long[] { getLocatedOffset() < 0 ? 0l : getLocatedOffset() };
		// } // TODO
		return null;
	}

	@Override
	public long size() {

		// if (this.extension != null) {
		// return this.extension.size() + 1;
		// }

		if (end == null || start == null) {
			return this.getTimeType() == ITime.Type.GRID ? Geometry.INFINITE_SIZE : 1;
		}
		if (irregintervals) {
			// do it the hard way. One day we'll improve.
			multiplicity = 0;
			for (long i = 0;; i++) {
				Time ext = makeExtent(i);
				if (!ext.is(ITime.Type.INITIALIZATION) && ext.end.getMilliseconds() > this.end.getMilliseconds()) {
					break;
				}
				multiplicity++;
			}
			irregintervals = false;
		}
		return multiplicity;
	}

	@Override
	public boolean contains(IExtent o) throws KlabException {
		return o instanceof Time ? getRange().contains(((Time) o).getRange()) : false;
	}

	@Override
	public boolean overlaps(IExtent o) throws KlabException {
		return o instanceof Time ? ((Time) o).intersects(this) : false;
	}

	@Override
	public boolean intersects(IExtent o) throws KlabException {
		return o instanceof Time ? getRange().intersection(((Time) o).getRange()) == null : false;
	}

	@Override
	public boolean isRegular() {
		return step != null;
	}

	@Override
	public int getDimensionality() {
		return step == null ? 0 : 1;
	}

	@Override
	public long[] shape() {
		return new long[] { size() };
	}

	@Override
	public IParameters<String> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time collapse() {
		return isConsistent()
				? create(this.extentType == ITime.Type.LOGICAL ? ITime.Type.LOGICAL : ITime.Type.PHYSICAL,
						(this.resolution == null ? null : this.resolution.getType()),
						(this.resolution == null ? null : this.resolution.getMultiplier(start, end)), start, end, null,
						(this.coverageResolution == null ? null : this.coverageResolution.getType()),
						this.coverageStart, this.coverageEnd)
				: this;
	}

	@Override
	public ITimeInstant getStart() {
		return start;
	}

	@Override
	public ITimeInstant getEnd() {
		return end;
	}

	@Override
	public ITimeDuration getStep() {
		return step;
	}

	/**
	 * For Groovy expressions: time << n
	 * 
	 * @param n
	 * @return
	 */
	public Time leftShift(int n) {
		return getPrevious(n);
	}

	public Time rightShift(int n) {
		return getNext(n);
	}

	public Time getNext(int n) {
		if (parentExtent != null && this.locatedLinearOffset > 0) {
			return (this.locatedLinearOffset + n) < parentExtent.size()
					? (Time) parentExtent.getExtent(this.locatedLinearOffset + n)
					: null;
		}
		return null;
	}

	public Time getInit() {
		return (Time) getExtent(0);
	}

	public Time getPrevious(int n) {
		if (parentExtent != null && this.locatedLinearOffset > 1) {
			return (this.locatedLinearOffset - n) >= 1 ? (Time) parentExtent.getExtent(this.locatedLinearOffset - n)
					: null;
		}
		return null;
	}

	@Override
	public ITime getExtent(long stateIndex) {

		// if (this.extension != null && stateIndex > 0) {
		// Pair<Long, Long> ext = extension.getExtension((int)stateIndex - 1);
		// return create(ext.getFirst(), ext.getSecond()).withLocatedOffset(stateIndex);
		// }

		if (stateIndex >= size() || stateIndex < 0) {
			throw new KlabIllegalArgumentException(
					"time: state " + stateIndex + " requested when size == " + multiplicity);
		}

		if (size() == 1) {
			return extentType == ITime.Type.LOGICAL ? initialization(this) : this;
		}

		return makeExtent(stateIndex);
	}

	/**
	 * Handles the surprisingly difficult issue of taking the previous time extent
	 * that is right before the passed one, without breaking the temporal contract.
	 * 
	 * @param extent
	 * @return
	 */
	public static Time getPreviousExtent(ITime extent) {

		Time ret = null;

		Time time = (Time) extent;

		/*
		 * break down the step into an integer offset and a fraction
		 */
		long intStep = (long) time.resolution.getMultiplier();
		// leftover is the fraction of the INTERVAL that is left after moving forward
		// intStep of them.
		double leftover = 0;
		double stepDecimal = 0;
		if (time.resolution.getMultiplier() > (double) intStep) {
			leftover = (time.resolution.getMultiplier() - (double) intStep);
		}
		if ((int) time.resolution.getMultiplier() > time.resolution.getMultiplier()) {
			stepDecimal = time.resolution.getMultiplier() - (int) time.resolution.getMultiplier();
		}

		/*
		 * merge back the integer part in the offset to obtain a fractional part that is
		 * less than 1. The fraction, if != 0, cannot possibly use the non-regular
		 * durations so it will be adjusted to 30d or 365d for months and years.
		 */
		if (leftover > 1) {
			BigDecimal bigDecimal = new BigDecimal(leftover);
			intStep += bigDecimal.longValue();
			leftover = bigDecimal.subtract(new BigDecimal(bigDecimal.longValue())).doubleValue();
		}

		DateTime start = null;
		DateTime end = null;

		if (time.start != null) {

			start = ((TimeInstant) time.start).asDate();

			switch (time.resolution.getType()) {
			case CENTURY:
				start = start.minusYears((int) (100 * intStep));
				if (leftover > 0) {
					long millis = (long) ((100 * DateTimeConstants.MILLIS_PER_DAY * 365) * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusYears(100 * (int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) ((100 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case DAY:
				start = start.minusDays((int) intStep);
				if (leftover > 0) {
					long millis = (long) (DateTimeConstants.MILLIS_PER_DAY * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusDays((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) (DateTimeConstants.MILLIS_PER_DAY * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case DECADE:
				start = start.minusYears((int) (10 * intStep));
				if (leftover > 0) {
					long millis = (long) ((10 * DateTimeConstants.MILLIS_PER_DAY * 365) * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusYears(10 * (int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) ((10 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case HOUR:
				start = start.minusHours((int) (100 * intStep));
				if (leftover > 0) {
					long millis = (long) (DateTimeConstants.MILLIS_PER_HOUR * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusHours((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) (DateTimeConstants.MILLIS_PER_HOUR * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case MILLENNIUM:
				start = start.minusYears((int) (1000 * intStep));
				if (leftover > 0) {
					long millis = (long) ((1000 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusYears(1000 * (int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) ((1000 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case MILLISECOND:
				start = start.minusMillis((int) intStep);
				end = start.plusMillis((int) time.resolution.getMultiplier());
				if (stepDecimal > 0.5) {
					end = new DateTime(end.getMillis() + 1);
				}
				break;
			case MINUTE:
				start = start.minusMinutes((int) intStep);
				if (leftover > 0) {
					long millis = (long) ((DateTimeConstants.MILLIS_PER_MINUTE * 365) * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusMinutes((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) ((DateTimeConstants.MILLIS_PER_MINUTE * 365) * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case MONTH:
				start = start.minusMonths((int) intStep);
				if (leftover > 0) {
					long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 30) * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusMonths((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 30) * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case SECOND:
				start = start.minusSeconds((int) intStep);
				if (leftover > 0) {
					long millis = (long) (1000 * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusSeconds((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) (1000 * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case WEEK:
				start = start.minusWeeks((int) intStep);
				if (leftover > 0) {
					long millis = (long) (DateTimeConstants.MILLIS_PER_WEEK * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusWeeks((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) (DateTimeConstants.MILLIS_PER_WEEK * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			case YEAR:
				start = start.minusYears((int) intStep);
				if (leftover > 0) {
					long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 365) * leftover);
					start = new DateTime(start.getMillis() + millis);
				}
				end = start.plusYears((int) time.resolution.getMultiplier());
				if (stepDecimal > 0) {
					long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
					end = new DateTime(end.getMillis() + millis);
				}
				break;
			default:
				break;
			}
		}

		ret = time.copy();

		if (start == null) {
			ret.partial = true;
		}

		ret.step = null;
		ret.start = start == null ? null : new TimeInstant(start);
		ret.end = end == null ? null : new TimeInstant(end);
		ret.extentType = ITime.Type.PHYSICAL;
		ret.multiplicity = 1;
		ret.resolution = resolution(time.resolution.getMultiplier(), time.resolution.getType());
		ret.locatedExtent = time.locatedExtent;
		ret.locatedOffsets = new long[] { -1 };
		ret.locatedLinearOffset = -1;
		ret.parentExtent = time;

		return ret;
	}

	private Time makeExtent(long stateIndex) {

		Time ret = null;

		if (stateIndex == 0) {

			ret = initialization(this);

		} else {

			DateTime start = null;
			DateTime end = null;

			// if (this.extension != null) {
			// Pair<Long, Long> span = this.extension.getExtension((int) stateIndex - 1);
			// start = new DateTime(span.getFirst());
			// end = new DateTime(span.getSecond());
			// } else {

			/*
			 * break down the step into an integer offset and a fraction
			 */
			long intStep = (long) resolution.getMultiplier() * (stateIndex - 1);
			// leftover is the fraction of the INTERVAL that is left after moving forward
			// intStep of them.
			double leftover = 0;
			double stepDecimal = 0;
			if (resolution.getMultiplier() > (double) intStep) {
				leftover = (resolution.getMultiplier() - (double) intStep) * (stateIndex - 1);
			}
			if ((int) resolution.getMultiplier() > resolution.getMultiplier()) {
				stepDecimal = resolution.getMultiplier() - (int) resolution.getMultiplier();
			}

			/*
			 * merge back the integer part in the offset to obtain a fractional part that is
			 * less than 1. The fraction, if != 0, cannot possibly use the non-regular
			 * durations so it will be adjusted to 30d or 365d for months and years.
			 */
			if (leftover > 1) {
				BigDecimal bigDecimal = new BigDecimal(leftover);
				intStep += bigDecimal.longValue();
				leftover = bigDecimal.subtract(new BigDecimal(bigDecimal.longValue())).doubleValue();
			}

			if (this.start != null) {

				start = ((TimeInstant) this.start).asDate();

				switch (resolution.getType()) {
				case CENTURY:
					start = start.plusYears((int) (100 * intStep));
					if (leftover > 0) {
						long millis = (long) ((100 * DateTimeConstants.MILLIS_PER_DAY * 365) * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusYears(100 * (int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) ((100 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case DAY:
					start = start.plusDays((int) intStep);
					if (leftover > 0) {
						long millis = (long) (DateTimeConstants.MILLIS_PER_DAY * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusDays((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) (DateTimeConstants.MILLIS_PER_DAY * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case DECADE:
					start = start.plusYears((int) (10 * intStep));
					if (leftover > 0) {
						long millis = (long) ((10 * DateTimeConstants.MILLIS_PER_DAY * 365) * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusYears(10 * (int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) ((10 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case HOUR:
					start = start.plusHours((int) (100 * intStep));
					if (leftover > 0) {
						long millis = (long) (DateTimeConstants.MILLIS_PER_HOUR * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusHours((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) (DateTimeConstants.MILLIS_PER_HOUR * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case MILLENNIUM:
					start = start.plusYears((int) (1000 * intStep));
					if (leftover > 0) {
						long millis = (long) ((1000 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusYears(1000 * (int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) ((1000 * DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case MILLISECOND:
					start = start.plusMillis((int) intStep);
					end = start.plusMillis((int) resolution.getMultiplier());
					if (stepDecimal > 0.5) {
						end = new DateTime(end.getMillis() + 1);
					}
					break;
				case MINUTE:
					start = start.plusMinutes((int) intStep);
					if (leftover > 0) {
						long millis = (long) ((DateTimeConstants.MILLIS_PER_MINUTE * 365) * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusMinutes((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) ((DateTimeConstants.MILLIS_PER_MINUTE * 365) * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case MONTH:
					start = start.plusMonths((int) intStep);
					if (leftover > 0) {
						long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 30) * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusMonths((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 30) * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case SECOND:
					start = start.plusSeconds((int) intStep);
					if (leftover > 0) {
						long millis = (long) (1000 * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusSeconds((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) (1000 * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case WEEK:
					start = start.plusWeeks((int) intStep);
					if (leftover > 0) {
						long millis = (long) (DateTimeConstants.MILLIS_PER_WEEK * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusWeeks((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) (DateTimeConstants.MILLIS_PER_WEEK * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				case YEAR:
					start = start.plusYears((int) intStep);
					if (leftover > 0) {
						long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 365) * leftover);
						start = new DateTime(start.getMillis() + millis);
					}
					end = start.plusYears((int) resolution.getMultiplier());
					if (stepDecimal > 0) {
						long millis = (long) ((DateTimeConstants.MILLIS_PER_DAY * 365) * stepDecimal);
						end = new DateTime(end.getMillis() + millis);
					}
					break;
				default:
					break;
				}
			}
			// }

			// we're a grid, the state we use is
			stateIndex--;

			ret = copy();

			if (start == null) {
				ret.partial = true;
			}

			ret.step = null;
			ret.start = start == null ? null : new TimeInstant(start);
			ret.end = end == null ? null : new TimeInstant(end);
			ret.extentType = ITime.Type.PHYSICAL;
			ret.multiplicity = 1;
			ret.resolution = resolution(this.resolution.getMultiplier(), this.resolution.getType());
			ret.locatedExtent = this;
			ret.locatedOffsets = new long[] { stateIndex + 1 };
			ret.locatedLinearOffset = stateIndex + 1;
			ret.parentExtent = this;
		}

		// remember lineage to speed up location of conformant extents
		ret.__id = this.__id;

		return ret;
	}

	@Override
	public boolean isCovered(long stateIndex) {
		return stateIndex >= 0 && stateIndex < size();
	}

	@Override
	public boolean isConsistent() {
		// TODO irregular will need checks
		return !partial;
	}

	public void setPartial(boolean b) {
		this.partial = b;
	}

	@Override
	public boolean isEmpty() {
		return (start == null && end == null && step == null && resolution == null)
				|| (start != null && end != null && start.getMilliseconds() >= end.getMilliseconds());
	}

	@Override
	public long[] getDimensionSizes() {
		return new long[] { size() };
	}

	@Override
	public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encode(IGeometry.Encoding... options) {

		Set<Encoding> opts = EnumSet.noneOf(Encoding.class);
		if (options != null) {
			for (Encoding e : options) {
				opts.add(e);
			}
		}

		String prefix = "T";
		if (getTimeType() == ITime.Type.LOGICAL) {
			prefix = "\u03c4";
		} else if (partial && step == null) {
			prefix = "t";
		}

		String ret = prefix + getDimensionality() + "(" + multiplicity + ")";
		String args = Geometry.PARAMETER_TIME_REPRESENTATION + "=" + getTimeType();

		Time target = this;

		if (this.is(ITime.Type.INITIALIZATION) && opts.contains(IGeometry.Encoding.CONCRETE_TIME_INTERVALS)) {
			args = Geometry.PARAMETER_TIME_REPRESENTATION + "=" + ITime.Type.PHYSICAL;
			if (this.size() > 1) {
				target = getPreviousExtent(this);
			}
		}

		if (target.start != null) {
			if (target.end != null) {
				args += "," + Geometry.PARAMETER_TIME_PERIOD + "=[" + target.start.getMilliseconds() + " "
						+ target.end.getMilliseconds() + "]";
			} else {
				args += "," + Geometry.PARAMETER_TIME_LOCATOR + "=" + target.start.getMilliseconds();
			}
		}
		if (target.step != null) {
			args += "," + Geometry.PARAMETER_TIME_GRIDRESOLUTION + "=" + target.step.getMilliseconds();
		}
		if (target.resolution != null) {
			args += "," + Geometry.PARAMETER_TIME_SCOPE + "=" + target.resolution.getMultiplier();
			args += "," + Geometry.PARAMETER_TIME_SCOPE_UNIT + "=" + target.resolution.getType();
		}
		if (target.coverageResolution != null) {
			args += "," + Geometry.PARAMETER_TIME_COVERAGE_UNIT + "=" + target.coverageResolution.getType();
			args += "," + Geometry.PARAMETER_TIME_COVERAGE_START + "=" + target.coverageStart;
			args += "," + Geometry.PARAMETER_TIME_COVERAGE_END + "=" + target.coverageEnd;
		}

		return ret + "{" + args + "}";
	}

	@Override
	public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {

		if (other instanceof Time) {

			Time ott = (Time) other;
			Time ret = (Time) copy();

			/*
			 * CHECK only intersecting if we're merging grid with grid. Otherwise if we get
			 * here we have accepted the passed extent as candidate, so we just let it have
			 * no effect on the coverage.
			 */
			if (ott.size() > 1 && ret.size() > 1) {
				if (connector.equals(LogicalConnector.INTERSECTION)) {
					Range range = ott.getRange();
					if (range != null) {
						Range merged = getRange().intersection(range);
						ret.start = new TimeInstant(new DateTime((long) merged.getLowerBound()));
						ret.end = new TimeInstant(new DateTime((long) merged.getUpperBound()));
					}
				} else if (connector.equals(LogicalConnector.UNION)) {
					Range range = ott.getRange();
					if (range != null) {
						Range merged = getRange().span(range);
						ret.start = new TimeInstant(new DateTime((long) merged.getLowerBound()));
						ret.end = new TimeInstant(new DateTime((long) merged.getUpperBound()));
					}
				}

				ret.extentType = extentType == ITime.Type.LOGICAL ? ITime.Type.LOGICAL : ITime.Type.PHYSICAL;
				ret.step = null;
				((ResolutionImpl) ret.resolution).setMultiplier(ret.resolution.getMultiplier(ret.start, ret.end));

			} else {
				return ret;
			}

			return ret;
		}

		return null;
	}

	public String toString() {
		return "<" + encode() + ">" + ":" + start + " to " + end;
	}

	@Override
	public long getOffset(long... dimOffsets) {
		return dimOffsets[0];
	}

	@Override
	public IExtent getExtent() {
		return collapse();
	}

	@Override
	public Time copy() {
		return new Time(this);
	}

	public TemporalExtension getExtension() {
		return this.extension;
	}

	public Time focus(Pair<Long, Long> extension) {
		TimeInstant focal = TimeInstant
				.create(extension.getFirst() + ((extension.getSecond() - extension.getFirst()) / 2));
		return focus(focal);
	}

	public Time focus(ITimeInstant focal) {

		Time ret = this.extension != null ? this.extension.at(focal.getMilliseconds()) : copy();

		ret.focus = focal;

		if (this.extension == null) {
			// keep location info
			ret.locatedOffsets = this.locatedOffsets;
			ret.locatedExtent = this.locatedExtent;
			ret.locatedLinearOffset = this.locatedLinearOffset;
		}

		return ret;
	}

	@Override
	public IServiceCall getKimSpecification() {

		List<Object> args = new ArrayList<>();
		if (step != null) {
			args.add("step");
			args.add(step.getSpecification());
		}
		if (start != null) {
			args.add("start");
			args.add(start.getSpecification());
		}
		if (end != null) {
			args.add("start");
			args.add(start.getSpecification());
		}

		if (resolution != null) {
			args.add("resolution");
			args.add(resolution.getMultiplier() + "." + resolution.getType().name());
		}

		args.add("type");
		args.add(extentType == null ? ITime.Type.LOGICAL : extentType.name());

		return new KimServiceCall("time", args.toArray());
	}

	@Override
	public Dimension.Type getType() {
		return Dimension.Type.TIME;
	}

	@Override
	public Resolution getResolution() {
		return resolution;
	}

	@Override
	public boolean isGeneric() {
		return extentType == ITime.Type.LOGICAL;
	}

	@Override
	public ITime getBoundingExtent() {
		return collapse();
	}

	@Override
	public ExtentDimension getExtentDimension() {
		return ExtentDimension.TEMPORAL;
	}

	@Override
	public Pair<Double, IUnit> getStandardizedDimension(ILocator locator) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Time create(Dimension dimension) {

		long[] period = dimension.getParameters().get(Geometry.PARAMETER_TIME_PERIOD, long[].class);
		String representation = dimension.getParameters().get(Geometry.PARAMETER_TIME_REPRESENTATION, String.class);
		Double scope = dimension.getParameters().get(Geometry.PARAMETER_TIME_SCOPE, Double.class);
		String unit = dimension.getParameters().get(Geometry.PARAMETER_TIME_SCOPE_UNIT, String.class);
		Long locator = dimension.getParameters().get(Geometry.PARAMETER_TIME_LOCATOR, Long.class);
		String res = dimension.getParameters().get(Geometry.PARAMETER_TIME_GRIDRESOLUTION, String.class);
		Long tstart = dimension.getParameters().get(Geometry.PARAMETER_TIME_START, Long.class);
		Long tend = dimension.getParameters().get(Geometry.PARAMETER_TIME_END, Long.class);
		String cunit = dimension.getParameters().get(Geometry.PARAMETER_TIME_COVERAGE_UNIT, String.class);
		Long cstart = dimension.getParameters().get(Geometry.PARAMETER_TIME_COVERAGE_START, Long.class);
		Long cend = dimension.getParameters().get(Geometry.PARAMETER_TIME_COVERAGE_END, Long.class);
		ITime.Type type = representation == null ? null : ITime.Type.valueOf(representation.toUpperCase());

		if (type == ITime.Type.INITIALIZATION) {
			return initialization((Scale) null);
		}

		if (type == ITime.Type.TERMINATION) {
			return termination((Scale) null);
		}

		if (dimension.isGeneric()) {
			type = ITime.Type.LOGICAL;
		}

		TimeInstant start = tstart == null ? null : new TimeInstant(tstart);
		TimeInstant end = tend == null ? null : new TimeInstant(tend);
		if (period != null) {
			start = new TimeInstant(period[0]);
			end = new TimeInstant(period[1]);
		} else if (locator != null) {
			start = new TimeInstant(locator);
		}

		Resolution.Type coverage = null;
		if (cunit != null) {
			coverage = Resolution.Type.parse(cunit);
		}

		Resolution.Type resType = unit == null ? null : Resolution.Type.parse(unit);
		if (resType == null && res != null) {
			Resolution rres = resolution(KimQuantity.parse(res));
			resType = rres.getType();
			scope = rres.getMultiplier();
		}

		if (type == null) {
			if (start != null && end != null) {
				type = ITime.Type.PHYSICAL;
				if (resType == null) {
					Resolution rres = resolution(start, end);
					resType = rres.getType();
					scope = rres.getMultiplier();
				}
			}
		}

		return create(type, resType, (scope == null ? null : 1.0), start, end, null, coverage,
				(cstart == null ? -1 : cstart), (cend == null ? -1 : cend));
	}

	@Override
	public ITime.Type getTimeType() {
		return extentType;
	}

	@Override
	public boolean is(ITime.Type type) {
		return this.extentType == type;
	}

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	@Override
	public IExtent at(Object... locators) {

		if (locators != null && locators.length == 1) {
			if (locators[0] instanceof String && "INITIALIZATION".equals(locators[0])) {
				return initialization(this);
			} else if (locators[0] instanceof String && "TERMINATION".equals(locators[0])) {
				return termination(this);
			} else if (locators[0] instanceof Number) {
				long ofs = ((Number) locators[0]).longValue();
				if (this.size() == 1 && ofs == 0) {
					return this;
				} else if (this.size() > ofs) {
					return getExtent(ofs);
				}
			} else if (locators[0] instanceof Time) {
				if (((Time) locators[0]).is(ITime.Type.INITIALIZATION)) {
					// initialization but with our scaleId
					return new Time((Time) locators[0]).withScaleId(getScaleId()).withLocatedOffset(0);
				} else if (((Time) locators[0]).__id == this.__id) {
					return (IExtent) locators[0];
				} else if (locators[0] instanceof TimesliceLocator) {
					return /* this.focus((ITimeInstant) */((TimesliceLocator) locators[0])/*
																							 * .getStart ())
																							 */;
				} else if (((Time) locators[0]).getLocatedOffsets() != null) {
					return (IExtent) locators[0];
				} else if (((Time) locators[0]).focus != null) {
					return focus(((Time) locators[0]).focus);
				} else {
					/*
					 * Mediation situation. Because of the irregular extents, not doing the coverage
					 * thing. TODO: do the coverage thing.
					 */
					Time other = (Time) locators[0];
					IExtent start = at(other.getStart());
					IExtent end = at(other.getEnd());
					// TODO compute how much other.getStart() leaves out of start() and add it
					// somehow to the coverage for the first and last steps
					if (start.equals(end)) {
						// works for initialization, too
						return other;
					}
					return new TimeGrid(other);
				}
			} else if (locators[0] instanceof ITimeInstant) {

				/*
				 * Pick the sub-extent containing the instant or return the entire scale if we
				 * have none. In all cases focalize on the specific instant requested.
				 */
				if (end.equals((ITimeInstant) locators[0])) {
					return termination(this);
				}
				if (!(start == null || start.isAfter((ITimeInstant) locators[0])
						|| (end != null && end.isBefore((ITimeInstant) locators[0])))) {

					if (size() <= 1 || extension != null) {
						return this.focus((ITimeInstant) locators[0]);
					}
					Time last = null;
					for (int i = 1; i < size(); i++) {
						last = (Time) getExtent(i);
						if (last.getStart().getMilliseconds() >= ((ITimeInstant) locators[0]).getMilliseconds()
								|| last.getEnd().getMilliseconds() > ((ITimeInstant) locators[0]).getMilliseconds()) {
							return last.focus((ITimeInstant) locators[0]);
						}
						if (last != null
								&& last.getEnd().getMilliseconds() == ((ITimeInstant) locators[0]).getMilliseconds()) {
							// admit a locator focused on the immediate after
							return last.focus((ITimeInstant) locators[0]);
						}
						// long target = ((ITimeInstant) locators[0]).getMilliseconds();
						// long tleft = target - start.getMilliseconds();
						// long n = tleft / resolution.getSpan() + 1;
						// if (target == end.getMilliseconds()) {
						// /*
						// * last extent, located to get the point before the beginning of the
						// * next period
						// */
						// Time ret = (Time) getExtent(size() - 1);
						// return ret.focus((ITimeInstant) locators[0]);
						//
						// } else if (n >= 0 && n < size()) {
						// Time ret = (Time) getExtent(n);
						// long nn = n;
						// // previous was approximate due to potential irregularity; correct as
						// // needed
						// while(nn > 0 && ret.getEnd().isBefore(((ITimeInstant) locators[0]))) {
						// ret = (Time) getExtent(++nn);
						// }
						// nn = n;
						// while(nn < size() && ret.getStart().isAfter(((ITimeInstant)
						// locators[0]))) {
						// ret = (Time) getExtent(--nn);
						// }
						// return ret.focus((ITimeInstant) locators[0]);
						// }
					}
				}
			}
		}

		throw new KlabException("unhandled time subsetting operation. Call an exorcist.");

	}

	public String describe() {
		return ((TimeInstant) this.start).describe(resolution) + " - " + ((TimeInstant) this.end).describe(resolution);
	}

	private Time withScaleId(String scaleId) {
		setScaleId(scaleId);
		return this;
	}

	Time withLocatedOffset(long n) {
		this.locatedLinearOffset = n;
		this.locatedOffsets = new long[] { n };
		return this;
	}

	public Time upgradeForOccurrents() {
		return create(ITime.Type.GRID, this.getResolution().getType(), 1.0, this.start, this.end,
				TimeDuration.create(this.start, this.end, true));
	}

	@Override
	public boolean intersects(Dimension dimension) {
		Time time = create(dimension);
		return time.getRange() == null || intersects(time);
	}

	// @Override
	// public IExtent adopt(IExtent extent, IMonitor monitor) {
	// /*
	// * TODO for now just adopt resolution and only from incomplete extents. This
	// * should be enough for dynamic models to work.
	// */
	// if (extent instanceof Time) {
	// Time other = (Time) extent;
	// if (other.getStep() != null) {
	// return create(ITime.Type.GRID, other.getResolution().getType(),
	// other.getResolution().getMultiplier(),
	// getStart() == null ? other.getStart() : getStart(),
	// getEnd() == null ? other.getEnd() : getEnd(), other.getStep());
	// }
	// }
	// return this;
	// }

	@Override
	public Resolution getCoverageResolution() {
		return coverageResolution;
	}

	@Override
	public long getCoverageLocatorStart() {
		return coverageStart;
	}

	@Override
	public long getCoverageLocatorEnd() {
		return coverageEnd;
	}

	/**
	 * Create a grid extent with the passed extremes and resolution
	 * 
	 * @param start
	 * @param end
	 * @param resolution2
	 */
	public static Time create(ITimeInstant start, ITimeInstant end, Resolution resolution) {
		return create(ITime.Type.GRID, resolution.getType(), resolution.getMultiplier(), start, end, null);
	}

	public static void main(String[] zoz) {

		TimeInstant now = new TimeInstant(2000);
		TimeInstant then = new TimeInstant(2003);
		Time time = create(now, then, resolution(1, Resolution.Type.MONTH));
		for (int i = 0; i < time.size(); i++) {
			System.out.println(time.getExtent(i));
		}

		// /*
		// * Cover a span with the original resolution, produce variable coverages in
		// the
		// * resulting grid
		// */
		// Time other = create(ITime.Type.PHYSICAL, Resolution.Type.WEEK, 2,
		// new TimeInstant(new DateTime(2000, 2, 10, 0, 0, 0)),
		// new TimeInstant(new DateTime(2000, 5, 11, 0, 0, 0)), null);
		// for (ILocator segment : time.at(other)) {
		// System.out.println(" > " + segment);
		// }
	}

	@Override
	protected Time contextualizeTo(IExtent other, IAnnotation constraint) {
		// TODO Auto-generated method stub
		return this;
	}

	/**
	 * 
	 * @author Ferd
	 *
	 */
	class TimeGrid extends Time {

		List<ILocator> covered = new ArrayList<>();

		public TimeGrid(Time other) {
			super(other);
			Range orext = other.getRange();
			Time extent = (Time) Time.this.at(other.getStart());
			while (extent != null) {

				Range exext = extent.getRange();
				Range inters = orext.intersection(exext);
				double coverage = inters.getWidth() / extent.getRange().getWidth();
				covered.add(extent.withCoverage(coverage));
				long n = extent.getLocatedOffset();
				if (extent.getEnd().isBefore(other.getEnd()) && n < Time.this.size()) {
					extent = (Time) Time.this.getExtent(n + 1);
				} else {
					break;
				}
			}
		}

		@Override
		public long size() {
			return covered.size();
		}

		@Override
		public ITime getExtent(long stateIndex) {
			return (ITime) covered.get((int) stateIndex);
		}

		@Override
		public Iterator<ILocator> iterator() {
			return covered.iterator();
		}
	}

	@Override
	public double getLength(IUnit temporalUnit) {
		if (this.end == null || this.start == null) {
			return 0;
		}
		return temporalUnit
				.convert(this.end.getMilliseconds() - this.start.getMilliseconds(), Units.INSTANCE.MILLISECONDS)
				.doubleValue();
	}

	@Override
	public ITime getNext() {
		if (parentExtent != null && this.locatedLinearOffset > 0) {
			return this.locatedLinearOffset < (parentExtent.size() - 1)
					? (ITime) parentExtent.getExtent(this.locatedLinearOffset + 1)
					: null;
		}
		return null;
	}

	@Override
	public ITimeInstant getFocus() {
		return focus;
	}

	/**
	 * Return a decent display format for the passed time at the passed resolution
	 * 
	 * TODO needs completion
	 * 
	 * @param start2
	 * @param resolution2
	 * @return
	 */
	public static String getDisplayLabel(ITimeInstant time, Resolution resolution) {
		if (resolution.getType() == Resolution.Type.YEAR) {
			return "" + ((TimeInstant) time).time.getYear();
		} else if (resolution.getType() == Resolution.Type.MONTH) {
			YearMonth md = new YearMonth(((TimeInstant) time).time.getYear(),
					((TimeInstant) time).time.getMonthOfYear());
			return "" + md.monthOfYear().getAsShortText() + " " + ((TimeInstant) time).time.getYear();
		} else if (resolution.getType() == Resolution.Type.MONTH) {
			YearMonth md = new YearMonth(((TimeInstant) time).time.getYear(),
					((TimeInstant) time).time.getMonthOfYear());
			return "" + md.monthOfYear().getAsShortText() + ((TimeInstant) time).time.getDayOfMonth() + ", "
					+ ((TimeInstant) time).time.getYear();
		}
		return time.toString();
	}

	@Override
	public ITime earliest() {
		return size() > 1 ? (ITime) getExtent(1) : null;
	}

	@Override
	public ITime latest() {
		return size() > 1 ? (ITime) getExtent(size() - 1) : null;
	}

	@Override
	public double getDimensionSize(IUnit unit) {
		/*
		 * must be a time unit
		 */
		Resolution resolution = Units.INSTANCE.asTemporalResolution(unit);
		if (resolution == null) {
			return Double.NaN;
		}
		long periods = this.start.getPeriods(this.end, resolution);
		ITimeInstant intend = this.start.plus((int) periods, resolution);
		long leftover = this.end.getMilliseconds() - intend.getMilliseconds();
		return (double) periods + ((double) leftover / (double) resolution.getSpan());
	}

	@Override
	public boolean isDistributed() {
		return size() > 1 || isRegular() || this.getTimeType() == ITime.Type.GRID;
	}

	public boolean mergeTransition(Dimension transition) {

		ITime tr = promote(transition);
		if (tr.getTimeType() == ITime.Type.INITIALIZATION) {
			return false;
		}
		if (extension == null) {
			extension = new TemporalExtension(this);
		}
		if (extension.add(tr)) {
			// this.extentType = ITime.Type.GRID;
			// multiplicity = extension.size() + 1;
			return true;
		}

		return false;
	}

	public static ITime promote(Dimension transition) {
		if (transition instanceof ITime) {
			return (ITime) transition;
		}
		return create(transition);
	}

	public int getLocatedTimeslice() {
		return this.timeSlice;
	}

	Time withLocatedTimeslice(int i) {
		this.withLocatedOffset(i + 1);
		this.timeSlice = i;
		return this;
	}

	public ITime[] getChangedExtents() {
		long[] timestamps = getUpdateTimestamps();
		ITime[] ret = new ITime[extension.size()];
		for (int i = 0; i < extension.size(); i++) {
			Pair<Long, Long> ext = extension.getExtension(i);
			ret[i] = create(ext.getFirst(), ext.getSecond()).withLocatedOffset(timestamps[i]);
		}
		return ret;
	}

	public long[] getUpdateTimestamps() {
		if (extension != null) {
			return extension.getTimestamps();
		}
		return new long[] {};
	}

	@Override
	public boolean hasChangeDuring(ITime time) {
		return extension == null ? false : extension.hasChangeDuring(time);
	}

	/**
	 * Copier with option to copy the "stateful" extension events.
	 * 
	 * @param copyExtension
	 * @return
	 */
	public Time copy(boolean copyExtension, boolean copyScaleLocation) {
		Time ret = new Time(this);
		if (copyExtension) {
			ret.extension = this.extension == null ? null : new TemporalExtension(this.extension);
		}
		if (copyScaleLocation) {
			ret.copyScaleLocation(this);
		}
		return ret;
	}

	/**
	 * If this represents a year, return the year it represents; otherwise return
	 * the start millisecond.
	 * 
	 * @return
	 */
	public long getNumericLocator() {
		if (this.getTimeType() == ITime.Type.INITIALIZATION) {
			return 0;
		}
		if (this.resolution != null && this.resolution.getType() == Resolution.Type.YEAR) {
			return start.getYear();
		}
		return start.getMilliseconds();
	}

	/*
	 * quickly return the span in milliseconds
	 */
	public long getLength() {
		return (start == null || end == null) ? 0 : end.getMilliseconds() - start.getMilliseconds();
	}

}
