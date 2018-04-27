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

import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.LogicalConnector;

/**
 * The Interface IScale.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IScale extends ILocator, Iterable<IScale>, IGeometry, ITopology<IScale> {

  /**
   * We deal with space and time in all natural systems, so we expose these to ease API use.
   *
   * @return the space, or null
   */
  ISpace getSpace();

  /**
   * We deal with space and time in all natural systems, so we expose these to ease API use.
   *
   * @return the time, or null
   */
  ITime getTime();

  /**
   * True if we have time and the time topology determines more than a single state. It's also in
   * IObservation, but it's convenient to duplicate it here too.
   *
   * @return true if distributed in time
   */
  boolean isTemporallyDistributed();

  /**
   * True if we have space and the space topology determines more than a single state. It's also in
   * IObservation, but it's convenient to duplicate it here too.
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
  List<IExtent> getExtents();

  /**
   * Return true only if he scale has > 0 extents and any of them is empty, so that the coverage of
   * any other scale can only be 0.
   *
   * @return true if scale cannot be the context for any observation.
   */
  boolean isEmpty();

  /**
   * {@inheritDoc}
   *
   * Return a new scale merging all extents from the passed parameter. Extents in common are merged
   * according to how the merge is implemented, any others are added as they are.
   * <p>
   * Must not modify the original scales.
   */
  @Override
  IScale merge(ITopologicallyComparable<?> other, LogicalConnector how);

  /**
   * {@inheritDoc}
   *
   * The scale implementation of {@link ILocator#at(ILocator)} always return a scale and can use an
   * extent, other scale, or ITime.INITIALIZATION as locator.
   */
  @Override
  IScale at(ILocator locator);

  /**
   * Mimics {@link org.integratedmodelling.klab.api.data.IGeometry.Dimension#shape()} passing the
   * type of the desired dimension.
   *
   * @param dimension the dimension we need the shape of
   * @return the shape of the passed dimension
   * @throws java.lang.IllegalArgumentException if the dimension is not known in this scale
   */
  public long[] shape(Type dimension);

}
