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
package org.integratedmodelling.klab.api.provenance;

import java.util.Optional;

import org.integratedmodelling.klab.api.resolution.IResolutionScope;

/**
 * Activity (process).
 * 
 * @author Ferd
 * @version $Id: $Id
 */
public interface IActivity extends IProvenance.Node {

	/**
	 * A classification of the observation activity (odo:Description) that can
	 * produce an observation of this observable. Encodes the same classification in
	 * ODO-IM. The descriptions specialize {@link IResolutionScope#Mode}, which is
	 * captured by exposing its correspondent value.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	enum Description {

		/**
		 * The observation activity that produces a countable object. Acknowledgement is
		 * a special case of instantiation, limited to a subject and performed on a fiat
		 * basis (in k.IM through an <code>observe</code> statement).
		 */
		INSTANTIATION(IResolutionScope.Mode.INSTANTIATION),
		/**
		 * The observation activity that produces a configuration (aka EMERGENCE) - the
		 * instantiation of a configuration.
		 */
		DETECTION(IResolutionScope.Mode.INSTANTIATION),
		/**
		 * The observation activity that produces a dynamic account of a process
		 */
		SIMULATION(IResolutionScope.Mode.RESOLUTION),
		/**
		 * The observation activity that produces a numeric quality
		 */
		QUANTIFICATION(IResolutionScope.Mode.RESOLUTION),
		/**
		 * The observation activity that produces a categorical quality (observes a
		 * conceptual category) over a context.
		 */
		CATEGORIZATION(IResolutionScope.Mode.RESOLUTION),
		/**
		 * The observation activity that produces a boolean quality (presence/absence)
		 */
		VERIFICATION(IResolutionScope.Mode.RESOLUTION),
		/**
		 * The observation activity that attributes a trait or role to another
		 * observation (if it is a quality, it may transform its values). Equivalent to
		 * INSTANTIATION of a concrete t/a given the abstract form and an inherent
		 * observable.
		 */
		CLASSIFICATION(IResolutionScope.Mode.INSTANTIATION),
		/**
		 * The resolution activity of a concrete trait or role that has been previously
		 * attributed to an observation.
		 */
		CHARACTERIZATION(IResolutionScope.Mode.RESOLUTION);

		IResolutionScope.Mode mode;

		Description(IResolutionScope.Mode mode) {
			this.mode = mode;
		}
	}

	/**
	 * <p>
	 * getStart.
	 * </p>
	 *
	 * @return a long.
	 */
	long getStart();

	/**
	 * <p>
	 * getEnd.
	 * </p>
	 *
	 * @return a long.
	 */
	long getEnd();

	/**
	 * If the action was caused by another action, return the action that caused it.
	 *
	 * @return a {@link java.util.Optional} object.
	 */
	Optional<IActivity> getCause();

	/**
	 * Actions are made by agents.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.provenance.IAgent} object.
	 */
	IAgent getAgent();

}
