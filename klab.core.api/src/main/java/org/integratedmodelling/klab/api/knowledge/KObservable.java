package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.data.mediation.KCurrency;
import org.integratedmodelling.klab.api.data.mediation.KUnit;
import org.integratedmodelling.klab.api.exceptions.KValidationException;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.api.lang.UnarySemanticOperator;
import org.integratedmodelling.klab.api.lang.ValueOperator;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.services.runtime.KChannel;

public interface KObservable extends KSemantics {

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
        Builder of(KConcept inherent);

        /**
         * @param compresent
         * @return the same builder this was called on, for chaining calls
         */
        Builder with(KConcept compresent);

        /**
         * @param context
         * @return the same builder this was called on, for chaining calls
         */
        Builder within(KConcept context);

        /**
         * @param goal
         * @return the same builder this was called on, for chaining calls
         */
        Builder withGoal(KConcept goal);

        /**
         * @param causant
         * @return the same builder this was called on, for chaining calls
         */
        Builder from(KConcept causant);

        /**
         * @param caused
         * @return the same builder this was called on, for chaining calls
         */
        Builder to(KConcept caused);

        /**
         * Add roles that become part of the semantics of the observable (Role Trait ... Observable)
         * 
         * @param role
         * @return the same builder this was called on, for chaining calls
         */
        Builder withRole(KConcept role);

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
         * @throws KValidationException
         */
        Builder as(UnarySemanticOperator type, KConcept... participants) throws KValidationException;

        /**
         * Add traits to the concept being built. Pair with (@link {@link #withTrait(Collection)} as
         * Java is WriteEverythingTwice, not DontRepeatYourself.
         *
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder withTrait(KConcept... concepts);

        /**
         * Add traits to the concept being built. Pair with (@link {@link #withTrait(IConcept...)}
         * as Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder withTrait(Collection<KConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far
         * does not have those traits or roles. Pair with (@link {@link #without(IConcept...)} as
         * Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder without(Collection<KConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far
         * does not have those traits or roles. Pair with (@link {@link #without(Collection)} as
         * Java is WriteEverythingTwice, not DontRepeatYourself.
         *
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder without(KConcept... concepts);

        /**
         * Build the concept (if necessary) as specified in the configured ontology. If the concept
         * as specified already exists, just return it.
         * 
         * @return the built concept
         * @throws KValidationException
         */
        KConcept buildConcept() throws KValidationException;

        /**
         * Build an observable using the observable-specific options (currency, unit, classification
         * and detail types). Use after constructing from an observable using
         * {@link IObservable#getBuilder()}.
         * 
         * @return the built concept
         * @throws KValidationException
         */
        KObservable buildObservable() throws KValidationException;

        /**
         * Return any exceptions accumulated through the building process before build() is called.
         * If build() is called when getErrors() returns a non-empty collection, it will throw an
         * exception collecting the messages from all exception in the list.
         * 
         * @return any errors accumulated
         */
        Collection<KValidationException> getErrors();

        /**
         * Use this to pass a declaration being parsed and set up a monitor so that logically
         * inconsistent declarations can be reported.
         * 
         * @param declaration (may be null)
         * @param monitor
         * @return the same builder this was called on, for chaining calls
         */
        Builder withDeclaration(KKimConcept declaration, KChannel monitor);

        Builder withCooccurrent(KConcept cooccurrent);

        Builder withAdjacent(KConcept adjacent);

        Builder withoutAny(Collection<KConcept> concepts);

        Builder withoutAny(SemanticType... type);

        Builder withoutAny(KConcept... concepts);

        Builder withUnit(KUnit unit);

        Builder withCurrency(KCurrency currency);

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
        Collection<KConcept> getRemoved();

        Builder linking(KConcept source, KConcept target);

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
        Builder withTargetPredicate(KConcept targetPredicate);

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
        Builder without(SemanticRole... roles);

        /**
         * Set the temporal inherency for the occurrent observable we specify. Does not change the
         * semantics.
         * 
         * @param concept
         * @return
         */
        Builder withTemporalInherent(KConcept concept);

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
        Builder withAnnotation(KAnnotation annotation);

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
    
    KConcept getSemantics();
    
    KUnit getUnit();
    
    KUnit getCurrency();

    Collection<Pair<ValueOperator, Object>> getValueOperators();

    Collection<KAnnotation> getAnnotations();

}
