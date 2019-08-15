package org.integratedmodelling.klab.scale;

import java.util.Iterator;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.common.Geometry.DimensionTarget;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

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
	 * @return
	 */
	public long[] getLocatedOffsets() {
		return locatedOffsets;
	}
	
	/**
	 * Linear located offset or -1
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

	public abstract boolean isEmpty();

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

}
