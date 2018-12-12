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

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

/**
 * The Interface ITime.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ITime extends IExtent {

	/** Constant <code>MIN_SCALE_RANK=0</code> */
	int MIN_SCALE_RANK = 0;
	/** Constant <code>MAX_SCALE_RANK=19</code> */
	int MAX_SCALE_RANK = 19;

	static public interface Resolution {

		public enum Type {

			MILLENNIUM(0), CENTURY(1), DECADE(2), YEAR(3), MONTH(4), WEEK(5), DAY(6), HOUR(7), MINUTE(8), SECOND(
					9), MILLISECOND(10), NANOSECOND(11);
			
			int rank;

			Type(int rank) {
				this.rank = rank;
			}
			public int getRank() {
				return rank;
			}

		}

		Type getType();

		double getMultiplier();

	}

	static public enum Type {
		/**
		 * Should be used rarely: generic focus on a period without temporally locating
		 * it. In every respect like using 'during' semantics, but allowing fuzzy
		 * matches by period (e.g. closer to certain months) for resolution.
		 */
		GENERIC,
		/**
		 * Specific time period of any lenght, single multiplicity
		 */
		SPECIFIC,
		/**
		 * Time grid.
		 */
		GRID
	}

	/**
	 * The empty, non-descript initialization locator refers to the extent before
	 * any extent exists.
	 */
	ILocator INITIALIZATION = new ILocator() {

		@Override
		public ILocator at(ILocator locator) {
			return this;
		}

		@Override
		public <T extends ILocator> T as(Class<T> cls) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	/**
	 * {@inheritDoc}
	 *
	 * Overriding to require that the collapsed type is ITimePeriod. This allows
	 * simpler coding against the API, and is the most logical way to enforce that
	 * getValueCount() == 1.
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
	 * FIXME this should only be defined if time is a grid - as done in
	 * ISpatialExtent (use a Grid object).
	 *
	 * @return step if any
	 */
	ITimeDuration getStep();

	/**
	 * Resolution of time observation according to this extent. TODO needs
	 * multiplier
	 * 
	 * @return
	 */
	Resolution getResolution();

	/**
	 * {@inheritDoc}
	 *
	 * The time implementation of {@link ILocator#at(ILocator)} always return a time
	 * and can only use another time as locator.
	 */
	@Override
	ITime at(ILocator locator);

}
