/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge.observation.scale;

import java.util.List;

import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KLocator;
import org.integratedmodelling.klab.api.knowledge.observation.scale.space.KSpace;
import org.integratedmodelling.klab.api.knowledge.observation.scale.time.KTime;
import org.integratedmodelling.klab.api.lang.LogicalConnector;

/**
 * The Interface IScale.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface KScale extends KLocator, KGeometry, KTopology<KScale> {

    /**
     * We deal with space and time in all natural systems, so we expose these to ease API use.
     *
     * @return the space, or null
     */
    KSpace getSpace();

    /**
     * We deal with space and time in all natural systems, so we expose these to ease API use.
     *
     * @return the time, or null
     */
    KTime getTime();

    /**
     * True if we have time and the time topology determines more than a single state. It's also in
     * IObservation, but it's convenient to duplicate it here too.
     *
     * @return true if distributed in time
     */
    boolean isTemporallyDistributed();

    /**
     * True if we have space and the space topology determines more than a single state. It's also
     * in IObservation, but it's convenient to duplicate it here too.
     *
     * @return true if distributed in space
     */
    boolean isSpatiallyDistributed();

    /**
     * Total number of extents available in this Scale. Note that in principle there may be more
     * extents than just space and/or time, although this is not supported at the moment. Read the
     * non-existing documentation.
     *
     * @return the number of extents for this topology
     */
    int getExtentCount();

    /**
     * Return the list of extents, ordered by contextualization priority (time, if present, will
     * always be first).
     *
     * @return the extents
     */
    List<KExtent> getExtents();

    /**
     * Return true only if he scale has > 0 extents and any of them is empty, so that the coverage
     * of any other scale can only be 0.
     *
     * @return true if scale cannot be the context for any observation.
     */
    boolean isEmpty();

    /**
     * Merge in another scale (possibly limited to specified extents) to return a new scale that
     * best represents the common traits in both, seeing the passed scale as a constraint. Used to
     * build the "common" scale of a dataflow before contextualization, where the passed scale is
     * that of the desired context and this is the scale of a model or computation used in it.
     * 
     * In detail, for each extent of the outgoing scale:
     * <ul>
     * <li>If this does not have an extent that the passed scale has, the result should adopt it as
     * is.</li>
     * <li>If this has an extent that the passed scale does not have, the result should <em>not</em>
     * contain it.</li>
     * <li>Boundaries should be inherited from the passed scale. The result can shrink but it cannot
     * grow beyond the common boundaries.</li>
     * <li>If this is distributed in the common extents, the result should stay distributed. If the
     * incoming scale is distributed and this is not, the result should become distributed. If both
     * are distributed, choices of representation may need to be made: the result's representation
     * should be the incoming one as much as possible, to prevent costly mediations.</li>
     * <li>If the result is distributed, the resolution should be our resolution if this was
     * distributed, or the incoming resolution if not.</li>
     * </ul>
     * 
     * @param scale the scale to merge in
     * @param dimensions the dimension on which to perform the merge; if no dimensions are passed,
     *        merge all dimensions
     */
    public KScale mergeContext(KScale scale, Dimension.Type... dimensions);

    /**
     * {@inheritDoc}
     *
     * Return a new scale merging all extents from the passed parameter. The extents of the merged
     * in scale are authoritative in terms of extent; granularity is negotiated as defined by each
     * extent individually.
     * <p>
     * Extents in common are merged according to how the merge is implemented; any extents that are
     * in one scale and not the other are left in the returned scale as they are.
     * <p>
     * Must not modify the original scales.
     */
    @Override
    KScale merge(KTopologicallyComparable<?> other, LogicalConnector how, MergingOption... options);

    /**
     * Mimics {@link org.integratedmodelling.klab.api.data.KGeometry.Dimension#shape()} passing the
     * type of the desired dimension.
     *
     * @param dimension the dimension we need the shape of
     * @return the shape of the passed dimension
     * @throws java.lang.IllegalArgumentException if the dimension is not known in this scale
     */
    long[] shape(Dimension.Type dimension);

    /**
     * Return the scale at the beginning of time, or the scale itself if there is no time at all.
     */
    KScale initialization();

    /**
     * Return the scale after the end of contextualization. This scale is not produced by the scale
     * iterator, and is used only for scheduling.
     * 
     * @return
     */
    KScale termination();

    /**
     * Return a new scale without the passed dimension.
     * 
     * @param dimension
     * @return
     */
    KScale without(KGeometry.Dimension.Type dimension);

    /**
     * The at method mandatorily returns a scale.
     */
    @Override
    KScale at(Object... dimensions);

    /**
     * Return whether the passed dimensions are regular, i.e. the extent in each sub-extent is the
     * same. If no dimensions are passed, check them all. If a non-existent dimension is passed,
     * return true for it.
     * 
     * @param dimensions
     * @return
     */
    boolean isRegular(KGeometry.Dimension.Type... dimensions);

    /**
     * Return a scale optimized for iterating along the dimensions passed here (use the same call
     * logics as in {@link KGeometry#at(Object...)}}. At worst the implementation can return the
     * same scale if the iterated class is compatible, but ideally it should return a wrapper that
     * makes iteration as fast as possible. Ensure that the returned iterator is thread-safe (i.e.,
     * if objects are reused ensure they are thread local).
     * <p>
     * Dimensions that are not mentioned in the parameters should be removed from the offsets if the
     * desired locator is an offset and their multiplicity is one.
     * <p>
     * Example: to scan a scale along a spatial grid using simple <x,y> offsets when it is known
     * that space is gridded and time is not there or is one-dimensional:
     * 
     * <pre>
     * for (Offset offset : scale.scan(Offset.class, IGrid.class)) {
     * 	... use spatial-only offset
     * }
     * </pre>
     * 
     * This will return offsets with only two dimensions (x,y for the grid coordinates) and ensure
     * that the grid is scanned in the quickest way possible. An even more API-friendly way (but
     * potentially slower due to retrieving the cell) would be
     * 
     * <pre>
     * for (Cell cell : scale.scan(Cell.class, IGrid.class)) {
     * 	... use cell as is
     * }
     * </pre>
     * 
     * All these are optimized versions of
     * 
     * <pre>
     * for (ILocator locator : scale) {
     * 	...
     * }
     * </pre>
     * 
     * which would need a call to locator.as(...) to obtain the desired info and would therefore
     * iterate more slowly due to the reinterpretation of the offsets at each call to next().
     * 
     * @return the desired iterable
     * @throw {@link IllegalArgumentException} if the parameters cannot be understood or honored.
     */
    <T extends KLocator> Iterable<T> scan(Class<T> desiredLocatorClass, Object... dimensionIdentifiers);

    /**
     * Return the same scale but with multiplicity 1 and all extents collapsed to their containing
     * extent.
     * 
     * @param dimensions select the dimensions to collapse. Pass none to collapse everything.
     * 
     * @return
     */
    KScale collapse(Dimension.Type... dimensions);

}
