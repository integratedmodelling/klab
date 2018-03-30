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

import java.util.Collection;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface ITemporalSeries<T> extends ITime {
    /**
     * return the item which is valid for the given time, or null if none qualifies.
     * @param time 
     * @return time
     */
    public T getAtTime(ITimeInstant time);

    /**
     * get the earliest temporal scale item with a start time AFTER or EQUAL TO the given time
     * @param time 
     * @return following time
     */
    public T getFollowing(ITimeInstant time);

    /**
     * get the latest temporal scale item with an end time BEFORE the given time
     * @param time 
     * @return prior time
     */
    public T getPrior(ITimeInstant time);

    public T getFirst();

    public T getLast();

    /**
     * return a collection of the items which are valid at any time during timePeriod, or an empty collection
     * if none qualifies.
     *
     * @param timePeriod
     * @return overlapping extents
     */
    public Collection<T> getOverlapping(ITimePeriod timePeriod);

    public void put(ITimeInstant start, ITimeInstant end, T item) throws KlabException;

    public void put(ITimePeriod timePeriod, T item);

    /**
     * shorten the time period of the item which contains the splice time
     *
     * @param spliceTime
     * @return shortened period
     * @throws KlabException
     */
    public ITimePeriod shorten(ITimeInstant spliceTime) throws KlabException;

    public void remove(ITimeInstant time);

    public ITimePeriod bisect(ITimeInstant spliceTime, T object) throws KlabException;
}
