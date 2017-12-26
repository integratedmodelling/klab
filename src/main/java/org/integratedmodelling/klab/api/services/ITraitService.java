package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;

public interface ITraitService {

    Collection<IConcept> getTraits(IKnowledge concept);

    IConcept getBaseParentTrait(IConcept trait);

    /**
     * Check if concept k carries the passed trait. Uses is() on all explicitly expressed
     * traits.
     * @param type 
     * @param trait 
     * 
     * @return
     */
    boolean hasTrait(IConcept type, IConcept trait);

    /**
     * Check if concept k carries a trait T so that the passed trait is-a T.
     * @param type 
     * @param trait 
     *
     * @return
     */
    boolean hasParentTrait(IConcept type, IConcept trait);

}
