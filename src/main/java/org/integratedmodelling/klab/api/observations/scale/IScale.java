package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IScale extends ILocator, IObservationTopology, Iterable<IScale> {

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

}
