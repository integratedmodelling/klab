/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge.observation.scale;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.geometry.IGeometry;
import org.integratedmodelling.klab.api.geometry.IGeometry.Dimension;
import org.integratedmodelling.klab.api.geometry.ILocator;
import org.integratedmodelling.klab.api.lang.LogicalConnector;

/**
 * A {@code IExtent} is a semantically aware {@link Dimension geometry
 * dimension} that represents an observation of the topology it describes.
 * {@code IExtent}s make up the dimensions of the semantically aware
 * {@link org.integratedmodelling.klab.api.data.IGeometry} represented by
 * {@link org.integratedmodelling.klab.api.observations.scale.IScale}.
 *
 * In a {@code IExtent}, the {{@link #size()} will never return
 * {IGeometry#UNDEFINED} and the shape returned by {{@link #shape()} will never
 * contain undefined values.
 *
 * {@code IExtent}s can be used as {@link ILocator locators} to address the
 * value space of observations.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IExtent extends ILocator, ITopology<IExtent>, IGeometry.Dimension {

	/**
	 * Merge in another extent from the overall context, producing an extent that is
	 * complete and conformant (this one may be partially specified) ready for
	 * contextualization in it. This is called during resolution to establish the
	 * final scale of contextualization for actuators in a dataflow. Allows
	 * specifications with partially specified extents (where {@link #isGeneric()}
	 * returns true) to inform the scale of the final contextualization. This should
	 * build conformant extents, i.e. only offset mediation should be necessary to
	 * address either topology.
	 * <p>
	 * The logic to produce the result extent should be:
	 * <ul>
	 * <li>Boundaries should be inherited from the passed extent, compatibly with
	 * the receiving boundaries. The result can shrink but it cannot grow beyond the
	 * common boundaries.</li>
	 * <li>If this is distributed, the result should stay distributed. If the
	 * incoming extent is distributed and this is not, the result should become
	 * distributed. If both are distributed, choices of representation may need to
	 * be made: the result's representation should remain the incoming one as much
	 * as possible, to prevent costly mediations.</li>
	 * <li>If the result is distributed, the resolution should be our resolution if
	 * this was distributed, or the incoming resolution if not.</li>
	 * </ul>
	 * 
	 * @param extent an extent of the same dimension type as ours (not necessarily
	 *               the same class, although usually implementing the same extent
	 *               interface) from the prospective contextualization scale.
	 */
	IExtent mergeContext(IExtent extent);

	/**
	 * Locate the extent and return another with the original located extent and
	 * offsets set in. Can be passed another extent (e.g. a point to locate a cell
	 * in a grid space), one or more integer locators, a time period, or anything
	 * that can be understood by the extent.
	 * 
	 * @param locator
	 * @return the extent, or null if location is impossible.
	 */
	IExtent at(Object... locators);

	/**
	 * Each extent must be able to return a worldview-dependent integer scale rank,
	 * usable to constrain model retrieval to specific scales. In spatial extents
	 * this corresponds to something like a "zoom level". The worldview establishes
	 * the scale for the ranking; otherwise, no assumptions are made on the value
	 * except that higher values correspond to smaller extents.
	 *
	 * The worldview defines this using numeric restrictions on the data property
	 * used to annotate scale constraints and establishes the range and granularity
	 * for the ranking.
	 *
	 * @return an integer summarizing the extent's size within the range covered by
	 *         the worldview
	 */
	int getScaleRank();

	/**
	 * Collapse the multiplicity and return the extent that represents the full
	 * extent of our topology in one single state. This extent may not be of the
	 * same class.
	 *
	 * @return a new extent with size() == 1.
	 */
	IExtent collapse();

	/**
	 * Return the simplest boundary that can be compared to another coming from an
	 * extent of the same type. This should be a "bounding box" that ignores
	 * internal structure and shape and behaves with optimal efficiency when merged
	 * with others.
	 * 
	 * @return the boundary.
	 */
	IExtent getBoundingExtent();

	/**
	 * Return the dimensional coverage in the passed unit, which must be compatible.
	 * 
	 * @param unit
	 * @return
	 */
	double getDimensionSize(IUnit unit);

	/**
	 * Return the standardized (SI) dimension of the extent at the passed locator
	 * along with the unit it's in.
	 * 
	 * @return
	 */
	Pair<Double, IUnit> getStandardizedDimension(ILocator locator);

	/**
	 * If this extent specifies a larger portion of the topology than the modeled
	 * world contains, return a < 1.0 coverage. This can happen when the extent
	 * semantics constrains the representation - e.g. regular spatial grids covering
	 * more space than there actually is. Coverage = 0 should never happen as
	 * extents with no coverage can't provide topology for an observation, and as
	 * such should not be returned by any function.
	 *
	 * @return coverage in the range (0 1]
	 */
	double getCoverage();

	/**
	 * Get a state mediator to the passed extent. If extent is incompatible return
	 * null; if no mediation is needed, return an identity mediator, which all
	 * implementations should provide.
	 * 
	 * @param extent the foreign extent to mediate to and from.
	 * @return the configured mediator or null
	 * @throw {@link IllegalArgumentException} if called improperly
	 */
	public abstract IScaleMediator getMediator(IExtent extent);

	/** {@inheritDoc} */
	@Override
	IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how, MergingOption...options);

	/**
	 * Return the n-th state of the ordered topology as a new extent with one state.
	 * 
	 * @param stateIndex must be between 0 and {@link #size()}, exclusive.
	 * @return a new extent with getValueCount() == 1, or this if it is 1-sized and
	 *         0 is passed.
	 */
	IExtent getExtent(long stateIndex);

}
