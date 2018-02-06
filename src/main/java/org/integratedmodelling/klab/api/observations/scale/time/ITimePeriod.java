/*******************************************************************************
 *  Copyright (C) 2007, 2014:
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

/**
 * Authoritative type for expressing an anchored (specific start/end) duration of time.
 *
 * The semantics for observation over time dictates that states are OBSERVED at (i.e. read from) a given time,
 * whereas states are GENERATED for (i.e. written to) the instant immediately following the given time.
 * Put more plainly, one READS from the present, and WRITES to the future.
 *
 * @author luke
 *
 */
public interface ITimePeriod extends ITime {

    /**
     * whether or not the time period contains the given instant, using exclusive-start, inclusive-end
     * semantics.
     *
     * @param time
     * @return true if this contains time
     */
    public boolean contains(ITimeInstant time);

    /**
     * whether or not the time period contains the given instant (ms since Jan 1, 1970), using
     * exclusive-start, inclusive-end semantics.
     *
     * @param millisInstant
     * @return true if the instant specified is in this
     */
    public boolean contains(long millisInstant);

    /**
     * whether or not the time period ends before the instant, using exclusive-start, inclusive-end semantics.
     *
     * @param instant
     * @return true if this ends before instant
     */
    public boolean endsBefore(ITimeInstant instant);

    /**
     * whether or not the time period ends before the start instant of the other period, using
     * exclusive-start, inclusive-end semantics.
     *
     * @param other
     * @return true if this ends before other
     */
    public boolean endsBefore(ITime other);

    /**
     * whether or not the two time periods overlap, using exclusive-start, inclusive-end semantics.
     *
     * @param other
     * @return true if this overlaps other
     */
    public boolean overlaps(ITime other);

    /**
     * Return duration in milliseconds.
     * 
     * @return duration in ms
     */
    public long getMillis();

}
