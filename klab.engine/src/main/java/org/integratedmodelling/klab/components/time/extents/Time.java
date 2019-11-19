package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimDate;
import org.integratedmodelling.kim.model.KimQuantity;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
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
import org.integratedmodelling.klab.components.time.extents.mediators.TimeIdentity;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale;
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
	boolean partial = false;
	boolean regular = true;

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

	public static Time initialization(Scale scale) {
		Time ret = new Time();
		ret.extentType = ITime.Type.INITIALIZATION;
		ret.start = new TimeInstant(0);
		ret.end = new TimeInstant(0);
		ret.multiplicity = 1;
		ret.resolution = new ResolutionImpl(Resolution.Type.YEAR, 0.0);
		if (scale != null) {
			ret.setScaleId(scale.getScaleId());
		}
		return ret;
	}

	public static Time create(int year) {
		Time ret = new Time();
		ret.extentType = ITime.Type.PHYSICAL;
		ret.start = new TimeInstant(year);
		ret.end = new TimeInstant(new DateTime(year + 1, 1, 1, 0, 0));
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
			} else if (start != null && end != null) {
				ret.multiplicity = (long) (ret.getCoveredExtent() / ret.step.getMilliseconds()) + 1;
			} else {
				ret.partial = true;
				ret.multiplicity = 0;
			}
		}
		return ret;
	}

	public static Time create(IAnnotation timeAnnotation) {

		if (timeAnnotation.containsKey(IServiceCall.DEFAULT_PARAMETER_NAME)) {
			if (timeAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME) instanceof Integer) {
				timeAnnotation.put("year", timeAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME));
			}
		}

		return (Time) new org.integratedmodelling.klab.components.time.services.Time().eval(timeAnnotation,
				new Expression.Scope(Klab.INSTANCE.getRootMonitor()));
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
		return start != null && end != null ? Range.closed(start.getMilliseconds(), end.getMilliseconds()) : null;
	}

	@Override
	public IExtent merge(IExtent extent) throws KlabException {
		// TODO hostia
		if (extent instanceof ITime) {

			ITime other = (ITime)extent;
			Time ret = copy();
			
			// schiaff in the unknowns
			if (start == null && other.getStart() != null) {
				ret.start = other.getStart();
			}
			if (end == null && other.getEnd() != null) {
				ret.end = other.getEnd();
			}
			
			// shouldn't change representation, step and the like
			if (this.resolution == null) {
				ret.resolution = other.getResolution();
			}
			
			ret.multiplicity = 1;
			if (ret.start != null && ret.end != null && ret.step != null) {
				ret.multiplicity = (ret.end.getMilliseconds() - ret.start.getMilliseconds()) / ret.step.getMilliseconds();
			}
			
			return ret;
			
		}
		return this;
	}

	@Override
	public double getCoveredExtent() {
		return end.getMilliseconds() - start.getMilliseconds();
	}

	@Override
	public double getCoverage() {
		return end.getMilliseconds() - start.getMilliseconds();
	}

	@Override
	public IScaleMediator getMediator(IExtent extent) {
		// TODO Auto-generated method stub
		return new TimeIdentity();
	}

	@Override
	public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
		// TODO Auto-generated method stub
		return copy();
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
		return new long[] { multiplicity };
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
						this.resolution.getType(), resolution.getMultiplier(start, end), start, end, null)
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

	@Override
	public IExtent getExtent(long stateIndex) {

		if (stateIndex >= multiplicity || stateIndex < 0) {
			throw new IllegalArgumentException("time: state " + stateIndex + " requested when size == " + multiplicity);
		}

		if (this.multiplicity == 1) {
			return this;
		}

		// should also work for infinite time
		long newStart = this.start.getMilliseconds() + (this.step.getMilliseconds() * stateIndex);
		long newEnd = newStart + this.step.getMilliseconds();

		// TODO if realtime, we should align with the clock as all these ops cannot
		// guarantee synchronicity

		Time ret = copy();

		ret.step = null;
		ret.start = new TimeInstant(newStart);
		ret.end = new TimeInstant(newEnd);
		ret.extentType = ITime.Type.PHYSICAL;
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
		return new long[] { multiplicity };
	}

	@Override
	public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encode() {

		String prefix = "T";
		if (partial && step == null) {
			prefix = "t";
		}

		String ret = prefix + getDimensionality() + "(" + multiplicity + ")";
		String args = Geometry.PARAMETER_TIME_REPRESENTATION + "=" + getTimeType();

		if (!this.is(ITime.Type.INITIALIZATION)) {
			if (start != null) {
				if (end != null) {
					args += "," + Geometry.PARAMETER_TIME_PERIOD + "=[" + start.getMilliseconds() + " "
							+ end.getMilliseconds() + "]";
				} else {
					args += "," + Geometry.PARAMETER_TIME_LOCATOR + "=" + start.getMilliseconds();
				}
			}
			if (step != null) {
				args += "," + Geometry.PARAMETER_TIME_GRIDRESOLUTION + "=" + step.getMilliseconds();
			}
			if (resolution != null) {
				args += "," + Geometry.PARAMETER_TIME_SCOPE + "=" + resolution.getMultiplier();
				args += "," + Geometry.PARAMETER_TIME_SCOPE_UNIT + "=" + resolution.getType();
			}
		}

		return ret + "{" + args + "}";
	}

	@Override
	public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {

		if (other instanceof Time) {

			Time ott = (Time) other;
			Time ret = (Time) copy();
			if (connector.equals(LogicalConnector.INTERSECTION)) {
				Range<Long> range = ott.getRange();
				if (range != null) {
					Range<Long> merged = getRange().intersection(range);
					ret.start = new TimeInstant(new DateTime(merged.lowerEndpoint()));
					ret.end = new TimeInstant(new DateTime(merged.upperEndpoint()));
				}
			} else if (connector.equals(LogicalConnector.UNION)) {
				Range<Long> range = ott.getRange();
				if (range != null) {
					Range<Long> merged = getRange().span(range);
					ret.start = new TimeInstant(new DateTime(merged.lowerEndpoint()));
					ret.end = new TimeInstant(new DateTime(merged.upperEndpoint()));
				}
			}

			ret.extentType = extentType == ITime.Type.LOGICAL ? ITime.Type.LOGICAL : ITime.Type.PHYSICAL;
			ret.step = null;
			((ResolutionImpl) ret.resolution).setMultiplier(ret.resolution.getMultiplier(ret.start, ret.end));

			return ret;
		}

		return null;
	}

	public String toString() {
		return "<" + encode() + ">";
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
		args.add(extentType.name());

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

	public static Time create(Dimension dimension) {

		long[] period = dimension.getParameters().get(Geometry.PARAMETER_TIME_PERIOD, long[].class);
		String representation = dimension.getParameters().get(Geometry.PARAMETER_TIME_REPRESENTATION, String.class);
		Double scope = dimension.getParameters().get(Geometry.PARAMETER_TIME_SCOPE, Double.class);
		String unit = dimension.getParameters().get(Geometry.PARAMETER_TIME_SCOPE_UNIT, String.class);
		Long locator = dimension.getParameters().get(Geometry.PARAMETER_TIME_LOCATOR, Long.class);
		Long tstep = dimension.getParameters().get(Geometry.PARAMETER_TIME_GRIDRESOLUTION, Long.class);
		Long tstart = dimension.getParameters().get(Geometry.PARAMETER_TIME_START, Long.class);
		Long tend = dimension.getParameters().get(Geometry.PARAMETER_TIME_END, Long.class);

		ITime.Type type = representation == null ? null : ITime.Type.valueOf(representation.toUpperCase());

		if (type == ITime.Type.INITIALIZATION) {
			return initialization(null);
		}

		TimeInstant start = tstart == null ? null : new TimeInstant(tstart);
		TimeInstant end = tend == null ? null : new TimeInstant(tend);
		if (period != null) {
			start = new TimeInstant(period[0]);
			end = new TimeInstant(period[1]);
		} else if (locator != null) {
			start = new TimeInstant(locator);
		}

		Resolution.Type resType = unit == null ? null : Resolution.Type.parse(unit);
		ITimeDuration step = tstep == null ? null : TimeDuration.create(tstep, resType);

		return create(type, resType, scope == null ? null : 1.0, start, end, step);
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
			if (locators[0] instanceof Number) {
				long ofs = ((Number) locators[0]).longValue();
				if (this.size() == 1 && ofs == 0) {
					return this;
				} else if (this.size() > ofs) {
					return getExtent(ofs);
				}
			} else if (locators[0] instanceof Time) {
				if (((Time) locators[0]).is(ITime.Type.INITIALIZATION)) {
					// initialization but with our scaleId
					return new Time((Time) locators[0]).withScaleId(getScaleId());
				}
				/*
				 * TODO potential mediation situation
				 */
			} else if (locators[0] instanceof ITimeInstant) {
				
				/*
				 * Pick the sub-extent containing the instant
				 */
				if (!(start == null || start.isAfter((ITimeInstant) locators[0])
						|| (end != null && end.isBefore((ITimeInstant) locators[0])))) {
					
					if (size() <= 1) {
						return this;
					}
					
					if (step != null) {
						long target = ((ITimeInstant) locators[0]).getMilliseconds();
						long tleft = target - start.getMilliseconds();
						long n = tleft/step.getMilliseconds();
						if (n >= 0 && n < size()) {
							return getExtent(n);
						}
					}
				}
			}
		}

		throw new KlabException("HOSTIA unhandled time subsetting operation. Call the exorcist.");

	}
	
	public String describe() {
		return "[" + this.start + " - " + this.end + "]";
	}

	private IExtent withScaleId(String scaleId) {
		setScaleId(scaleId);
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

}
