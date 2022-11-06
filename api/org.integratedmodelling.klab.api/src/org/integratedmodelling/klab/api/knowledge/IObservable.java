/*
 * This file is part of k.LAB. k.LAB is free software: you can redistribute it and/or modify it
 * under the terms of the Affero GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version. A copy of the
 * GNU Affero General Public License is distributed in the root directory of the k.LAB distribution
 * (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>. Copyright (C)
 * 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IConceptService;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

/**
 * An observable is a declared concept with additional observation semantics, including classifying
 * concepts for discretizations, units, currencies or ranges. The declaration of non-quality
 * concepts will always be the same as their type.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IObservable extends ISemantic, IResolvable {

    /**
     * Observables used as queries for categories can specify a conceptual resolution. This is not
     * used when the observable is used to annotate semantics, although Any could be used in
     * dependencies to request multiple model resolution for non-abstract generic countables. If
     * value operators are specified, "down to" may override the resolution or be incompatible with
     * it.
     * 
     * @author Ferd
     */
    enum Resolution {
        /**
         * Makes the observable specify "any" child or itself, normally excluding the abstract ones
         * or those with children.
         */
        Any,
        /**
         * Makes the observable specify all children and itself, normally excluding the abstract
         * ones.
         */
        All,
        /**
         * Ensures the observable specifies only itself in contexts where it would normally specify
         * subclasses too.
         */
        Only
    }

    /**
     * Conditions stated in the observable that control the use of the default value. Only
     * meaningful if a default value is given.
     * 
     * @author Ferd
     *
     */
    enum ResolutionException {
        Missing,
        Nodata,
        Error
    }

    /**
     * The observable builder provides a uniform interface to create and declare concepts that
     * incarnate all the possible features for an observable. The builder is smart and fast when
     * concepts that already exist due to previous declarations are requested.
     * <p>
     * NOTE: the builder's method <em>all</em> return the same builder, not a child builder. This
     * means that, for example, of(concept).withTrait(trait) will apply the trait to the
     * <em>main</em> concept, not the inherent one. In most programmatical applications, this is the
     * desired behavior for fluent observable specification and modification. We provide an
     * ObservableComposer which acts as a stateful builder and implements the alternative behavior
     * and validates each new specification against semantic constraints, meant to be used with
     * interactive applications that build concepts incrementally.
     * 
     * @author ferdinando.villa
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
         * @param compresent
         * @return the same builder this was called on, for chaining calls
         */
        Builder with(IConcept compresent);

        /**
         * @param context
         * @return the same builder this was called on, for chaining calls
         */
        Builder within(IConcept context);

        /**
         * @param goal
         * @return the same builder this was called on, for chaining calls
         */
        Builder withGoal(IConcept goal);

        /**
         * @param causant
         * @return the same builder this was called on, for chaining calls
         */
        Builder from(IConcept causant);

        /**
         * @param caused
         * @return the same builder this was called on, for chaining calls
         */
        Builder to(IConcept caused);

        /**
         * Add roles that become part of the semantics of the observable (Role Trait ... Observable)
         * 
         * @param role
         * @return the same builder this was called on, for chaining calls
         */
        Builder withRole(IConcept role);

        /**
         * Transform the original concept into its equivalent filtered by the passed semantic
         * operator. For example, transform an original event into its probability by passing
         * SemanticOperator.PROBABILITY. If the operator implies additional operands (for example a
         * ratio) these should be passed after the semantic type. This one transforms the concept in
         * the builder right away, leaving nothing to do for build() but return the transformed
         * concept, unless more build actions are called after it. If the original concept cannot be
         * transformed into the specified one, build() will return an informative exception, but no
         * error will be reported when the method is called. The getErrors() call will report the
         * exceptions accumulated if necessary.
         * 
         * @param type
         * @param participants
         * @return the same builder this was called on, for chaining calls
         * @throws KlabValidationException
         */
        Builder as(UnarySemanticOperator type, IConcept... participants) throws KlabValidationException;

        /**
         * Add traits to the concept being built. Pair with (@link {@link #withTrait(Collection)} as
         * Java is WriteEverythingTwice, not DontRepeatYourself.
         *
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder withTrait(IConcept... concepts);

        /**
         * Add traits to the concept being built. Pair with (@link {@link #withTrait(IConcept...)}
         * as Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder withTrait(Collection<IConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far
         * does not have those traits or roles. Pair with (@link {@link #without(IConcept...)} as
         * Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder without(Collection<IConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far
         * does not have those traits or roles. Pair with (@link {@link #without(Collection)} as
         * Java is WriteEverythingTwice, not DontRepeatYourself.
         *
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder without(IConcept... concepts);

        /**
         * Build the concept (if necessary) as specified in the configured ontology. If the concept
         * as specified already exists, just return it.
         * 
         * @return the built concept
         * @throws KlabValidationException
         */
        IConcept buildConcept() throws KlabValidationException;

        /**
         * Build an observable using the observable-specific options (currency, unit, classification
         * and detail types). Use after constructing from an observable using
         * {@link IObservable#getBuilder()}.
         * 
         * @return the built concept
         * @throws KlabValidationException
         */
        IObservable buildObservable() throws KlabValidationException;

        /**
         * Return any exceptions accumulated through the building process before build() is called.
         * If build() is called when getErrors() returns a non-empty collection, it will throw an
         * exception collecting the messages from all exception in the list.
         * 
         * @return any errors accumulated
         */
        Collection<KlabValidationException> getErrors();

        /**
         * Use this to pass a declaration being parsed and set up a monitor so that logically
         * inconsistent declarations can be reported.
         * 
         * @param declaration (may be null)
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

        /**
         * Value operators are added in the order they are received.
         * 
         * @param operator
         * @param valueOperand
         * @return
         */
        Builder withValueOperator(ValueOperator operator, Object valueOperand);

        /**
         * After any of the "without" functions get called, this can be checked on the resulting
         * builder to see what exactly was removed.
         * 
         * @return
         */
        Collection<IConcept> getRemoved();

        Builder linking(IConcept source, IConcept target);

        /**
         * Set the stated name for the observable, which will shadow the read-only "given" name
         * based on the semantics (and make it inaccessible). The read-only reference name (uniquely
         * linked to the semantics) remains unaltered.
         * 
         * @param name
         * @return
         */
        Builder named(String name);

        /**
         * Set the flag that signifies distributed inherency (of each).
         * 
         * @param ofEach
         * @return
         */
        Builder withDistributedInherency(boolean ofEach);

        /**
         * Remove any value operators
         * 
         * @return
         */
        Builder withoutValueOperators();

        /**
         * Tags the classifier of an abstract attribute as targeting a specific concrete attribute,
         * so that any classified objects that won't have that specific attribute can be recognized
         * as irrelevant to this observation and hidden.
         * 
         * @param targetPredicate
         * @return
         */
        Builder withTargetPredicate(IConcept targetPredicate);

        /**
         * Set the observable resulting from buildObservable() as optional.
         * 
         * @param optional
         * @return
         */
        Builder optional(boolean optional);

        /**
         * Remove all the elements <em>directly</em> stated in the current concept corresponding to
         * the passed role, if existing, and return a builder for the concept without them.
         * 
         * @param roles
         * @return
         */
        Builder without(ObservableRole... roles);

        /**
         * Set the temporal inherency for the occurrent observable we specify. Does not change the
         * semantics.
         * 
         * @param concept
         * @return
         */
        Builder withTemporalInherent(IConcept concept);

        /**
         * Add the dereified attribute to the observable. Will only affect the computations built
         * from it after it's resolved.
         * 
         * @param dereifiedAttribute
         * @return
         */
        Builder withDereifiedAttribute(String dereifiedAttribute);

        /**
         * Call after {@link #buildConcept()} or {@link #buildObservable()} to check if any change
         * to the ontologies were made. Returns false if the concept expression requested was
         * already available.
         * 
         * @return
         */
        boolean axiomsAdded();

        /**
         * Set the dereified status to true, so that the observable can be recognized as being
         * "virtual" and not linked to a model.
         * 
         * @return
         */
        Builder setDereified();

        /**
         * Set both the name and the reference name, to preserve a previous setting
         * 
         * @param name
         * @param referenceName
         * @return
         */
        Builder named(String name, String referenceName);

        /**
         * Pass the unit as a string (also checks for correctness at build)
         * 
         * @param unit
         * @return
         */
        Builder withUnit(String unit);

        /**
         * | Pass a currency as a string (also check for monetary value at build)
         * 
         * @param currency
         * @return
         */
        Builder withCurrency(String currency);

        /**
         * Add an inline value to the observable (will check with the IArtifact.Type of the
         * observable at build).
         * 
         * @param value
         * @return
         */
        Builder withInlineValue(Object value);

        /**
         * 
         * @param defaultValue
         * @return
         */
        Builder withDefaultValue(Object defaultValue);
        
        /**
         * 
         * @param resolutionException
         * @return
         */
        Builder withResolutionException(ResolutionException resolutionException);
        
        /**
         * Add a numeric range (check that the artifact type is numeric at build)
         * 
         * @param range
         * @return
         */
        Builder withRange(Range range);

        /**
         * Make this observable generic or not
         * 
         * @param generic
         * @return
         */
        Builder generic(boolean generic);

        /**
         * Define the resolution type for the observable.
         * 
         * @param only
         * @return
         */
        Builder withResolution(Resolution only);

        /**
         * Give or remove the fluid units trait
         * 
         * @param b
         * @return
         */
        Builder fluidUnits(boolean b);

        /**
         * Add an annotation to the result observable.
         * 
         * @param annotation
         * @return
         */
        Builder withAnnotation(IAnnotation annotation);

        /**
         * TODO check if still used
         * 
         * @param global
         * @return
         */
        Builder global(boolean global);

        /**
         * Set the URL for the observable when it comes from a k.IM specification. Only use with
         * full awareness.
         * 
         * @param uri
         * @return
         */
        Builder withUrl(String uri);

    }

    /**
     * Get a builder that will rebuild this observable. Use to build alternative observables with
     * added or removed components.
     * 
     * @param monitor building is monitored, so a monitor must be passed.
     * @return
     */
    Builder getBuilder(IMonitor monitor);

    /**
     * Each observable must be able to quickly assess the type of the description (observation
     * activity) that will produce an IObservation of it. This is also used to instantiate storage
     * for states.
     *
     * @return the necessary observation type
     */
    IActivity.Description getDescriptionType();

    /**
     * Return the type of the artifact correspondent to an observation of this observable.
     * 
     * @return the artifact type.
     */
    IArtifact.Type getArtifactType();

    /**
     * The name is never null and is meant for human and code consumption. It can be explicitly set
     * using the 'named' k.IM clause, and is always a simple lowercase identifier. If no 'named'
     * clause is present, a name is computed similarly to {@link #getReferenceName()} but without
     * using disambiguating namespaces, therefore not guaranteeing a 1:1 correspondence to the
     * semantics but with enough predictability to not have to use 'named' in simple situations to
     * refer to the observable. It's always a lowercase identifier usable for coding in k.IM, k.DL,
     * and most languages. Even if 'named' is given, the name may be different from the stated
     * because of disambiguation when the observable is used in a dataflow.
     *
     * @return the name of this observable
     */
    String getName();

    /**
     * The reference name is the default name and only depends on the contents of the observable. It
     * is uniquely related to the semantics. It may be modified for disambiguation in the
     * observables used when creating dataflows. It does not correspond to {@link #getName()}, which
     * is meant for human consumption.
     * 
     * @return the reference name of this observable
     */
    String getReferenceName();

    /**
     * The stated name is either null or whatever was given in the 'named' clause, and will never be
     * modified or redefined. It is meant to preserve the original name to capture references in
     * models that are derived from others.
     * 
     * @return the stated name of this observable.
     */
    String getStatedName();

    /**
     * Return any mediator in the state: unit, currency or range. These are also returned separately
     * by other methods if we need to discriminate.
     * 
     * @return
     */
    IValueMediator getMediator();

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
     * The context type, direct or indirect, and revised according to the stated inherency (will be
     * reverted to null if the indirect context is X and the concept is <this> of X). The revision
     * only applies to observables and does not affect the underlying semantics.
     * 
     * @return the context type
     */
    IConcept getContext();

    /**
     * The inherent type, direct or indirect.
     * 
     * @return the inherent type
     */
    IConcept getInherent();

    /**
     * An occurrent observable may be temporally inherent to an event, i.e. it will happen during
     * each instance of it. Specified by 'during each' in observable syntax.
     * 
     * @return
     */
    IConcept getTemporalInherent();

    /**
     * If the observable was defined with an inline value (e.g. '10 as Concept'), report the POD
     * value here.
     *
     * @return the inline value (a POD; a distribution, {@link IKimExpression expression},
     *         {@link IServiceCall function call}, {@link org.integratedmodelling.klab.utils.Range}
     *         or {@link java.util.List} are also possible, but so far there are no situations in
     *         which this happens.)
     */
    Object getValue();

    /**
     * If a default value was defined for a quality observable, it is returned here. It will be
     * applied according to the stated resolution exceptions and the optional status.
     * 
     * @return
     */
    Object getDefaultValue();

    /**
     * Resolution exceptions linked to the use of a stated default value.
     * 
     * @return
     */
    Collection<ResolutionException> getResolutionExceptions();

    /**
     * A generic observable expects to be resolved extensively - i.e., all the subtypes, leaving the
     * base type last if the subtypes don't provide full coverage. This subsumes the abstract nature
     * of the observable concept, but may also be true in dependency observables, which may
     * explicitly ask to be generic even if not abstract ('any' modifier), or result from an
     * abstract clause (e.g. 'during <abstract event type>').
     *
     * @return true if generic
     */
    boolean isGeneric();

    /**
     * True if the observable was declared optional. This can only happen in model dependencies and
     * for the observables of acknowledged subjects.
     *
     * @return optional status
     */
    boolean isOptional();

    /**
     * Use the reasoner with the passed concept. If there are conceptual modifiers and the passed
     * semantics is another observable, apply the reasoner to them as well. This check is expensive
     * and should not be used during contextualization.
     * 
     * @param c
     * @return
     */
    boolean is(ISemantic c);

    /**
     * If this observable is the subjective point of view of a subject, return that subject. A null
     * return value implies the observer is the owner of the session, i.e. what we can most
     * legitimately call the "objective" observer for the observable.
     * 
     * @return
     */
    IDirectObservation getObserver();

    /**
     * Return all the annotations attributed to the object in the originating k.IM code.
     *
     * @return a list of annotations in order of declaration, or null.
     */
    List<IAnnotation> getAnnotations();

    /**
     * String definition of this observable, re-parseable into a compatible one. The definition is
     * normalized, with sorted components and parenthesized as necessary, to guarantee an
     * unambiguous result and the equality of observable definitions with identical semantics. Only
     * the part that affects semantics is part of the definition: name, units or currencies are not
     * included. Use {@link #getDeclaration()} if those are desired.
     * 
     * @return
     */
    String getDefinition();

    /**
     * The declaration is the same string returned by {@link #getDefinition()}, but including all
     * clauses that do not directly affect semantics. If a name was stated in the original
     * declaration, the 'named' clause is added to the definition. The same applies to units or
     * currencies. Apart from that, the definition remains normalized so it may differ from an
     * original, user-supplied string.
     * 
     * @return
     */
    String getDeclaration();

    /**
     * Abstract status of an observable may be more involved than just the abstract status of the
     * main type, although in most cases that will be the result.
     * 
     * @return
     */
    boolean isAbstract();

    /**
     * True if the main observable has the passed semantic identifier. This check is quick and
     * painless.
     * 
     * @param type
     * @return
     */
    boolean is(Type type);

    /**
     * Any value operators are returned here, paired with their operands.
     * 
     * @return
     */
    List<Pair<ValueOperator, Object>> getValueOperators();

    /**
     * Globalized observables have "all" prepended and are used in classifiers and other situations
     * (but never in models) to indicate that all levels of the subsumed asserted hierarchy should
     * be considered, including abstract ones.
     * 
     * @return
     */
    boolean isGlobal();

    /**
     * If a resolution was specified, return it. If not, return null - the default resolution will
     * depend on the context of use, and will be ignored in most models.
     * 
     * @return
     */
    Resolution getResolution();

    /**
     * Return any role picked up during resolution for this observable. This happens when the
     * observable has been resolved from a generic dependency on the role, which may have been
     * defined by the session or implied during the resolution of an upstream process or direct
     * observable.
     * <p>
     * The roles returned here are not part of the observable's semantics and only apply to it in
     * the specific resolution and contextualization scope.
     * 
     * @return
     */
    Collection<IConcept> getContextualRoles();

    /**
     * Complements the equivalent {@link IConcept#resolves(IConcept, IConcept)} with a check on
     * value operators and other possible differences.
     * 
     * @param other
     * @param context
     * @return
     */
    boolean resolves(IObservable other, IConcept context);

    /**
     * Return any abstract identity or role that are set in this observable, and will need to be
     * resolved to concrete ones before the observable can be resolved. This will return an empty
     * set if the observable is generic, as that is handled differently.
     * <p>
     * For now abstract roles are always returned, and abstract identities are returned only if they
     * are required by the observable ('requires identity ....'). This prevents unwanted resolutions
     * of abstract predicates that may be used as tags only: the "need" for identification must be
     * explicitly stated.
     * 
     * @return
     */
    Collection<IConcept> getAbstractPredicates();

    /**
     * If the observable results from resolving another with abstract predicates, return the mapping
     * of abstract -> concrete made by the resolver. This enables reconstructing the original
     * abstract observable by replacing the concept after translating it
     * ({@link IConceptService#replaceComponent(IConcept, Map)}) using the reverse mapping of the
     * result.
     * 
     * @return
     */
    Map<IConcept, IConcept> getResolvedPredicates();

    /**
     * If true, the observable is for a dereifying observation, which just merges the results of
     * observations of inherent sub-contexts (e.g. runoff of watershed, within region). Actuators
     * for those observations aren't scheduled and may be treated differently.
     * 
     * @return true if dereified
     */
    boolean isDereified();

    /**
     * If true, this observable is explicitly declaring a context that is a subclass of the
     * "natural" context of the primary observable. For example, "X within RiverBasin" when the
     * natural context (declared for X) is "Region". This is used to speed search for alternative
     * explanations that require distributing calculations across different objects, only done if
     * the natural observation is not possible. These should only be used as the observables of
     * models, with full knowledge of the drawbacks (i.e., X must be observable in the context and
     * the observation within X must fully cover the observation in the natural context) and the
     * flag won't be set if the same specification is given in a semantic declaration.
     * 
     * @return
     */
    boolean isSpecialized();

}
