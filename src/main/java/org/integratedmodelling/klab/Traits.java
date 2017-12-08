package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.services.ITraitService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.owl.OWL;

public enum Traits implements ITraitService {
    INSTANCE;

    public Collection<IConcept> getTraits(IKnowledge concept) {

        Set<IConcept> ret = new HashSet<>();

        if (concept instanceof IConcept) {
            ret.addAll(OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_REALM_PROPERTY)));
            ret.addAll(OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_IDENTITY_PROPERTY)));
            ret.addAll(OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_ATTRIBUTE_PROPERTY)));
        }
        return ret;
    }

    public IConcept getBaseParentTrait(IConcept trait) {

        String orig = trait.getMetadata().getString(NS.ORIGINAL_TRAIT);
        if (orig != null) {
            trait = Concepts.c(orig);
        }
        
        /*
         * there should only be one of these or none.
         */
        if (trait.getMetadata().get(NS.BASE_DECLARATION) != null) {
            return trait;
        }

        for (IConcept c : trait.getAllParents()) {
            IConcept r = getBaseParentTrait(c);
            if (r != null) {
                return r;
            }
        }
        return null;
    }
    

    /**
     * Check if concept k carries the passed trait. Uses is() on all explicitly expressed
     * traits.
     * @param type 
     * @param trait 
     * 
     * @return
     */
    public boolean hasTrait(IConcept type, IConcept trait) {

        for (IConcept c : getTraits(type)) {
            if (c.is(trait)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Check if concept k carries a trait T so that the passed trait is-a T.
     * @param type 
     * @param trait 
     *
     * @return
     */
    public boolean hasParentTrait(IConcept type, IConcept trait) {

        for (IConcept c : getTraits(type)) {
            if (trait.is(c)) {
                return true;
            }
        }

        return false;
    }

}
