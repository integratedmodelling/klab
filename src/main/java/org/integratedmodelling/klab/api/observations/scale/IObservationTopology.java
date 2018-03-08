/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.observations.scale;

import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;

/**
 * IObservationTopology refers to a set of concomitant observations of abstracts such as space
 * and time, which constrain any remaining observations. It provides a common base API for both
 * IScale and ITransition; the latter has multiplicity 1 from the point of view of an observer, while
 * the former may have higher order. A IScale can be therefore seen as a sequence of ITransitions. A
 * single ITransition may map to a IScale for a different observer.
 * 
 * @author Ferd
 */
public abstract interface IObservationTopology extends IGeometry, ITopology<IScale> {

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

}
