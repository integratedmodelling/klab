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

import org.integratedmodelling.klab.api.collections.IPair;

/**
 * The Interface ITimeDuration.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ITimeDuration extends Comparable<ITimeDuration> {

	/**
	 * Return a new period anchored to the passed instant.
	 * 
	 * @param instant
	 * @return
	 */
	ITimeDuration anchor(ITimeInstant instant);

	/**
	 * True if the period is anchored to a start time.
	 * 
	 * @return
	 */
	boolean isAnchored();

	/**
	 * Get start time, or null if not anchored.
	 * 
	 * @return
	 */
	ITimeInstant getStart();

	/**
	 * Return the natural resolution for something whose time is expressed in this
	 * range of duration.
	 * 
	 * @return
	 */
	ITime.Resolution.Type getResolution();

	/**
	 * If false, this is defined in a logical way but the actual duration is
	 * contextual to the anchored period. So calling {@link #getMilliseconds()}
	 * throws an exception if the period isn't anchored. This is the case of months
	 * and anything that has months in its lineage.
	 * 
	 * @return
	 */
	boolean isRegular();

	/**
	 * number of milliseconds in this duration. It might be a bit too
	 * implementation-specific, but for now I'm leaving it as-is because it's a good
	 * interface to describe the one implementation that currently exists
	 * (DurationValue)
	 *
	 * @return duration in milliseconds
	 * @throws IllegalStateException if duration is irregular and not anchored.
	 */
	long getMilliseconds();

	/**
	 * number of milliseconds of the maximum duration if irregular, the same as
	 * getMilliseconds() otherwise.
	 *
	 * @return max duration in milliseconds
	 * @throws IllegalStateException if duration is irregular and not anchored.
	 */
	long getMaxMilliseconds();

	/**
	 * number of milliseconds of the maximum common divisor of the possible
	 * durations if irregular, the same as getMilliseconds() otherwise.
	 *
	 * @return largest common duration in milliseconds
	 * @throws IllegalStateException if duration is irregular and not anchored.
	 */
	long getCommonDivisorMilliseconds();

	/**
	 * Localize a duration to an extent starting at the current moment using the
	 * same resolution that was implied in the generating text. For example, if the
	 * duration was one year, localize to the current year (jan 1st to dec 31st).
	 * Return the start and end points of the extent.
	 *
	 * @return localization
	 */
	IPair<ITimeInstant, ITimeInstant> localize();

	/**
	 * True for a no-duration duration.
	 * 
	 * @return
	 */
	boolean isEmpty();
	
	/**
	 * Return a re-parseable specification, either in milliseconds or (if irregular) 
	 * as a Kim unit specification.
	 * @return
	 */
	String getSpecification();

}
