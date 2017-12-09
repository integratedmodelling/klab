package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement.DescriptionType;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.ObservableBuilder;

public enum Observables implements IObservableService {

    INSTANCE;

    @Override
    public @Nullable IConcept getInherentType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE
                .getRestrictedClasses((IConcept) concept, Concepts.p(NS.IS_INHERENT_TO_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getCompresentType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE
                .getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_COMPRESENT_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }
    
    @Override
    public @Nullable IConcept getCausantType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE
                .getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_CAUSANT_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }
    
    @Override
    public @Nullable IConcept getCausedType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE
                .getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_CAUSED_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }
    
    @Override
    public @Nullable IConcept getGoalType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE
                .getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_PURPOSE_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }
    /**
     * Get the context ('within') for the passed quality or trait. If the passed concept
     * is an attribute, configuration, class or realm, the context is the one specified
     * for the quality/configuration under "describes". For identities and processes,
     * context is found in 'applies to'. If the context is not specified but there is an
     * inherent concept, the context of the inherent, if any, is returned.
     * 
     * @param concept
     * @return the context type, or null.
     */
    @Override
    public @Nullable IConcept getContextType(IConcept concept) {
        return getContextType(concept, true);
    }

    /*
     * FIXME logics should be revised. Check documentation in docs/concepts/introduction.rst too.
     * @param concept
     * @param recurse
     * @return
     */
    private IConcept getContextType(IConcept concept, boolean recurse) {

        IConcept ret = null;
        if (recurse && (concept.is(Type.ATTRIBUTE) || concept.is(Type.REALM) || concept.is(Type.CLASS)
                || concept.is(Type.CONFIGURATION))) {

            List<IConcept> contexts = new ArrayList<>();
            for (IConcept described : getDescribedQualities(concept)) {
                ret = described == null ? null : getContextType(described, false);
            }
            ret = contexts.size() > 0 ? Concepts.INSTANCE.getLeastGeneralCommonConcept(contexts) : null;

        } else if (recurse && (concept.is(Type.IDENTITY) || concept.is(Type.PROCESS))) {
            Collection<IConcept> contexts = getApplicableObservables(concept);
            ret = contexts.size() > 0 ? Concepts.INSTANCE.getLeastGeneralCommonConcept(contexts) : null;
        } else {

            Collection<IConcept> cls = OWL.INSTANCE
                    .getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_CONTEXT_PROPERTY));
            ret = cls.isEmpty() ? null : cls.iterator().next();
        }

        if (ret == null && concept.is(Type.OBSERVABLE)) {
            IConcept inherent = getInherentType(concept);
            if (inherent != null) {
                ret = getContextType(inherent, false);
            }
        }

        return ret;
    }

    public Collection<IConcept> getDescribedQualities(IConcept configuration) {
        List<IConcept> ret = new ArrayList<>();
        ret.addAll(OWL.INSTANCE
                .getRestrictedClasses(configuration, Concepts.p(NS.DESCRIBES_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE
                .getRestrictedClasses(configuration, Concepts.p(NS.PROPORTIONAL_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE
                .getRestrictedClasses(configuration, Concepts.p(NS.INVERSELY_PROPORTIONAL_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE
                .getRestrictedClasses(configuration, Concepts.p(NS.CLASSIFIES_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE
                .getRestrictedClasses(configuration, Concepts.p(NS.DISCRETIZES_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.MARKS_QUALITY_PROPERTY)));
        return ret;
    }

    public Collection<IConcept> getDescribedQualities(IConcept configuration, DescriptionType type) {
        return OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(getDescriptionProperty(type)));
    }

    private String getDescriptionProperty(DescriptionType type) {
        String ret = null;
        switch (type) {
        case CLASSIFIES:
            ret = NS.CLASSIFIES_QUALITY_PROPERTY;
            break;
        case DECREASES_WITH:
            ret = NS.INVERSELY_PROPORTIONAL_QUALITY_PROPERTY;
            break;
        case DESCRIBES:
            ret = NS.CLASSIFIES_QUALITY_PROPERTY;
            break;
        case DISCRETIZES:
            ret = NS.DISCRETIZES_QUALITY_PROPERTY;
            break;
        case INCREASES_WITH:
            ret = NS.PROPORTIONAL_QUALITY_PROPERTY;
            break;
        case MARKS:
            ret = NS.MARKS_QUALITY_PROPERTY;
            break;
        }

        return ret;
    }

    /**
     * True if o1 and o2 are observables from recognized domains, have compatible context
     * and inherency, o1 is o2, and o1 adopts all the traits and roles that o2 adopts.
     * 
     * @param o1
     * @param o2
     * @return
     */
    public boolean isCompatible(IConcept o1, IConcept o2) {
        return isCompatible(o1, o2, 0);
    }

    /**
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, different realms will
     * not determine incompatibility.
     */
    static public final int ACCEPT_REALM_DIFFERENCES      = 0x01;
    /**
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, only types that have
     * the exact same core type will be accepted.
     */
    static public final int REQUIRE_SAME_CORE_TYPE        = 0x02;
    /**
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, types with roles that
     * are more general of the roles in the first concept will be accepted.
     */
    static public final int USE_ROLE_PARENT_CLOSURE       = 0x04;
    /**
     * If passed to {@link #isCompatible(IConcept, IConcept, int)}, types with traits that
     * are more general of the traits in the first concept will be accepted.
     */
    static public final int USE_TRAIT_PARENT_CLOSURE      = 0x08;

    /**
     * If passed to
     * {@link #declareObservable(IConcept, Collection, IConcept, IConcept, Collection, IConcept, IConcept, IOntology, int)}
     * causes acceptance of subjective traits for observables.
     */
    static public final int ACCEPT_SUBJECTIVE_OBSERVABLES = 0x10;

    /**
     * Compatibility from the point of view of observation. True if o1 and o2 are
     * observables from recognized domains, have compatible context and inherency, o1 is
     * o2, and o1 adopts all the traits and roles that o2 adopts.
     * 
     * @param o1
     * @param o2
     * @param flags 
     * @return
     */
    public boolean isCompatible(IConcept o1, IConcept o2, int flags) {

        boolean mustBeSameCoreType = (flags & REQUIRE_SAME_CORE_TYPE) != 0;
        boolean useRoleParentClosure = (flags & USE_ROLE_PARENT_CLOSURE) != 0;
        boolean acceptRealmDifferences = (flags & ACCEPT_REALM_DIFFERENCES) != 0;

        // TODO unsupported
        boolean useTraitParentClosure = (flags & USE_TRAIT_PARENT_CLOSURE) != 0;

        if ((!o1.is(Type.OBSERVABLE) || !o2.is(Type.OBSERVABLE))
                && !(o1.is(Type.CONFIGURATION) && o2.is(Type.CONFIGURATION))) {
            return false;
        }

        IConcept core1 = getCoreObservable(o1);
        IConcept core2 = getCoreObservable(o2);

        if (core1 == null || core2 == null || !(mustBeSameCoreType ? core1.equals(core2) : core1.is(core2))) {
            return false;
        }

        IConcept cc1 = getContextType(o1);
        IConcept cc2 = getContextType(o2);

        if ((cc1 == null && cc1 != null) || (cc1 != null && cc2 == null)) {
            return false;
        }
        if (cc1 != null && cc1 != null) {
            if (!isCompatible(cc1, cc2, ACCEPT_REALM_DIFFERENCES)) {
                return false;
            }
        }

        IConcept ic1 = getInherentType(o1);
        IConcept ic2 = getInherentType(o2);

        if ((ic1 == null && ic1 != null) || (ic1 != null && ic2 == null)) {
            return false;
        }
        if (ic1 != null && ic1 != null) {
            if (!isCompatible(ic1, ic2)) {
                return false;
            }
        }

        for (IConcept t : Traits.INSTANCE.getTraits(o2)) {
            boolean ok = Traits.INSTANCE.hasTrait(o1, t);
            if (!ok && useTraitParentClosure) {
                ok = Traits.INSTANCE.hasParentTrait(o1, t);
            }
            if (!ok) {
                return false;
            }
        }

        for (IConcept t : Roles.INSTANCE.getRoles(o2)) {
            boolean ok = Roles.INSTANCE.hasRole(o1, t);
            if (!ok && useRoleParentClosure) {
                ok = Roles.INSTANCE.hasParentRole(o1, t);
            }
            if (!ok) {
                return false;
            }
        }

        return true;
    }

    @Override
    public IConcept getCoreObservable(IConcept c) {
        String def = c.getMetadata().getString(NS.CORE_OBSERVABLE_PROPERTY);
        return def == null ? c : Concepts.c(def);
    }

    @Override
    public Collection<IConcept> getApplicableObservables(IConcept main) {
        return OWL.INSTANCE.getRestrictedClasses((IConcept) main, Concepts.p(NS.APPLIES_TO_PROPERTY));
    }

    @Override
    public Builder declare(IConcept main, IOntology ontology) {
        return new ObservableBuilder(main, ontology);
    }

    @Override
    public Builder declare(String main, IConcept parent, IOntology ontology) {
        return new ObservableBuilder(main, parent, ontology);
    }

    @Override
    public Builder declare(String main, Set<Type> type, IOntology ontology) {
        return new ObservableBuilder(main, type, ontology);
    }

    @Override
    public Builder declare(IConcept main) {
        return declare(main, Configuration.INSTANCE.useCommonOntology() ? Reasoner.INSTANCE.getOntology()
                : null);
    }

    @Override
    public Builder declare(String main, IConcept parent) {
        return declare(main, parent, Configuration.INSTANCE.useCommonOntology()
                ? Reasoner.INSTANCE.getOntology()
                : null);
    }

    @Override
    public Builder declare(String main, Set<Type> type) {
        return declare(main, type, Configuration.INSTANCE.useCommonOntology()
                ? Reasoner.INSTANCE.getOntology()
                : null);
    }

}
