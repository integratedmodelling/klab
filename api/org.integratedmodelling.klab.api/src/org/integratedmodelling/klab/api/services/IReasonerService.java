/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * The reasoning service holds the OWL reasoner of choice and exposes a number
 * of cached is() methods that enable very fast subsumption checking. It also
 * manages conceptual quality spaces and configurations, as well as implications
 * and observable detection due to extant relationships.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IReasonerService {

	/**
	 * <p>
	 * getSemanticClosure.
	 * </p>
	 *
	 * @param main a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
	 *             object.
	 * @return a {@link java.util.Set} object.
	 */
	Set<IConcept> getSemanticClosure(IConcept main);

	/**
	 * <p>
	 * getParentClosure.
	 * </p>
	 *
	 * @param main a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
	 *             object.
	 * @return a {@link java.util.Set} object.
	 */
	Set<IConcept> getParentClosure(IConcept main);

	/**
	 * <p>
	 * isSatisfiable.
	 * </p>
	 *
	 * @param concept a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
	 *                object.
	 * @return a boolean.
	 */
	boolean isSatisfiable(IConcept concept);

	/**
	 * Target.implies(implied) means target.is(implied) or target has a predicate
	 * that is(implied). The implementation should cache results so that it can be
	 * called many times without penalty.
	 * 
	 * @param target
	 * @param implied
	 * @return
	 */
	boolean implies(IConcept target, IConcept implied);

	/**
	 * The contextual version of implies() is for roles (the only predicates for
	 * which inference is contextual) and takes an observation as context, where the
	 * role may be implied for the target.
	 * 
	 * @param target
	 * @param implied
	 * @return
	 */
	boolean implies(IConcept target, IConcept role, IObservation context);

	/**
	 * The existence of a relationship between two or more subjects may create new
	 * subjects (if structural) or a process (if functional), which are instantiated
	 * after the relationship is instantiated and resolved and automatically
	 * resolved.
	 * 
	 * @param triggerObservation the new observation that may trigger emergent
	 *                           behavior
	 * @param scope              the scope of resolution, where all missing triggers
	 *                           will be looked for
	 * @return pairs containing the type of observable that emerges from the
	 *         observation and the triggering observations themselves.
	 * 
	 */
	Map<IConcept, Collection<IObservation>> getEmergentResolvables(IObservation triggerObservation,
			IContextualizationScope scope);


}
