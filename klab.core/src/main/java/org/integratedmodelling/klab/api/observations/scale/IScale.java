package org.integratedmodelling.klab.api.observations.scale;

import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.LogicalConnector;

/**
 * 
 * @author ferdinando.villa
 *
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
   * Return a new scale merging all extents from the passed parameter. Extents in common are merged
   * according to how the merge is implemented, any others are added as they are.
   * <p>
   * Must not modify the original scales.
   * 
   * @param coverage can pass a scale or an extent.
   * @param how
   *
   * @return a new merged scale
   */
  @Override
  IScale merge(ITopologicallyComparable<?> other, LogicalConnector how);

  /**
   * The scale implementation of {@link ILocator#at(ILocator)} always return a scale and can use an
   * extent, other scale, or ITime.INITIALIZATION as locator.
   */
  @Override
  IScale at(ILocator locator);

  /**
   * Mimics {@link org.integratedmodelling.kim.api.data.IGeometry.Dimension#shape()} passing the
   * type of the desired dimension.
   * 
   * @param dimension the dimension we need the shape of
   * @return the shape of the passed dimension
   * @throws IllegalArgumentException if the dimension is not known in this scale
   */
  public long[] shape(Type dimension);

}
