package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimDate;
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
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;

import com.google.common.collect.Range;

/**
 * Examples:
 * 
 * create(ITime.GENERIC, 1, ITime.Resolution.Type.YEAR) -> generic 1-year
 * create(ITime.GENERIC, 10, ITime.Resolution.Type.YEAR) -> generic 10-year
 * create(1975, ITime.Resolution.Type.YEAR) -> specific 1-year period 1975
 * create(1980, 2000) -> specific, 20 years period create(1980, 2000, 1,
 * ITime.Resolution.Type.DAY) -> 1-day Grid create("ISOdate", "ISOdate",
 * 102030L) -> date to date grid with ms resolution create(ITime.REALTIME,
 * ITime.Resolution.Type.HOUR) -> realtime year-res, start now, end never
 * 
 * @param objects
 * @return
 */
public class Time extends Extent implements ITime {

	ITime.Type extentType;
	ITimeInstant start;
	ITimeInstant end;
	ITimeDuration step;
	boolean realtime = false;
	Resolution resolution;
	long multiplicity = 1;

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

	public static ITimeDuration duration(IKimQuantity iKimQuantity) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ITimeDuration duration(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ITimeDuration duration(Number number, Resolution.Type type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Resolution resolution(int i, Resolution.Type focus) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Resolution resolution(IKimQuantity spec) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Resolution resolution(ITimeInstant start2, ITimeInstant end2) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Resolution resolution(String string) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		// TODO Auto-generated method stub
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
	public long getOffset(ILocator index) {
		// TODO Auto-generated method stub
		return 0;
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
	public ITime at(ILocator locator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExtent getExtent(long stateIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCovered(long stateIndex) {
		// TODO only meaningful for irregular time
		return true;
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
	public long[] getDimensionOffsets(long linearOffset) {
		return new long[] { linearOffset };
	}

	@Override
	public long getOffset(long[] dimOffsets) {
		return dimOffsets[0];
	}

	@Override
	public IExtent getExtent() {
		return collapse();
	}

	@Override
	public AbstractExtent copy() {
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



}
