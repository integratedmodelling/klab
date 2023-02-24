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

import org.integratedmodelling.klab.api.knowledge.observation.scale.time.KTime;

/**
 * Activity (process). Primary processes produce artifacts. Secondary processes
 * (after creation) may modify them. Activities in k.LAB represent each
 * execution of an actuator, which represents a IPlan (part of the overall plan
 * that is the IActuator).
 * 
 * @author Ferd
 * @version $Id: $Id
 */
public interface KActivity extends KProvenance.Node {

	/**
	 * A classification of the primary observation activity (odo:Description) that
	 * can produce an observation of this observable. Encodes the same
	 * classification in ODO-IM. The descriptions specialize
	 * {@link IResolutionScope#Mode}, which is captured by exposing its
	 * correspondent value.
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
		INSTANTIATION(true),
		/**
		 * The observation activity that produces a configuration (aka EMERGENCE) - the
		 * instantiation of a configuration.
		 */
		DETECTION(true),
		/**
		 * The observation activity that produces a dynamic account of a process
		 */
		SIMULATION(false),
		/**
		 * The observation activity that produces a numeric quality
		 */
		QUANTIFICATION(false),
		/**
		 * The observation activity that produces a categorical quality (observes a
		 * conceptual category) over a context.
		 */
		CATEGORIZATION(false),
		/**
		 * The observation activity that produces a boolean quality (presence/absence)
		 */
		VERIFICATION(false),
		/**
		 * The observation activity that attributes a trait or role to another
		 * observation (if it is a quality, it may transform its values). Equivalent to
		 * INSTANTIATION of a concrete t/a given the abstract form and an inherent
		 * observable.
		 */
		CLASSIFICATION(true),
		/**
		 * The resolution activity of a concrete trait or role that has been previously
		 * attributed to an observation.
		 */
		CHARACTERIZATION(false),
		/**
		 * Compilation is the observation of a void observable, producing only side
		 * effects. Creates non-semantic artifacts such as tables, charts, reports etc.
		 */
		COMPILATION(false),
		/**
		 * Acknowledgement is the no-op of observation activity: an object exists and we
		 * just take notice of it.
		 */
		ACKNOWLEDGEMENT(false);

		boolean instantiation;

		/**
		 * Return the resolution mode for this description activity.
		 * 
		 * @return
		 */
		public boolean isInstantiation() {
			return instantiation;
		}

		Description(boolean mode) {
			this.instantiation = mode;
		}
	}

	/**
	 * System time of start.
	 *
	 * @return a long.
	 */
	long getStart();

	/**
	 * System time of end.
	 *
	 * @return a long.
	 */
	long getEnd();

	/**
	 * Scheduler time of action. Null if agent is not the k.LAB scheduler.
	 * 
	 * @return
	 */
	KTime getSchedulerTime();

//	/**
//	 * The type of the action.
//	 * 
//	 * @return
//	 */
//	Type getType();

	/**
	 * If the action was caused by another action, return the action that caused it.
	 *
	 * @return a {@link java.util.Optional} object.
	 */
	Optional<KActivity> getCause();

	/**
	 * Actions are made by agents.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.provenance.KAgent} object.
	 */
	KAgent getAgent();

}
