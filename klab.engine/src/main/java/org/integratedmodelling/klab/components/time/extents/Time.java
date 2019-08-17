package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimDate;
import org.integratedmodelling.kim.model.KimQuantity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;

import com.google.common.collect.Range;

public class Time extends Extent implements ITime {

	ITime.Type extentType;
	ITimeInstant start;
	ITimeInstant end;
	ITimeDuration step;
	boolean realtime = false;
	Resolution resolution;
	long multiplicity = 1;

	/**
	 * The empty, non-descript initialization locator refers to the time before time
	 * begins. Any recontextualization of the initialization locator produces the
	 * initialization locator.
	 */
	public static ILocator INITIALIZATION = new Time() {

		public int hashCode() {
			return 234567;
		}

		@Override
		public boolean equals(Object o) {
			return o == this;
		}
		
		@Override
		public Time copy() {
			return this;
		}
		
		@Override 
		public Time getExtent(long offset) {
			return this;
		}

		@Override
		public boolean contains(IExtent o) throws KlabException {
			return o == this;
		}

		@Override
		public boolean overlaps(IExtent o) throws KlabException {
			return o == this;
		}

		@Override
		public boolean intersects(IExtent o) throws KlabException {
			return o == this;
		}
		
		
	};

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

		public Resolution copy() {
			return new ResolutionImpl(type, multiplier);
		}

		@Override
		public double getMultiplier(ITimeInstant start, ITimeInstant end) {
			double span = end.getMillis() - start.getMillis();
			return span / type.getMilliseconds();
		}

		public void setMultiplier(double multiplier) {
			this.multiplier = multiplier;
		}

