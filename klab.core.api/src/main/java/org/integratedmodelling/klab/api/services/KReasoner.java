package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.KSemantics;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.knowledge.observation.KObservation;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;

public interface KReasoner {

    /**
     * All services publish capabilities and have a call to obtain them.
     * 
     * @author Ferd
     *
     */
    interface Capabilities {

    }

    Capabilities getCapabilities();

    /**
     * 
     * @param definition
     * @return
     */
    KConcept resolveConcept(String definition);

    /**
     * 
     * @param definition
     * @return
     */
    KObservable resolveObservable(String definition);

    Collection<KConcept> operands(KSemantics target);

    Collection<KConcept> children(KSemantics target);

    Collection<KConcept> parents(KSemantics target);

    Collection<KConcept> allChildren(KSemantics target);

    Collection<KConcept> allParents(KSemantics target);

    Collection<KConcept> closure(KSemantics target);

    int semanticDistance(KSemantics target);

    int semanticDistance(KSemantics target, KSemantics context);

    KConcept coreObservable(KConcept first);

    Pair<KConcept, List<SemanticType>> splitOperators(KConcept concept);

    int assertedDistance(KConcept kConcept, KConcept t);

    Collection<KConcept> roles(KConcept concept);

    boolean hasRole(KConcept concept, KConcept t);

    KConcept directContext(KConcept concept);

    KConcept context(KConcept concept);

    KConcept directInherent(KConcept concept);

    KConcept inherent(KConcept concept);

    KConcept directGoal(KConcept concept);

    KConcept goal(KConcept concept);

    KConcept directCooccurrent(KConcept concept);

    KConcept directCausant(KConcept concept);

    KConcept directCaused(KConcept concept);

    KConcept directAdjacent(KConcept concept);

    KConcept directCompresent(KConcept concept);

    KConcept directRelativeTo(KConcept concept);

    KConcept cooccurrent(KConcept concept);

    KConcept causant(KConcept concept);

    KConcept caused(KConcept concept);

    KConcept adjacent(KConcept concept);

    KConcept compresent(KConcept concept);

    KConcept relativeTo(KConcept concept);

    Object displayLabel(KSemantics concept);

    Object codeName(KSemantics concept);

    String style(KConcept concept);

    /**
     * Return all traits, i.e. identities, attributes and realms.
     *
     * @param concept
     * @return
     */
    Collection<KConcept> traits(KConcept concept);

    /**
     * Return all identities.
     * 
     * @param concept
     * @return identities
     */
    Collection<KConcept> identities(KConcept concept);

    /**
     * Return all attributes
     * 
     * @param concept
     * @return attributes
     */
    Collection<KConcept> attributes(KConcept concept);

    /**
     * Return all realms.
     * 
     * @param concept
     * @return realms
     */
    Collection<KConcept> realms(KConcept concept);

    /**
     * <p>
     * getBaseParentTrait.
     * </p>
     *
     * @param trait
     * @return
     */
    KConcept baseParentTrait(KConcept trait);

    /**
     * Check if concept k carries the passed trait. Uses is() on all explicitly expressed traits.
     *
     * @param type
     * @param trait
     * @return a boolean.
     */
    boolean hasTrait(KConcept type, KConcept trait);

    /**
     * Check if concept k carries a trait T so that the passed trait is-a T.
     *
     * @return a boolean.
     */
    boolean hasParentTrait(KConcept type, KConcept trait);

    /**
     * Like {@link #traits(KConcept)} but only returns the traits directly attributed to this
     * concept.
     * 
     * @param concept
     * @return
     */
    Collection<KConcept> directTraits(KConcept concept);

    /**
     * Like {@link #traits(KConcept)} but only returns the traits directly attributed to this
     * concept.
     * 
     * @param concept
     * @return
     */
    Collection<KConcept> directRoles(KConcept concept);

    
    /**
     * Return the Java class of the observation type corresponding to the passed observable.
     *
     * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
     * @return a {@link java.lang.Class} object.
     */
    Class<? extends KObservation> observationClass(KObservable observable);

    /**
     * Return the base enum type (quality, subject....) for the passed observable.
     *
     * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
     * @param acceptTraits if true, will return a trait type (which can be the observable of a class
     *        model although it's not an observable per se).
     * @throws java.lang.IllegalArgumentException
     * 
     *         if not an observable
     * @return the enum type
     */
    SemanticType observableType(KObservable observable, boolean acceptTraits);

    /**
     * Return the asserted source of the relationship, assuming it is unique. If it is not unique,
     * the result is arbitrary among the possible sources.
     * 
     * @param relationship a relationship concept
     * @return the source. May be null in abstract relationships.
     */
    KConcept relationshipSource(KConcept relationship);

    /**
     * Return all the asserted sources of the relationship.
     * 
     * @param relationship a relationship concept
     * @return the sources. May be empty in abstract relationships.
     */
    Collection<KConcept> relationshipSources(KConcept relationship);

    /**
     * Return the asserted target of the relationship, assuming it is unique. If it is not unique,
     * the result is arbitrary among the possible targets.
     * 
     * @param relationship a relationship concept
     * @return the target. May be null in abstract relationships.
     */
    KConcept relationshipTarget(KConcept relationship);

    /**
     * Return all the asserted targets of the relationship.
     * 
     * @param relationship a relationship concept
     * @return the targets. May be empty in abstract relationships.
     */
    Collection<KConcept> relationshipTargets(KConcept relationship);

    /**
     * Return the concept this has been asserted to be the negation of, or null.
     * 
     * @param concept
     * @return
     */
    KConcept negated(KConcept concept);

    /**
     * 
     * @param ret
     * @return
     */
    boolean satisfiable(KConcept ret);

    /**
     * Get all the restricted target of the "applies to" specification for this concept.
     *
     * @param main a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return all applicable concepts or an empty collection
     */
    Collection<KConcept> applicableObservables(KConcept main);

    interface Admin {

        /**
         * The "port" to ingest a wordview, available only to admins. Also makes it possible for the
         * resolver to declare local concepts as long as it owns the semantic service. Definition
         * must be made only in terms of known concepts (no forward declaration is allowed), so
         * order of ingestion is critical.
         * 
         * @param statement
         * @return
         */
        KConcept addConcept(KKimConceptStatement statement);

    }

}
