package org.integratedmodelling.klab.scale;

import java.util.Iterator;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.common.LogicalConnector;

/**
 * Common superclass for all Extents.
 * 
 * All this is providing is the scaleId field, used to match to a corresponding
 * ID in the owning Scale to very quickly check for same-scale lineage so that
 * costly tests of conformity can be skipped when matching topologies for
 * mediation.
 * 
 * The (also abstract) Extent class adds more requirements for complex and
 * composite extents. Simple ones such as Cell, Shape and Period only adopt this
 * one.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractExtent implements IExtent {

	protected static class SelfIterator implements Iterator<ILocator> {

		ILocator self;

		public SelfIterator(ILocator locator) {
			this.self = locator;
		}

		@Override
		public boolean hasNext() {
			return self != null;
		}

		@Override
		public ILocator next() {
			ILocator ret = self;
			self = null;
			return ret;
		}
	}

	private transient String scaleId;
	protected transient Dimension baseDimension;
	protected IGeometry geometry;

	protected Extent locatedExtent = null;
	protected long[] locatedOffsets = null;
	protected long locatedLinearOffset = -1;
	private Double coverage;
	private Function<IExtent, Double> computeCoverage = null;

	@Override
	public double getCoverage() {
		return this.coverage == null ? (computeCoverage == null ? 1.0 : (this.coverage = computeCoverage.apply(this)))
				: this.coverage;
	}

	/**
	 * If we are in a situation where coverage may need to be computed, install the
	 * necessary logics here. Done this way to avoid expensive pre-computation when
	 * it's not needed.
	 * 
	 * @param function
	 * @return
	 */
	public IExtent withCoverageFunction(Function<IExtent, Double> function) {
		this.computeCoverage = function;
		return this;
	}
	
	/**
	 * Preset the coverage when it's economic to do so.
	 * @param coverage
	 * @return
	 */
	public IExtent withCoverage(double coverage) {
		this.coverage = coverage;
		return this;
	}

	/**
	 * The extent this locates, if any.
	 * 
	 * @return
	 */
	public IExtent getLocatedExtent() {
		return locatedExtent;
	}

	/**
	 * Located offsets wrt the dimensionality of the extent, or null.
	 * 
	 * @return
	 */
	public long[] getLocatedOffsets() {
		return locatedOffsets;
	}

	/**
	 * Linear located offset or -1
	 * 
	 * @return
	 */
	public long getLocatedOffset() {
		return locatedLinearOffset;
	}

	protected void setScaleId(String id) {
		this.scaleId = id;
	}

	public void setGeometry(IGeometry geometry) {
		this.geometry = geometry;
	}

	protected String getScaleId() {
		return this.scaleId;
	}

	boolean isOwnExtent(Scale scale) {
		return scale.getScaleId().equals(scaleId);
	}

	/**
	 * True if the i-th state of the topology correspond to a concrete subdivision
	 * where observations can be made. Determines the status of "data" vs. "no-data"
	 * for the state of an observation defined over this extent.
	 * 
	 * @param stateIndex
	 * @return whether there is an observable world at the given location.
	 */
	public abstract boolean isCovered(long stateIndex);

	public abstract boolean isEmpty();

	/**
	 * Return the n-th state of the ordered topology as a new extent with one state.
	 * 
	 * @param stateIndex
	 * @return a new extent with getValueCount() == 1.
	 */
	public abstract IExtent getExtent(long stateIndex);

	/**
	 * Return a double that describes the extent of this topological object. It
	 * should only be used to compare objects of the same type. Redundant with
	 * {@link IExtent#getStandardizedDimension()} but this is meant to compute fast.
	 *
	 * @return the covered extent
	 */
	public abstract double getCoveredExtent();

	/**
	 * Return the string rep for the {@link Dimension} this represents.
	 * 
	 * @return the encoded representation
	 */
	public abstract String encode();

	/**
	 * Merge only the extents, without regard for the grain or internal
	 * representation but ensuring that the covered extent calculations are correct.
	 * Used for quick coverage calculations.
	 * 
	 * @param other
	 * @return the merged extent
	 */
	public abstract IExtent mergeCoverage(IExtent other, LogicalConnector connector);

	/**
	 * Return the single-valued topological value that represents the total extent
	 * covered, ignoring the subdivisions.
	 * 
	 * @return the full, 1-dim extent.
	 */
	public abstract IExtent getExtent();

	/**
	 * All extents must be able to produce a deep copy of themselves.
	 * 
	 * @return a new extent identical to this.
	 */
	public abstract AbstractExtent copy();

	/**
	 * All extents must have a two-way street between k.IM code functions and
	 * themselves.
	 * 
	 * @return the k.IM function call specifying this extent.
	 */
	public abstract IServiceCall getKimSpecification();

	public void setDimension(Dimension dimension) {
		this.baseDimension = dimension;
	}

	/**
	 * Apply the default contextualization to this extent that will make it match
	 * the passed extent from the context observation, ensuring that any constraints
	 * from the passed annotation (from k.IM and potentially null) are honored.
	 * 
	 * @param other
	 * @param constraint
	 */
	protected abstract IExtent contextualizeTo(IExtent other, IAnnotation constraint);

}
