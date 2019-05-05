package org.integratedmodelling.klab.components.time.extents;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.observations.scale.time.ITimePeriod;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.joda.time.DateTime;

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
	}

	private Time() {
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

	@Override
	public int getScaleRank() {
		return resolution.getType().getRank();
	}

	@Override
	public IExtent merge(IExtent extent) throws KlabException {
		// TODO Auto-generated method stub
//		return null;
	    return this;
	}

	@Override
	public double getCoveredExtent() {
		return end.getMillis() - start.getMillis();
	}

	@Override
	public double getCoverage() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean overlaps(IExtent o) throws KlabException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(IExtent o) throws KlabException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRegular() {
		return step != null;
	}

	@Override
	public int getDimensionality() {
		// TODO Auto-generated method stub
		return 0;
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
	public ITimePeriod collapse() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConsistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long[] getDimensionSizes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encode() {
		String ret = "S" + (multiplicity == 1 ? "0" : "1") + "(" + multiplicity + ")";
		String opt = "{";
		if (opt.length() > 1) {
			ret += opt + "}";
		}
		return ret;
	}

	@Override
	public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] getDimensionOffsets(long linearOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getOffset(long[] dimOffsets) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IExtent getExtent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractExtent copy() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGeneric() {
		// TODO Auto-generated method stub
		return false;
	}

}