		public String toString() {
			return multiplier + " " + type;
		}
	}

	private Time() {
	}

	private Time(Time time) {
		this.baseDimension = time.baseDimension;
		this.end = time.end;
		this.extentType = time.extentType;
		this.multiplicity = time.multiplicity;
		this.realtime = time.realtime;
		this.resolution = ((ResolutionImpl) time.resolution).copy();
		this.start = time.start;
		this.step = time.step;
	}

	public static Time create(int year) {
		Time ret = new Time();
		ret.extentType = ITime.Type.SPECIFIC;
		ret.start = new TimeInstant(year);
		ret.end = new TimeInstant(new DateTime(year + 1, 1, 1, 0, 0));
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, 1.0);
		return ret;
	}

	public static Time create(int startYear, int endYear) {
		Time ret = new Time();
		ret.extentType = ITime.Type.SPECIFIC;
		ret.start = new TimeInstant(startYear);
		ret.end = new TimeInstant(endYear);
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, endYear - startYear);
		return ret;
	}

	public static Time create(ITime.Type type, Resolution.Type resolutionType, double resolutionMultiplier,
			ITimeInstant start, ITimeInstant end, ITimeDuration period) {
		Time ret = new Time();
		ret.extentType = type;
		ret.start = start;
		ret.end = end;
		ret.resolution = new ResolutionImpl(resolutionType, resolutionMultiplier);
		ret.step = period;
		if (ret.step != null) {
			if (type == ITime.Type.REAL && ret.end == null) {
				ret.multiplicity = Geometry.INFINITE_SIZE;
			} else {
				ret.multiplicity = (long) ret.getCoveredExtent() / ret.step.getMilliseconds();
			}
		}
		return ret;
	}

	public static ITimeInstant instant(KimDate date) {
		DateTime dtime = new DateTime(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMin(),
				date.getSec(), date.getMs());
		return new TimeInstant(dtime);
	}

	public static ITimeInstant instant(int year) {
		return instant(KimDate.asDate(year));
	}

	public static ITimeDuration duration(IKimQuantity spec) {
		Resolution res = new ResolutionImpl(Resolution.Type.parse(spec.getUnit()), spec.getValue().doubleValue());
		return TimeDuration.create((long) (res.getMultiplier() * res.getType().getMilliseconds()), res.getType());
	}

	public static ITimeDuration duration(String string) {
		return duration(KimQuantity.parse(string));
	}

	public static ITimeDuration duration(Number number, Resolution.Type type) {
		return TimeDuration.create(number.longValue(), type);
	}

	public static Resolution resolution(IKimQuantity spec) {
		return new ResolutionImpl(Resolution.Type.parse(spec.getUnit()), spec.getValue().doubleValue());
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

	private Range<Long> getRange() {
		return Range.closed(start.getMillis(), end.getMillis());
	}

	@Override
	public IExtent merge(IExtent extent) throws KlabException {
		// TODO hostia
		return this;
	}

	@Override
	public double getCoveredExtent() {
		return end.getMillis() - start.getMillis();
	}

	@Override
	public double getCoverage() {
		return end.getMillis() - start.getMillis();
	}

	@Override
	public IScaleMediator getMediator(IExtent extent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ILocator> T as(Class<T> cls) {
//		if (Long.class.isAssignableFrom(cls)) {
//			return (T) Long.valueOf(locatedOffset < 0 ? 0 : locatedOffset);
//		} else if (Long[].class.isAssignableFrom(cls)) {
//			return (T) new Long[] { locatedOffset < 0 ? 0l : locatedOffset };
//		} // TODO
		return null;
	}

	@Override
	public long size() {
		return multiplicity;
	}

	@Override
	public boolean contains(IExtent o) throws KlabException {
		return o instanceof Time ? getRange().encloses(((Time) o).getRange()) : false;
	}

	@Override
	public boolean overlaps(IExtent o) throws KlabException {
		return o instanceof Time ? ((Time) o).intersects(this) : false;
	}

	@Override
	public boolean intersects(IExtent o) throws KlabException {
		return o instanceof Time ? getRange().intersection(((Time) o).getRange()).isEmpty() : false;
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
		// TODO Auto-generated method stub
		return new long[] { multiplicity };
	}

	@Override
	public IParameters<String> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time collapse() {
		return create(this.extentType == ITime.Type.GENERIC ? ITime.Type.GENERIC : ITime.Type.SPECIFIC,
				this.resolution.getType(), resolution.getMultiplier(start, end), start, end, null);
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

	@Override
	public IExtent getExtent(long stateIndex) {

		if (stateIndex >= multiplicity || stateIndex < 0) {
			throw new IllegalArgumentException("time: state " + stateIndex + " requested when size == " + multiplicity);
		}

		if (this.multiplicity == 1) {
			return this;
		}

		// should also work for infinite time
		long newStart = this.start.getMillis() + (this.step.getMilliseconds() * stateIndex);
		long newEnd = newStart + this.step.getMilliseconds();

		// TODO if realtime, we should align with the clock as all these ops cannot
		// guarantee synchronicity

		Time ret = copy();

		ret.start = new TimeInstant(newStart);
		ret.end = new TimeInstant(newEnd);
		ret.extentType = ITime.Type.SPECIFIC;
		ret.multiplicity = 1;
		ret.resolution = resolution(ret.start, ret.end);
		ret.locatedExtent = this;
		ret.locatedOffsets = new long[] { stateIndex };

		return ret;
	}

	@Override
	public boolean isCovered(long stateIndex) {
		return stateIndex >= 0 && stateIndex < multiplicity;
	}

	@Override
	public boolean isConsistent() {
		// TODO irregular will need checks
		return true;
	}

	@Override
	public boolean isEmpty() {
		return start.getMillis() >= end.getMillis();
	}

	@Override
	public long[] getDimensionSizes() {
		return new long[] { multiplicity };
	}

	@Override
	public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encode() {
		String ret = "T" + getDimensionality() + "(" + multiplicity + ")";
		String opt = "{";
		if (opt.length() > 1) {
			ret += opt + "}";
		}
		return ret;
	}

	@Override
	public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {

		if (other instanceof Time) {

			Time ott = (Time) other;
			Time ret = (Time) copy();
			if (connector.equals(LogicalConnector.INTERSECTION)) {
				Range<Long> merged = getRange().intersection(ott.getRange());
				ret.start = new TimeInstant(new DateTime(merged.lowerEndpoint()));
				ret.end = new TimeInstant(new DateTime(merged.upperEndpoint()));
			} else if (connector.equals(LogicalConnector.UNION)) {
				Range<Long> merged = getRange().span(ott.getRange());
				ret.start = new TimeInstant(new DateTime(merged.lowerEndpoint()));
				ret.end = new TimeInstant(new DateTime(merged.upperEndpoint()));
			}

			ret.extentType = extentType == ITime.Type.GENERIC ? ITime.Type.GENERIC : ITime.Type.SPECIFIC;
			ret.step = null;
			((ResolutionImpl) ret.resolution).setMultiplier(ret.resolution.getMultiplier(ret.start, ret.end));

			return ret;
		}

		return null;
	}

	public String toString() {
		return "<TIME " + encode() + ">";
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

	@Override
	public IServiceCall getKimSpecification() {
		// TODO Auto-generated method stub
		return null;
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
		return extentType == ITime.Type.GENERIC;
	}

	@Override
	public IExtent getBoundingExtent() {
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

	public static IExtent create(Dimension dimension) {

		Resolution resolution = null;
		TimeInstant start = null;
		TimeInstant end = null;
		ITime.Type type = null;
		ITimeDuration step = null;

		// TODO
		System.out.println("FAAAAAAAAAA");

		return create(type, resolution.getType(), resolution.getMultiplier(), start, end, step);
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
		// TODO must be a grid or realtime
		// locator can be an anchored time or period contained in a step, a timeinstant, an
		// integer offset or a long millisecond value.
		return null;
	}

}
