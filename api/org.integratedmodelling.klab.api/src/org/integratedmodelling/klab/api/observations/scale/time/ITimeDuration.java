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
package org.integratedmodelling.klab.api.observations.scale.time;

import org.integratedmodelling.klab.api.data.utils.IPair;

/**
 * The Interface ITimeDuration.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
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
