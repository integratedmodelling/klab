package org.integratedmodelling.klab.api.knowledge.observation.scale.time;
///*
// * This file is part of k.LAB.
// * 
// * k.LAB is free software: you can redistribute it and/or modify
// * it under the terms of the Affero GNU General Public License as published
// * by the Free Software Foundation, either version 3 of the License,
// * or (at your option) any later version.
// *
// * A copy of the GNU Affero General Public License is distributed in the root
// * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
// * see <http://www.gnu.org/licenses/>.
// * 
// * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
// * in author tags. All rights reserved.
// */
//package org.integratedmodelling.klab.api.observations.scale.time;
//
//import java.util.Collection;
//import org.integratedmodelling.klab.exceptions.KlabException;
//
//// TODO: Auto-generated Javadoc
///**
// * The Interface ITemporalSeries.
// *
// * @author ferdinando.villa
// * @version $Id: $Id
// * @param <T> the generic type
// */
//public interface ITemporalSeries<T> extends ITime {
//    
//    /**
//     * return the item which is valid for the given time, or null if none qualifies.
//     *
//     * @param time the time
//     * @return time
//     */
//    public T getAtTime(ITimeInstant time);
//
//    /**
//     * get the earliest temporal scale item with a start time AFTER or EQUAL TO the given time.
//     *
//     * @param time the time
//     * @return following time
//     */
//    public T getFollowing(ITimeInstant time);
//
//    /**
//     * get the latest temporal scale item with an end time BEFORE the given time.
//     *
//     * @param time the time
//     * @return prior time
//     */
//    public T getPrior(ITimeInstant time);
//
//    /**
//     * Gets the first.
//     *
//     * @return the first
//     */
//    public T getFirst();
//
//    /**
//     * Gets the last.
//     *
//     * @return the last
//     */
//    public T getLast();
//
//    /**
//     * return a collection of the items which are valid at any time during timePeriod, or an empty
//     * collection if none qualifies.
//     *
//     * @param timePeriod the time period
//     * @return overlapping extents
//     */
//    public Collection<T> getOverlapping(ITimePeriod timePeriod);
//
//    /**
//     * Put.
//     *
//     * @param start the start
//     * @param end the end
//     * @param item the item
//     * @throws KlabException the klab exception
//     */
//    public void put(ITimeInstant start, ITimeInstant end, T item) throws KlabException;
//
//    /**
//     * Put.
//     *
//     * @param timePeriod the time period
//     * @param item the item
//     */
//    public void put(ITimePeriod timePeriod, T item);
//
//    /**
//     * shorten the time period of the item which contains the splice time.
//     *
//     * @param spliceTime the splice time
//     * @return shortened period
//     * @throws KlabException the klab exception
//     */
//    public ITimePeriod shorten(ITimeInstant spliceTime) throws KlabException;
//
//    /**
//     * Removes the.
//     *
//     * @param time the time
//     */
//    public void remove(ITimeInstant time);
//
//    /**
//     * Bisect.
//     *
//     * @param spliceTime the splice time
//     * @param object the object
//     * @return the i time period
//     * @throws KlabException the klab exception
//     */
//    public ITimePeriod bisect(ITimeInstant spliceTime, T object) throws KlabException;
//}
