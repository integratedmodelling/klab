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
package org.integratedmodelling.klab.api.knowledge.observation.scale.time;

// TODO: Auto-generated Javadoc
/**
 * Authoritative type for expressing an anchored (specific start/end) duration of time.
 *
 * The semantics for observation over time dictates that states are OBSERVED at (i.e. read from) a given time,
 * whereas states are GENERATED for (i.e. written to) the instant immediately following the given time.
 * Put more plainly, one READS from the present, and WRITES to the future.
 *
 * @author luke
 * @version $Id: $Id
 */
public interface KTimePeriod extends KTime {

    /**
     * whether or not the time period contains the given instant, using exclusive-start,
     * inclusive-end semantics.
     *
     * @param time the time
     * @return true if this contains time
     */
    public boolean contains(KTimeInstant time);

    /**
     * whether or not the time period contains the given instant (ms since Jan 1, 1970), using
     * exclusive-start, inclusive-end semantics.
     *
     * @param millisInstant the millis instant
     * @return true if the instant specified is in this
     */
    public boolean contains(long millisInstant);

    /**
     * whether or not the time period ends before the instant, using exclusive-start, inclusive-end
     * semantics.
     *
     * @param instant the instant
     * @return true if this ends before instant
     */
    public boolean endsBefore(KTimeInstant instant);

    /**
     * whether or not the time period ends before the start instant of the other period, using
     * exclusive-start, inclusive-end semantics.
     *
     * @param other the other
     * @return true if this ends before other
     */
    public boolean endsBefore(KTime other);

    /**
     * whether or not the two time periods overlap, using exclusive-start, inclusive-end semantics.
     *
     * @param other the other
     * @return true if this overlaps other
     */
    public boolean overlaps(KTime other);

    /**
     * Return duration in milliseconds.
     *
     * @return duration in ms
     */
    public long getMillis();

}
