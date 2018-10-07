package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.IConcept;

public interface ITraitService {

    /**
     * Return all traits, i.e. identities, attributes and realms.
     *
     * @param concept a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<IConcept> getTraits(IConcept concept);


    /**
     * Return all identities.
     * 
     * @param concept
     * @return identities
     */
	Collection<IConcept> getIdentities(IConcept concept);

	/**
	 * Return all attributes
	 * @param concept
	 * @return attributes
	 */
	Collection<IConcept> getAttributes(IConcept concept);

	/**
	 * Return all realms.
	 * 
	 * @param concept
	 * @return realms
	 */
	Collection<IConcept> getRealms(IConcept concept);
    
    /**
     * <p>getBaseParentTrait.</p>
     *
     * @param trait a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     */
    IConcept getBaseParentTrait(IConcept trait);

    /**
     * Check if concept k carries the passed trait. Uses is() on all explicitly expressed
     * traits.
     *
     * @param type a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @param trait a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a boolean.
     */
    boolean hasTrait(IConcept type, IConcept trait);

    /**
     * Check if concept k carries a trait T so that the passed trait is-a T.
     *
     * @param type a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @param trait a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return a boolean.
     */
    boolean hasParentTrait(IConcept type, IConcept trait);


    /**
     * Like {@link #getTraits(IConcept)} but only returns the traits directly attributed to
     * this concept.
     * 
     * @param concept
     * @return
     */
	Collection<IConcept> getDirectTraits(IConcept concept);

	/**
	 * Return the concept this has been asserted to be the negation of, or null.
	 * 
	 * @param concept
	 * @return
	 */
    IConcept getNegated(IConcept concept);


}
