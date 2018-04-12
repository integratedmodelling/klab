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
package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;

/**
 * IObservationTopology refers to a set of concomitant observations of abstracts such as space and
 * time, which constrain any remaining observations. It provides a common base API for both IScale
 * and ITransition; the latter has multiplicity 1 in time from the point of view of an observer,
 * while the former may have higher order. A IScale can be therefore seen as a sequence of
 * ITransitions. A single ITransition may map to a IScale for a different observer.
 * 
 * @author Ferd
 */
public abstract interface IObservationTopology extends Iterable<IExtent> {

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
   * Total number of extents available in this Scale. Note that there may be more extents than just
   * space and/or time. Read the non-existing documentation.
   *
   * @return the number of extents for this topology
   */
  int getExtentCount();

  /**
   * Get the n-th extent. Index reflects the scale's inherent sorting and must be stable across
   * instances, i.e. if two scales have the same extents, they must be in the same order, and this
   * also applies to scales that are subsets.
   * 
   * @param index
   * @return the extent in position {@code index}
   */
  IExtent getExtent(int index);

  /**
   * Get the extent that observes the passed domain concept, or null if it does not exist.
   * 
   * @param domainConcept
   * @return the extent that observes the passed concept
   */
  IExtent getExtent(IConcept domainConcept);

  /**
   * Return true only if he scale has > 0 extents and any of them is empty, so that the coverage of
   * any other scale can only be 0.
   *
   * @return true if scale cannot be the context for any observation.
   */
  boolean isEmpty();

}
