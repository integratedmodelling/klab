package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.SemanticOperator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IObservableService {

    /**
     * The observable builder provides a uniform interface to create and declare concepts that incarnate all
     * the possible features for an observable. Get a builder from either
     * {@link IObservableService#declare(IConcept)}, {@link IObservableService#declare(String, IConcept)} or
     * {@link IObservableService#declare(String, Set)} (or any of the counterparts that specify the ontology for
     * the result) and call any methods in sequence before calling
     * {{@link #build()} to actually create the concept. The builder is smart and
     * fast when concepts that already exist due to previous declarations are requested.
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
         * 
         * @param limit
         * @return the same builder this was called on, for chaining calls
         */
        Builder downTo(IConcept limit);

        /**
         * Classify of the concept built so far 'by' the passed one.
         * 
         * @param trait
         * @return the same builder this was called on, for chaining calls
         */
        Builder by(IConcept trait);

        /**
         * Contextualize the concept built so far to the passed context one. Will choose the semantics
         * appropriately for the specific context and observables requested - e.g. a quality contextual to a
         * region will restrict the context, a quality inherent to an agent in a region will use inherency
         * etc.
         * 
         * @param context
         * @return the same builder this was called on, for chaining calls
         */
        Builder contextualizedTo(IConcept context);

        /**
         * Transform the original concept into its equivalent filtered by the passed semantic operator. For
         * example, transform an original event into its probability by passing SemanticOperator.PROBABILITY.
         * If the operator implies additional operands (for example a ratio) these should be passed after
         * the semantic type.
         * 
         * This one transforms the concept in the builder right away, leaving nothing to do for build() 
         * but return the transformed concept, unless more build actions are called after it.
         * 
         * If the original concept cannot be transformed into the specified one, build() will return an
         * informative exception, but no error will be reported when the method is called. The getErrors()
         * call will report the exceptions accumulated if necessary.
         * 
         * @param type
         * @param participants 
         * @return the same builder this was called on, for chaining calls
         */
        Builder as(SemanticOperator type, IConcept... participants);

        /**
         * Add traits to the concept being built.
         * 
         * Pair with (@link {@link #withTrait(Collection)} as Java is WriteEverythingTwice, not
         * DontRepeatYourself.
         *
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder withTrait(IConcept... concepts);

        /**
         * Add traits to the concept being built.
         * 
         * Pair with (@link {@link #withTrait(IConcept...)} as Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder withTrait(Collection<IConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far does not have
         * those traits or roles.
         * 
         * Pair with (@link {@link #without(IConcept...)} as Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder without(Collection<IConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far does not have
         * those traits or roles.
         * 
         * Pair with (@link {@link #without(Collection)} as Java is WriteEverythingTwice, not
         * DontRepeatYourself.
         *
         * @param concepts
         * @return the same builder this was called on, for chaining calls
         */
        Builder without(IConcept... concepts);

        /**
         * Build the concept (if necessary) as specified in the configured ontology. If the concept as specified
         * already exists, just return it.
         * 
         * @return the built concept
         * @throws KlabValidationException
         */
        IConcept build() throws KlabValidationException;

        /**
         * Return any exceptions accumulated through the building process before build() is called. If build()
         * is called when getErrors() returns a non-empty collection, it will throw an exception collecting 
         * the messages from all exception in the list.
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
        
    }

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build()} on it.
     * 
     * The concept is created in the reasoner's ontology if a reasoner is active, or in the ontology
     * where the main concept is located if not.
     * 
     * @param main
     * @return a builder for the main concept
     */
    Builder declare(IConcept main);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build()} on it. This version can be passed
     * a name which can be with or without namespace, and a parent. The concept is only created if it's not
     * there.
     * 
     * The concept is created in the reasoner's ontology if a reasoner is active, or in the ontology
     * where the main concept is located if not.
     * 
     * @param main concept ID, which must be fully specified or build() will throw an exception.
     * @param parent
     * @return a builder for the main concept
     */
    Builder declare(String main, @NotNull IConcept parent);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build()} on it. This version can be passed
     * a name which can be with or without namespace, and a type to establish the core parent. The concept is
     * only created if it's not there.
     * 
     * The concept is created in the reasoner's ontology if a reasoner is active, or in the ontology
     * where the main concept is located if not.
     * 
     * @param main concept ID, which must be fully specified or build() will throw an exception
     * @param type
     * @return a builder for the main concept
     */
    Builder declare(String main, @NotNull Set<Type> type);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build()} on it.
     * 
     * @param main
     * @param ontology 
     * @return a builder for the main concept
     */
    Builder declare(IConcept main, IOntology ontology);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build()} on it. This version can be passed
     * a name which can be with or without namespace, and a parent. The concept is only created if it's not
     * there.
     * 
     * @param main
     * @param parent
     * @param ontology 
     * @return a builder for the main concept
     */
    Builder declare(String main, @NotNull IConcept parent, IOntology ontology);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build()} on it. This version can be passed
     * a name which can be with or without namespace, and a type to establish the core parent. The concept is
     * only created if it's not there.
     * 
     * @param main
     * @param type
     * @param ontology 
     * @return a builder for the main concept
     */
    Builder declare(String main, @NotNull Set<Type> type, IOntology ontology);

    /**
     * Get all the restricted target of the "applies to" specification for this concept.
     * 
     * @param main
     * @return all applicable concepts or an empty collection
     */
    Collection<IConcept> getApplicableObservables(IConcept main);

}
