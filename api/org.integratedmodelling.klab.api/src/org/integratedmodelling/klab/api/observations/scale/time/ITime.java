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
 * Time, as seen by k.LAB when the default contextualizer time() is used.
 * <p>
 * Legal time extents are:
 *
 * <ul>
 * <li>Generic with resolution and no specific interval: dependencies will be
 * matched only by resolution and no temporal mediation will be possible.</li>
 * <li>Generic with resolution and interval: dependencies will be matched by
 * resolution and closeness to the interval; mediation will be possible;
 * resources having time <i>closer</i> to the interval will be chosen even if
 * they are not within the interval. This will be the default time extent for
 * k.Explorer users, tuned in on the current year.</li>
 * <li>Specific with resolution and interval: dependencies will be matched by
 * resolution and interval; mediation will be possible; resources having time
 * not within the interval will <i>not</i> be chosen even if they are.</li>
 * <li>Regular grid must have interval and step: dependencies will be matched by
 * resolution and interval, choosing "moving" dependencies over
 * initialization-only ones; mediation will be possible. The end of period may
 * remain unspecified for endless process simulations.</li>
 * <li>Irregular grid has steps that follow a resource and may have individually
 * different duration. Any use of this is likely to involve complex and
 * potentially costly mediations.</li>
 * <li>Event time is an irregular grid aligned with a source of events. A
 * transition is generated per each event.</li>
 * <li>Real time is a grid aligned with the current time. Start time is now if
 * not specified. End time, if specified, must be in the actual future.</li>
 * </ul>
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

			MILLENNIUM(0), CENTURY(1), DECADE(2), YEAR(3), MONTH(4), WEEK(5), DAY(6), HOUR(7), MINUTE(8), SECOND(9),
			MILLISECOND(10);

			int rank;

			Type(int rank) {
				this.rank = rank;
			}

			public int getRank() {
				return rank;
			}

			public long getMilliseconds() {
				switch (this) {
				case MILLISECOND:
					return 1;
				case SECOND:
					return 1000;
				case MINUTE:
					return 1000l * 60l;
				case HOUR:
					return 1000l * 60l * 60l;
				case DAY:
					return 1000l * 60l * 60l * 24l;
				case WEEK:
					return 1000l * 60l * 60l * 24l * 7l;
				case MONTH:
					return 1000l * 60l * 60l * 24l * 30;
				case YEAR:
					return 1000l * 60l * 60l * 24l * 365l;
				case DECADE:
					return 1000l * 60l * 60l * 24l * 365l * 10l;
				case CENTURY:
					return 1000l * 60l * 60l * 24l * 365l * 100l;
				case MILLENNIUM:
					return 1000l * 60l * 60l * 24l * 365l * 1000l;
				default:
					break;

				}
				return 0;
			}

		}

		Type getType();

		double getMultiplier();

	}

	static public enum Type {

		/**
		 * Generic focus on a period without temporally locating it but specifying the
		 * length of the period of interest.
		 */
		GENERIC,

		/**
		 * Specific time period of any lenght, single multiplicity
		 */
		SPECIFIC,

		/**
		 * Time grid, multiplicity N.
		 */
		GRID,

		/**
		 * Real time, which is necessarily a grid, potentially irregular, multiplicity
		 * may be infinite if end is undefined.
		 */
		REAL
	}

	/**
	 * The empty, non-descript initialization locator refers to the extent before
	 * any extent exists. Match with == only.
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
	 * Resolution of time observation according to this extent.
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
