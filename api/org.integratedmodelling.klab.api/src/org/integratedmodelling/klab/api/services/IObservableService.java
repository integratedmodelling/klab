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

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Interface IObservableService.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IObservableService {

    /**
     * Flag for {@link #isCompatible(IConcept, IConcept, int)}.
     * 
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, different realms
     * will not determine incompatibility.
     */
    static public final int ACCEPT_REALM_DIFFERENCES = 0x01;

    /**
     * Flag for {@link #isCompatible(IConcept, IConcept, int)}.
     * 
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, only types that
     * have the exact same core type will be accepted.
     */
    static public final int REQUIRE_SAME_CORE_TYPE = 0x02;

    /**
     * Flag for {@link #isCompatible(IConcept, IConcept, int)}.
     * 
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, types with roles
     * that are more general of the roles in the first concept will be accepted.
     */
    static public final int USE_ROLE_PARENT_CLOSURE = 0x04;

    /**
     * Flag for {@link #isCompatible(IConcept, IConcept, int)}.
     * 
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, types with
     * traits that are more general of the traits in the first concept will be
     * accepted.
     */
    static public final int USE_TRAIT_PARENT_CLOSURE = 0x08;

    /**
     * Flag for {@link #isCompatible(IConcept, IConcept, int)}.
     * 
     * If passed to {@link #isCompatible(IConcept, IConcept, int)} causes acceptance
     * of subjective traits for observables.
     */
    static public final int ACCEPT_SUBJECTIVE_OBSERVABLES = 0x10;

    /**
     * Reconstruct an observable from a canonical declaration.
     *
     * @param declaration
     *            a {@link java.lang.String} object.
     * @return the reconstructed observable.
     */
    IObservable declare(String declaration);

    /**
     * Reconstruct an observable from the result of parsing a declaration.
     *
     * @param observable
     *            a {@link org.integratedmodelling.kim.api.IKimObservable} object.
     * @param monitor
     *            a
     *            {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
     *            object.
     * @return the reconstructed observable.
     */
    IObservable declare(IKimObservable observable, IMonitor monitor);

    /**
     * <p>
     * getInherentType.
     * </p>
     *
     * @param concept
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getInherentType(IConcept concept);

    /**
     * <p>
     * getCompresentType.
     * </p>
     *
     * @param concept
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getCompresentType(IConcept concept);

    /**
     * <p>
     * getCausantType.
     * </p>
     *
     * @param concept
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getCausantType(IConcept concept);

    /**
     * <p>
     * getCausedType.
     * </p>
     *
     * @param concept
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getCausedType(IConcept concept);

    /**
     * <p>
     * getGoalType.
     * </p>
     *
     * @param concept
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getGoalType(IConcept concept);

    /**
     * <p>
     * getContextType.
     * </p>
     *
     * @param concept
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getContextType(IConcept concept);

    /**
     * Get all the restricted target of the "applies to" specification for this
     * concept.
     *
     * @param main
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return all applicable concepts or an empty collection
     */
    Collection<IConcept> getApplicableObservables(IConcept main);

    /**
     * <p>
     * getCoreObservable.
     * </p>
     *
     * @param c
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getCoreObservable(IConcept c);

    /**
     * True if o1 and o2 are observables from recognized domains, have compatible
     * context and inherency, o1 is o2, and o1 adopts all the traits and roles that
     * o2 adopts.
     *
     * @param o1
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @param o2
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     *            
     * @return true if these are compatible observables
     */
    boolean isCompatible(IConcept o1, IConcept o2);

    /**
     * Same as {@link #isCompatible(IConcept, IConcept)} but accepts flags to handle
     * specific needs in assessing compatibility.
     *
     * @param o1
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @param o2
     *            a {@link org.integratedmodelling.klab.api.knowledge.IConcept}
     *            object.
     * @param flags
     *            see {@link #ACCEPT_REALM_DIFFERENCES} and siblings. Pass them in
     *            bitwise OR if more are needed.
     * @return true if these are compatible observables
     */
    boolean isCompatible(IConcept o1, IConcept o2, int flags);

    /**
     * Return the Java class of the observation type corresponding to the passed
     * observable.
     *
     * @param observable
     *            a {@link org.integratedmodelling.klab.api.knowledge.IObservable}
     *            object.
     * @return a {@link java.lang.Class} object.
     */
    Class<? extends IObservation> getObservationClass(IObservable observable);

    /**
     * Return the Java class of the observation type corresponding to the passed
     * resolvable.
     *
     * @param resolvable
     *            a {@link org.integratedmodelling.klab.api.resolution.IResolvable}
     *            object.
     * @return a {@link java.lang.Class} object.
     */
    Class<? extends IObservation> getObservationClass(IResolvable resolvable);

    /**
     * Return the base enum type (quality, subject....) for the passed observable.
     *
     * @param observable
     *            a {@link org.integratedmodelling.klab.api.knowledge.IObservable}
     *            object.
     * @param acceptTraits
     * 			if true, will return a trait type (which can be the observable of a 
     * 	 		class model although it's not an observable per se).
     * @throws java.lang.IllegalArgumentException
     * 	 
     *             if not an observable
     * @return the enum type
     */
    Type getObservableType(IObservable observable, boolean acceptTraits);

    /**
     * Return the asserted source of the relationship, assuming it is unique. If it
     * is not unique, the result is arbitrary among the possible sources.
     * 
     * @param relationship
     *            a relationship concept
     * @return the source. May be null in abstract relationships.
     */
    IConcept getRelationshipSource(IConcept relationship);

    /**
     * Return all the asserted sources of the relationship.
     * 
     * @param relationship
     *            a relationship concept
     * @return the sources. May be empty in abstract relationships.
     */
    Collection<IConcept> getRelationshipSources(IConcept relationship);

    /**
     * Return the asserted target of the relationship, assuming it is unique. If it
     * is not unique, the result is arbitrary among the possible targets.
     * 
     * @param relationship
     *            a relationship concept
     * @return the target. May be null in abstract relationships.
     */
    IConcept getRelationshipTarget(IConcept relationship);

    /**
     * Return all the asserted targets of the relationship.
     * 
     * @param relationship
     *            a relationship concept
     * @return the targets. May be empty in abstract relationships.
     */
    Collection<IConcept> getRelationshipTargets(IConcept relationship);

    /**
     * Return the adjacent type, if any.
     * 
     * @param concept
     * @return
     */
    IConcept getAdjacentType(IConcept concept);

    /**
     * Return the co-occurrent type ('during'), if any.
     * 
     * @param concept
     * @return
     */
    IConcept getCooccurrentType(IConcept concept);

    IConcept getDirectInherentType(IConcept concept);

    IConcept getDirectCompresentType(IConcept concept);

    IConcept getDirectCausantType(IConcept concept);

    IConcept getDirectCausedType(IConcept concept);

    IConcept getDirectGoalType(IConcept concept);

    IConcept getDirectAdjacentType(IConcept concept);

    IConcept getDirectCooccurrentType(IConcept concept);

    IConcept getDirectContextType(IConcept concept);

    IConcept getComparisonType(IConcept concept);

    /**
     * Recontextualize an observable to a different COMPATIBLE context. If the context is not compatible,
     * throw an IllegalArgumentException. Used during resolution of any concept to ensure its context 
     * matter.
     * 
     * @param observable
     * @param newContext
     * @return the recontextualized observable
     */
    IObservable contextualizeTo(IObservable observable, IConcept newContext, IMonitor monitor);
}
