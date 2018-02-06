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
package org.integratedmodelling.klab.api.observations.scale.time;

import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface ITime extends IExtent {

    int MIN_SCALE_RANK = 0;
    int MAX_SCALE_RANK = 19;

    
    @Override
    ITime getExtent();

    /**
     * Overriding to constrain the result type further (from IExtent to ITemporalExtent)
     */
    @Override
    ITime getExtent(int stateIndex);

    /**
     * Overriding to require that the collapsed type is ITimePeriod. This allows simpler coding against the API,
     * and is the most logical way to enforce that getValueCount() == 1.
     */
    @Override
    ITimePeriod collapse();

    /**
     * May be null in partially specified extents.
     * 
     * @return start time
     */
    ITimeInstant getStart();

    /**
     * May be null in partially specified extents.
     * 
     * @return end time
     */
    ITimeInstant getEnd();

    /**
     * If multiplicity is 1, return the whole temporal extent.
     * 
     * FIXME this should only be defined if time is a grid - as done in ISpatialExtent (use a Grid object).
     * 
     * @return step if any
     */
    ITimeDuration getStep();

    /**
     * Get the transition between time i and the next.
     * 
     * @param i
     * @return
     */
    ITransition getTransition(int i);
    
}
