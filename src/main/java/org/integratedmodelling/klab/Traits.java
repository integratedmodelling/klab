package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.services.ITraitService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.OWL;

public enum Traits implements ITraitService {
    INSTANCE;

    @Override
    public Collection<IConcept> getTraits(IKnowledge concept) {

        Set<IConcept> ret = new HashSet<>();

        if (concept instanceof IConcept) {
            ret.addAll(OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_REALM_PROPERTY)));
            ret.addAll(OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_IDENTITY_PROPERTY)));
            ret.addAll(OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_ATTRIBUTE_PROPERTY)));
        }
        return ret;
    }

    @Override
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

    @Override
    public boolean hasTrait(IConcept type, IConcept trait) {

        for (IConcept c : getTraits(type)) {
            if (c.is(trait)) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public boolean hasParentTrait(IConcept type, IConcept trait) {

        for (IConcept c : getTraits(type)) {
            if (trait.is(c)) {
                return true;
            }
        }

        return false;
    }
    
    public void restrict(IConcept target, IProperty property, LogicalConnector how, Set<IConcept> fillers)
            throws KlabValidationException {

        /*
         * divide up in bins according to base trait; take property from annotation;
         * restrict each group.
         */
        Map<IConcept, List<IConcept>> pairs = new HashMap<>();
        for (IConcept t : fillers) {
            IConcept base = getBaseParentTrait(t);
            if (!pairs.containsKey(base)) {
                pairs.put(base, new ArrayList<>());
            }
            pairs.get(base).add(t);
        }

        for (IConcept base : pairs.keySet()) {

            String prop = base.getMetadata().getString(NS.TRAIT_RESTRICTING_PROPERTY);
            if (prop == null || Concepts.INSTANCE.getProperty(prop) == null) {
                if (base.is(Type.SUBJECTIVE)) {
                    /*
                     * we can assign any subjective traits to anything
                     */
                    prop = NS.HAS_SUBJECTIVE_TRAIT_PROPERTY;
                } else {
                    throw new KlabValidationException("cannot find property to restrict for trait " + base);
                }
            }
            OWL.INSTANCE.restrictSome(target, Concepts.p(prop), how, pairs.get(base));
        }

    }


}
