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
import java.util.List;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

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
	 * The observable builder provides a uniform interface to create and declare
	 * concepts that incarnate all the possible features for an observable. The
	 * builder is smart and fast when concepts that already exist due to previous
	 * declarations are requested.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	interface Builder {

		/**
		 * Add an inherent type to the concept built so far.
		 * 
		 * @param inherent
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder of(IConcept inherent);

		/**
		 * 
		 * @param compresent
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder with(IConcept compresent);

		/**
		 * 
		 * @param context
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder within(IConcept context);

		/**
		 * 
		 * @param goal
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder withGoal(IConcept goal);

		/**
		 * 
		 * @param causant
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder from(IConcept causant);

		/**
		 * 
		 * @param caused
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder to(IConcept caused);

		/**
		 * 
		 * @param role
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder as(IConcept role);
		
		/**
		 * Contextualize the concept built so far to the passed context one. Will choose
		 * the semantics appropriately for the specific context and observables
		 * requested - e.g. a quality contextual to a region will restrict the context,
		 * a quality inherent to an agent in a region will use inherency etc.
		 * 
		 * @param context
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder contextualizedTo(IConcept context);

		/**
		 * Transform the original concept into its equivalent filtered by the passed
		 * semantic operator. For example, transform an original event into its
		 * probability by passing SemanticOperator.PROBABILITY. If the operator implies
		 * additional operands (for example a ratio) these should be passed after the
		 * semantic type.
		 * 
		 * This one transforms the concept in the builder right away, leaving nothing to
		 * do for build() but return the transformed concept, unless more build actions
		 * are called after it.
		 * 
		 * If the original concept cannot be transformed into the specified one, build()
		 * will return an informative exception, but no error will be reported when the
		 * method is called. The getErrors() call will report the exceptions accumulated
		 * if necessary.
		 * 
		 * @param type
		 * @param participants
		 * @return the same builder this was called on, for chaining calls
		 * @throws KlabValidationException
		 */
		Builder as(UnarySemanticOperator type, IConcept... participants) throws KlabValidationException;

		/**
		 * Add traits to the concept being built.
		 * 
		 * Pair with (@link {@link #withTrait(Collection)} as Java is
		 * WriteEverythingTwice, not DontRepeatYourself.
		 *
		 * @param concepts
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder withTrait(IConcept... concepts);

		/**
		 * Add traits to the concept being built.
		 * 
		 * Pair with (@link {@link #withTrait(IConcept...)} as Java is
		 * WriteEverythingTwice, not DontRepeatYourself.
		 * 
		 * @param concepts
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder withTrait(Collection<IConcept> concepts);

		/**
		 * Remove traits or roles from the concept being built. Do nothing if the
		 * concept so far does not have those traits or roles.
		 * 
		 * Pair with (@link {@link #without(IConcept...)} as Java is
		 * WriteEverythingTwice, not DontRepeatYourself.
		 * 
		 * @param concepts
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder without(Collection<IConcept> concepts);

		/**
		 * Remove traits or roles from the concept being built. Do nothing if the
		 * concept so far does not have those traits or roles.
		 * 
		 * Pair with (@link {@link #without(Collection)} as Java is
		 * WriteEverythingTwice, not DontRepeatYourself.
		 *
		 * @param concepts
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder without(IConcept... concepts);

		/**
		 * Build the concept (if necessary) as specified in the configured ontology. If
		 * the concept as specified already exists, just return it.
		 * 
		 * @return the built concept
		 * @throws KlabValidationException
		 */
		IConcept build() throws KlabValidationException;

		/**
		 * Return any exceptions accumulated through the building process before build()
		 * is called. If build() is called when getErrors() returns a non-empty
		 * collection, it will throw an exception collecting the messages from all
		 * exception in the list.
		 * 
		 * @return any errors accumulated
		 */
		Collection<KlabValidationException> getErrors();

		/**
		 * Negate the concept (make it into its negation). Acts immediately on the
		 * concept in the builder.
		 * 
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder negated();

		/**
		 * Make the observable optional (used in dependencies)
		 * 
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder optional();

		/**
		 * Use this to pass a declaration being parsed and set up a monitor so that
		 * logically inconsistent declarations can be reported.
		 * 
		 * @param declaration
		 *            (may be null)
		 * @param monitor
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder withDeclaration(IKimConcept declaration, IMonitor monitor);

		Builder withCooccurrent(IConcept cooccurrent);

		Builder withAdjacent(IConcept adjacent);

	}

	// /**
	// * Get a builder for a declaration of named observable, which will allow
	// adding traits and
	// clauses
	// * and obtain the final concept by calling {@link Builder#build()} on it.
	// *
	// * The concept is created in the reasoner's ontology if a reasoner is active,
	// or in the ontology
	// * where the main concept is located if not.
	// *
	// * @param main
	// * @return a builder for the main concept
	// */
	// // Builder declare(IConcept main);
	//
	// /**
	// * Get a builder for a declaration of named observable, which will allow
	// adding traits and
	// clauses
	// * and obtain the final concept by calling {@link Builder#build()} on it. This
	// version can be
	// * passed a name which can be with or without namespace, and a parent. The
	// concept is only
	// created
	// * if it's not there.
	// *
	// * The concept is created in the reasoner's ontology if a reasoner is active,
	// or in the ontology
	// * where the main concept is located if not.
	// *
	// * @param main concept ID, which must be fully specified or build() will throw
	// an exception.
	// * @param parent
	// * @return a builder for the main concept
	// */
	// // Builder declare(String main, @NotNull IConcept parent);
	//
	// /**
	// * Get a builder for a declaration of named observable, which will allow
	// adding traits and
	// clauses
	// * and obtain the final concept by calling {@link Builder#build()} on it. This
	// version can be
	// * passed a name which can be with or without namespace, and a type to
	// establish the core
	// parent.
	// * The concept is only created if it's not there.
	// *
	// * The concept is created in the reasoner's ontology if a reasoner is active,
	// or in the ontology
	// * where the main concept is located if not.
	// *
	// * @param main concept ID, which must be fully specified or build() will throw
	// an exception
	// * @param type
	// * @return a builder for the main concept
	// */
	// // Builder declare(String main, @NotNull Set<Type> type);
	//
	// /**
	// * Get a builder for a declaration of named observable, which will allow
	// adding traits and
	// clauses
	// * and obtain the final concept by calling {@link Builder#build()} on it.
	// *
	// * @param main
	// * @param ontology
	// * @return a builder for the main concept
	// */
	// // Builder declare(IConcept main, IOntology ontology);
	//
	// /**
	// * Get a builder for a declaration of named observable, which will allow
	// adding traits and
	// clauses
	// * and obtain the final concept by calling {@link Builder#build()} on it. This
	// version can be
	// * passed a name which can be with or without namespace, and a parent. The
	// concept is only
	// created
	// * if it's not there.
	// *
	// * @param main
	// * @param parent
	// * @param ontology
	// * @return a builder for the main concept
	// */
	// // Builder declare(String main, @NotNull IConcept parent, IOntology
	// ontology);
	//
	// /**
	// * Get a builder for a declaration of named observable, which will allow
	// adding traits and
	// clauses
	// * and obtain the final concept by calling {@link Builder#build()} on it. This
	// version can be
	// * passed a name which can be with or without namespace, and a type to
	// establish the core
	// parent.
	// * The concept is only created if it's not there.
	// *
	// * @param main
	// * @param type
	// * @param ontology
	// * @return a builder for the main concept
	// */
	// // Builder declare(String main, @NotNull Set<Type> type, IOntology ontology);

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
	 * Return service calls producing the sequence of mediators needed to turn an
	 * observable into a compatible one.
	 *
	 * The guaranteed constraint coming in is that to.canResolve(from). If this is
	 * not true, the function throws an IllegalArgumentException.
	 *
	 * If from.observable.equals(to.observable), the mediation can only be a unit or
	 * currency conversion. Otherwise it may involve classification (by/downTo) or
	 * other transformation.
	 *
	 * @param from
	 *            a {@link org.integratedmodelling.klab.api.knowledge.IObservable}
	 *            object.
	 * @param to
	 *            a {@link org.integratedmodelling.klab.api.knowledge.IObservable}
	 *            object.
	 * @throws java.lang.IllegalArgumentException
	 *             if observables are incompatible
	 * @return a list of mediators, never null, possibly empty
	 */
	List<IComputableResource> computeMediators(IObservable from, IObservable to);

	/**
	 * Return the base enum type (quality, subject....) for the passed observable.
	 *
	 * @param observable
	 *            a {@link org.integratedmodelling.klab.api.knowledge.IObservable}
	 *            object.
	 * @throws java.lang.IllegalArgumentException
	 *             if not an observable
	 * @return the enum type
	 */
	Type getObservableType(IObservable observable);

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
}
