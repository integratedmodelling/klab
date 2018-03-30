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

import org.integratedmodelling.klab.api.data.utils.IPair;

public interface ITimeDuration extends Comparable<ITimeDuration> {

    /**
     * number of milliseconds in this duration. It might be a bit too implementation-specific, but for now I'm
     * leaving it as-is because it's a good interface to describe the one implementation that currently exists
     * (DurationValue)
     *
     * @return duration in milliseconds
     */
    long getMilliseconds();

    /**
     * Localize a duration to an extent starting at the current moment
     * using the same resolution that was implied in the generating
     * text. For example, if the duration was one year, localize to the
     * current year (jan 1st to dec 31st). Return the start and end points
     * of the extent.
     *
     * @return localization
     */
    IPair<ITimeInstant, ITimeInstant> localize();
}
