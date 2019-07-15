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
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Range;

/**
 * An observable is a declared concept with additional observation semantics,
 * including classifying concepts for discretizations, units, currencies or
 * ranges. The declaration of non-quality concepts will always be the same as
 * their type.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IObservable extends ISemantic, IResolvable {

	/**
	 * A classification of the observation activity that can produce an observation
	 * of this observable.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public enum ObservationType {
		/**
		 * The observation that produces a countable object
		 */
		INSTANTIATION,
		/**
		 * The observation that produces a configuration
		 */
		DETECTION,
		/**
		 * The observation that produces a dynamic account of a process
		 */
		SIMULATION,
		/**
		 * The observation that produces a numeric quality
		 */
		QUANTIFICATION,
		/**
		 * The observation that produces a categorical quality (observes a conceptual
		 * category) over a context.
		 */
		CATEGORIZATION,
		/**
		 * The observation that produces a boolean quality (presence/absence)
		 */
		VERIFICATION,
		/**
		 * The observation that attributes a trait or role to another observation (if it
		 * is a quality, it may transform its values). Equivalent to INSTANTIATION of a
		 * concrete t/a given the abstract form and an inherent observable.
		 */
		CLASSIFICATION,
		/**
		 * The resolution of a concrete trait or role that has been previously
		 * attributed to an observation.
		 */
		CHARACTERIZATION
	}

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
		 * Add roles that become part of the semantics of the observable (Role Trait ...
		 * Observable)
		 * 
		 * @param role
		 * @return the same builder this was called on, for chaining calls
		 */
		Builder withRole(IConcept role);

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
		IConcept buildConcept() throws KlabValidationException;

		/**
		 * Build an observable using the observable-specific options (currency, unit,
		 * classification and detail types). Use after constructing from an observable
		 * using {@link IObservable#getBuilder()}.
		 * 
		 * @return the built concept
		 * @throws KlabValidationException
		 */
		IObservable buildObservable() throws KlabValidationException;

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

		Builder withoutAny(Collection<IConcept> concepts);

		Builder withoutAny(IKimConcept.Type... type);

		Builder withoutAny(IConcept... concepts);

		Builder withUnit(IUnit unit);

		Builder withCurrency(ICurrency currency);

		Builder downTo(IConcept detail);

		Builder by(IConcept classifier);

		Builder withValueOperator(ValueOperator operator, Object valueOperand);

		/**
		 * After any of the "without" functions get called, this can be checked on the
		 * resulting builder to see what exactly was removed.
		 * 
		 * @return
		 */
		Collection<IConcept> getRemoved();

		Builder linking(IConcept source, IConcept target);

		Builder named(String name);

		/**
		 * Set the flag that signifies distributed inherency (of each).
		 * 
		 * @param ofEach
		 * @return
		 */
		Builder setDistributedInherency(boolean ofEach);

		/**
		 * Set the observable whose observation is expected to go to the 
		 * filter that this observable specifies.
		 * 
		 * @param observableId
		 * @return
		 */
		Builder filtering(IObservable observable);

	}

	/**
	 * Get a builder that will rebuild this observable. Use to build alternative
	 * observables with added or removed components.
	 * 
	 * @param monitor
	 *            building is monitored, so a monitor must be passed.
	 * 
	 * @return
	 */
	Builder getBuilder(IMonitor monitor);

	/**
	 * Each observable must be able to quickly assess the type of the observation
	 * that will produce an IObservation of it. This is also used to instantiate the
	 * storage for states.
	 *
	 * @return the necessary observation type
	 */
	ObservationType getObservationType();

	/**
	 * Return the type of the artifact correspondent to an observation of this
	 * observable.
	 * 
	 * @return the artifact type.
	 */
	IArtifact.Type getArtifactType();

	/**
	 * Observables always have a name, which is unique in the context of a model
	 * where they are used, and can be used within a model to refer to the
	 * observation made of it. The name can be explicitly set using the 'named' k.IM
	 * clause, and is always a simple lowercase identifier.
	 *
	 * @return the formal name of this observable
	 */
	String getName();

	/**
	 * <p>
	 * getDownTo.
	 * </p>
	 *
	 * @return the normalized 'down to' limiter concept if any was specified.
	 */
	IConcept getDownTo();

	/**
	 * @return the 'by' classifier concept, if any was specified.
	 */
	IConcept getClassifier();

	/**
	 * <p>
	 * getRange.
	 * </p>
	 *
	 * @return the numeric range, if any was specified.
	 */
	Range getRange();

	/**
	 * <p>
	 * getUnit.
	 * </p>
	 *
	 * @return the unit, if any was specified.
	 */
	IUnit getUnit();

	/**
	 * <p>
	 * getCurrency.
	 * </p>
	 *
	 * @return the currency, if any was specified.
	 */
	ICurrency getCurrency();

	/**
	 * The context type.
	 * 
	 * @return the context type
	 */
	IConcept getContext();

	/**
	 * The inherent type.
	 * 
	 * @return the inherent type
	 */
	IConcept getInherentType();

	/**
	 * The comparison type, if any, for observables that admit it - values,
	 * proportions and ratios. This is only certainly not null for ratios.
	 * 
	 * @return the inherent type
	 */
	IConcept getComparisonType();

	/**
	 * The caused ('causing') type.
	 * 
	 * @return the caused type
	 */
	IConcept getCaused();

	/**
	 * The causant ('caused by') type
	 * 
	 * @return the caused type
	 */
	IConcept getCausant();

	/**
	 * The compresent ('with') type
	 * 
	 * @return the compresent type
	 */
	IConcept getCompresent();

	/**
	 * The purpose ('for') type
	 * 
	 * @return the purpose type
	 */
	IConcept getPurpose();

	/**
	 * If the observable was defined with an inline value (e.g. '10 as Concept'),
	 * report the POD value here.
	 *
	 * @return the inline value (a POD; a distribution, {@link IKimExpression
	 *         expression}, {@link IServiceCall function call},
	 *         {@link org.integratedmodelling.klab.utils.Range} or
	 *         {@link java.util.List} are also possible, but so far there are no
	 *         situations in which this happens.)
	 */
	Object getValue();

	/**
	 * A generic observable expects to be resolved extensively - i.e., all the
	 * subtypes, leaving the base type last if the subtypes don't provide full
	 * coverage. This subsumes the abstract nature of the observable concept, but
	 * may also be true in dependency observables, which may explicitly ask to be
	 * generic even if not abstract ('any' modifier).
	 *
	 * @return true if generic
	 */
	boolean isGeneric();

	/**
	 * True if the observable was declared optional. This can only happen in model
	 * dependencies and for the observables of acknowledged subjects.
	 *
	 * @return optional status
	 */
	boolean isOptional();

	/**
	 * Use the reasoner with the passed concept. If there are conceptual modifiers
	 * and the passed semantics is another observable, apply the reasoner to them as
	 * well.
	 * 
	 * @param c
	 * @return
	 */
	boolean is(ISemantic c);

	/**
	 * If this observable is the subjective point of view of a subject, return that
	 * subject. A null return value implies the observer is the owner of the
	 * session, i.e. what we can most legitimately call the "objective" observer for
	 * the observable.
	 * 
	 * @return
	 */
	IDirectObservation getObserver();

	/**
	 * Return all the annotations attributed to the object in the originating k.IM
	 * code.
	 *
	 * @return a list of annotations in order of declaration, or null.
	 */
	List<IAnnotation> getAnnotations();

	/**
	 * String definition of this observable, re-parseable in a compatible one.
	 * 
	 * @return
	 */
	String getDefinition();

	/**
	 * Abstract status of an observable may be more involved than just the abstract
	 * status of the main type, although in most cases that will be the result.
	 * 
	 * @return
	 */
	boolean isAbstract();

	/**
	 * True if the main observable has the passed semantics.
	 * 
	 * @param type
	 * @return
	 */
	boolean is(Type type);

	/**
	 * If the observable has a value operator (e.g. "> 0") return it here.
	 * 
	 * TODO this must become a list of <operator, operand> pairs
	 * 
	 * @return
	 */
	ValueOperator getValueOperator();

	/**
	 * If the observable has a value modifier, then it will also have an operand for
	 * it, which can be another IObservable, a IConcept, or a literal value
	 * (currently a number only).
	 * 
	 * @return the operand. Will be null unless {@link #getValueOperator()} returns
	 *         not null.
	 * @deprecated see note for getValueOperator().
	 */
	Object getValueOperand();

}
