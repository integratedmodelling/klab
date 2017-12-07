package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.SemanticOperator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IObservableService {

    /**
     * The observable builder provides a uniform interface to create and declare concepts that incarnate all
     * the possible features for an observable. Get a builder from either
     * {@link IObservableService#declare(IConcept)}, {@link IObservableService#declare(String, IConcept)} or
     * {@link IObservableService#declare(String, Set)} and call any methods in sequence before calling
     * {{@link #build()} or {@link #build(IOntology)} to actually create the concept. The builder is smart and
     * fast when concepts that already exist due to previous declarations are requested.
     * 
     * @author ferdinando.villa
     *
     */
    interface Builder {

        /**
         * Add an inherent type to the concept built so far.
         * 
         * @param concept
         * @return
         */
        Builder of(IConcept inherent);

        /**
         * 
         * @param compresent
         * @return
         */
        Builder with(IConcept compresent);

        /**
         * 
         * @param context
         * @return
         */
        Builder within(IConcept context);

        /**
         * 
         * @param goal
         * @return
         */
        Builder withGoal(IConcept goal);

        /**
         * 
         * @param causant
         * @return
         */
        Builder from(IConcept causant);

        /**
         * 
         * @param caused
         * @return
         */
        Builder to(IConcept caused);

        /**
         * 
         * @param role
         * @return
         */
        Builder as(IConcept role);

        /**
         * 
         * @param limit
         * @return
         */
        Builder downTo(IConcept limit);

        /**
         * Classify of the concept built so far 'by' the passed one.
         * 
         * @param concept
         * @return
         */
        Builder by(IConcept trait);

        /**
         * Contextualize the concept built so far to the passed context one. Will choose the semantics
         * appropriately for the specific context and observables requested - e.g. a quality contextual to a
         * region will restrict the context, a quality inherent to an agent in a region will use inherency
         * etc.
         * 
         * @param concept
         * @return
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
         * @return
         */
        Builder as(SemanticOperator type, IConcept... participants);

        /**
         * Add traits to the concept being built.
         * 
         * Pair with (@link {@link #with(IConcept...)} as Java is WriteEverythingTwice, not
         * DontRepeatYourself.
         *
         * @param concepts
         * @return
         */
        Builder withTrait(IConcept... concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far does not have
         * those traits or roles.
         * 
         * Pair with (@link {@link #with(Collection)} as Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return
         */
        Builder without(Collection<IConcept> concepts);

        /**
         * Remove traits or roles from the concept being built. Do nothing if the concept so far does not have
         * those traits or roles.
         * 
         * Pair with (@link {@link #with(IConcept...)} as Java is WriteEverythingTwice, not
         * DontRepeatYourself.
         *
         * @param concepts
         * @return
         */
        Builder without(IConcept... concepts);

        /**
         * Add traits to the concept being built.
         * 
         * Pair with (@link {@link #with(Collection)} as Java is WriteEverythingTwice, not DontRepeatYourself.
         * 
         * @param concepts
         * @return
         */
        Builder withTrait(Collection<IConcept> concepts);

        /**
         * Build the concept (if necessary) as specified in the passed ontology. If the concept as specified
         * already exists, just return it.
         * 
         * @param ontology
         * @return
         * @throws KlabValidationException
         */
        IConcept build(IOntology ontology) throws KlabValidationException;

        /**
         * 
         * @return
         * @throws KlabValidationException
         */
        IConcept build() throws KlabValidationException;

        /**
         * Return any exceptions accumulated through the building process before build() is called. If build()
         * is called when getErrors() returns a non-empty collection, it will throw the first exception in the
         * list.
         * 
         * @return
         */
        Collection<KlabValidationException> getErrors();

    }

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build(IOntology)} on it.
     * 
     * @param main
     * @return
     */
    Builder declare(IConcept main);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build(IOntology)} on it. This version can be passed
     * a name which can be with or without namespace, and a parent. The concept is only created if it's not
     * there.
     * 
     * @param main
     * @param parent
     * @return
     */
    Builder declare(String main, @NotNull IConcept parent);

    /**
     * Get a builder for a declaration of named observable, which will allow adding traits and clauses and
     * obtain the final concept by calling {@link Builder#build(IOntology)} on it. This version can be passed
     * a name which can be with or without namespace, and a type to establish the core parent. The concept is
     * only created if it's not there.
     * 
     * @param main
     * @param type
     * @return
     */
    Builder declare(String main, @NotNull Set<Type> type);

}
