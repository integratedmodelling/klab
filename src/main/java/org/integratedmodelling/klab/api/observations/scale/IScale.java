package org.integratedmodelling.klab.api.observations.scale;

import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;

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
   * Total number of extents available in this Scale. Note that there may be more extents than just space
   * and/or time. Read the non-existing documentation.
   *
   * @return the number of extents for this topology
   */
  int getExtentCount();

  /**
   * Return the list of extents ordered by contextualization priority.
   * 
   * @return the extents
   */
  List<IExtent> getExtents();
  
  /**
   * Get the extent that observes the passed domain concept, or null if it does not exist.
   * 
   * @param domainConcept
   * @return the extent that observes the passed concept
   */
  IExtent getExtent(IConcept domainConcept);

  /**
   * Return true only if he scale has > 0 extents and any of them is empty, so that the coverage of any
   * other scale can only be 0.
   *
   * @return true if scale cannot be the context for any observation.
   */
  boolean isEmpty();

  /**
   * Merge all common extents from the given scale, using the force parameter to define how the
   * extents are merged (see IExtent.merge). Extents in common are merged according to the passed
   * operator to compute the merged extent. The adopt parameter controls whether extents in the
   * passed scale that are not in the original one appear in the result. All extents in the original
   * scale will appear in the result.
   *
   * Must not modify the original scales.
   * 
   * @param scale
   * @param how
   * @param adopt
   *
   * @return a new merged scale
   * @throws KlabException
   */
  IScale merge(IScale scale, LogicalConnector how, boolean adopt) throws KlabException;
  
  /**
   * The scale implementation of {@link ILocator#at(ILocator)} always return a scale and 
   * can use an extent, other scale, or ITime.INITIALIZATION as locator.
   */
  @Override
  IScale at(ILocator locator);
  
  

}
