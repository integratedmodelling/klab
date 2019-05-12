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
package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.common.LogicalConnector;

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
public interface IExtent extends ILocator, ITopology<IExtent>, Iterable<IExtent>, IGeometry.Dimension {

    /**
     * Merge in another extent to complete what is incomplete in this one. This is
     * done recursively during resolution to establish the final scale for a
     * dataflow. Allows specifications with partially specified extents (where
     * {@link #isGeneric()} returns true) to inform the scale of the final
     * contextualization. This should build conformant extents, i.e. only 
     * offset mediation should be necessary to address either topology.
     * 
     * When another extent is merged into this and both specify extent and/or resolution,
     * our extent takes over the other's while resolution is negotiated to match the other's.
     * The only situation when our extent changes is when it needs to be adjusted to allow
     * the resolution to fit.
     * 
     * @param extent
     */
    public IExtent merge(IExtent extent);

    /**
     * Each extent must be able to return a worldview-dependent integer scale rank,
     * usable to constrain model retrieval to specific scales. In spatial extents
     * this corresponds to something like a "zoom level".
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
     * Return the simplest boundary that can be compared to another coming from 
     * an extent of the same type. This should be a "bounding box" that ignores
     * internal structure and shape.
     * 
     * @return the boundary.
     */
    IExtent getBoundingExtent();

    /**
     * Return a double that describes the extent of this topological object. It
     * should only be used to compare objects of the same type.
     *
     * @return the covered extent
     */
    double getCoveredExtent();

    /**
     * If this extent specifies a larger portion of the topology than the modeled
     * world contains, return a < 1.0 coverage. This can happen when the extent
     * semantics constrains the representation - e.g. regular spatial grids covering
     * more space than there actually is. Coverage = 0 should never happen as such
     * extents should not be returned by any function.
     *
     * @return coverage in the range (0 1]
     */
    double getCoverage();

    /**
     * Get a state mediator to the passed extent. If extent is incompatible return
     * null; if no mediation is needed, return an identity mediator, which all
     * implementations should provide.
     * 
     * @param extent
     *            the foreign extent to mediate to and from.
     * @return the configured mediator or null
     * @throw {@link IllegalArgumentException} if called improperly
     */
    public abstract IScaleMediator getMediator(IExtent extent);

    /** {@inheritDoc} */
    @Override
    IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how);

}
