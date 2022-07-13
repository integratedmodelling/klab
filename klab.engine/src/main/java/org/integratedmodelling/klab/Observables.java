package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimConceptStatement.DescriptionType;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.kim.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimObservable;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.ConfigurationDetector;
import org.integratedmodelling.klab.owl.KimKnowledgeProcessor;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.owl.Ontology;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.xtext.KimInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Observables implements IObservableService {

    INSTANCE;

    ConfigurationDetector configurationDetector = new ConfigurationDetector();

    /**
     * Describes a configuration for the configuration detector.
     * 
     * @author Ferd
     *
     */
    static class Configuration {
        Set<IConcept> targets = new HashSet<>();
        IConcept configuration;
        BinarySemanticOperator connector = null;
    }

    private Map<String, Configuration> configurations = Collections.synchronizedMap(new LinkedHashMap<String, Configuration>());

    @Inject
    ParseHelper<Model> observableParser;

    private Observables() {
        IInjectorProvider injectorProvider = new KimInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
        Services.INSTANCE.registerService(this, IObservableService.class);
    }

    @Override
    public IObservable declare(String declaration) {

        IMonitor monitor = Klab.INSTANCE.getRootMonitor();
        try {
            ObservableSemantics parsed = observableParser.parse(declaration).getObservable();
            KimObservable interpreted = Kim.INSTANCE.declareObservable(parsed);
            return KimKnowledgeProcessor.INSTANCE.declare(interpreted, Reasoner.INSTANCE.getOntology(), monitor);
        } catch (Exception e) {
            monitor.error(e, declaration);
        }

        return null;
    }

    @Override
    public IKimObservable parseDeclaration(String declaration) {
        IMonitor monitor = Klab.INSTANCE.getRootMonitor();
        try {
            ObservableSemantics parsed = observableParser.parse(declaration).getObservable();
            return Kim.INSTANCE.declareObservable(parsed);
        } catch (Exception e) {
            monitor.error(e, declaration);
        }

        return null;
    }

    @Override
    public Observable declare(IKimObservable observable, IMonitor monitor) {
        return KimKnowledgeProcessor.INSTANCE.declare(observable, Reasoner.INSTANCE.getOntology(), monitor);
    }

    @Override
    public @Nullable IConcept getInherentType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.IS_INHERENT_TO_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getComparisonType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.IS_COMPARED_TO_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getDescribedType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept,
                Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getCompresentType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_COMPRESENT_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getCausantType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_CAUSANT_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getCausedType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_CAUSED_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getGoalType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_PURPOSE_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getAdjacentType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.IS_ADJACENT_TO_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getCooccurrentType(IConcept concept) {
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.OCCURS_DURING_PROPERTY));
        return cls.isEmpty() ? null : cls.iterator().next();
    }

    @Override
    public @Nullable IConcept getDirectInherentType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.IS_INHERENT_TO_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectCompresentType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.HAS_COMPRESENT_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectCausantType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.HAS_CAUSANT_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectCausedType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.HAS_CAUSED_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectGoalType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.HAS_PURPOSE_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectAdjacentType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.IS_ADJACENT_TO_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectCooccurrentType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.OCCURS_DURING_PROPERTY));
    }

    @Override
    public @Nullable IConcept getDirectContextType(IConcept concept) {
        return OWL.INSTANCE.getDirectRestrictedClass((IConcept) concept, Concepts.p(NS.HAS_CONTEXT_PROPERTY));
    }

    /**
     * Get the context ('within') for the passed quality or trait. If the passed concept is an
     * attribute, configuration, class or realm, the context is the one specified for the
     * quality/configuration under "describes". For identities and processes, context is found in
     * 'applies to'. If the context is not specified but there is an inherent concept, the context
     * of the inherent, if any, is returned.
     * <p>
     * This one returns either the explicitly set context or the implied one, ensuring that
     * {@link #getDirectContextType(IConcept)} can work correctly using only the directly stated
     * context.
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
     * 
     * @param concept
     * 
     * @param recurse
     * 
     * @return
     */
    private IConcept getContextType(IConcept concept, boolean recurse) {

        /*
         * try the kosher context first
         */
        Collection<IConcept> cls = OWL.INSTANCE.getRestrictedClasses((IConcept) concept, Concepts.p(NS.HAS_CONTEXT_PROPERTY));
        IConcept ret = cls.isEmpty() ? null : cls.iterator().next();

        /*
         * context of the inherent type is also our context if any
         */
        if (ret == null) {
            IConcept inherent = getInherentType(concept);
            if (inherent != null) {
                ret = getContextType(inherent, false);
            }
        }

        /*
         * if we apply to something, that is our context unless we specified otherwise
         */
        if (ret == null && recurse) {
            Collection<IConcept> contexts = getApplicableObservables(concept);
            ret = contexts.size() > 0 ? Concepts.INSTANCE.getLeastGeneralCommonConcept(contexts) : null;
        }

        /*
         * last resort, if we describe something with a context, that's our context too
         */
        if (ret == null && recurse) {
            List<IConcept> contexts = new ArrayList<>();
            for (IConcept described : getDescribedQualities(concept)) {
                ret = described == null ? null : getContextType(described, false);
            }
            ret = contexts.size() > 0 ? Concepts.INSTANCE.getLeastGeneralCommonConcept(contexts) : null;
        }

        return ret;
    }

    public Collection<IConcept> getDescribedQualities(IConcept configuration) {
        List<IConcept> ret = new ArrayList<>();
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.DESCRIBES_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.PROPORTIONAL_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.INVERSELY_PROPORTIONAL_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.CLASSIFIES_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.DISCRETIZES_QUALITY_PROPERTY)));
        ret.addAll(OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(NS.MARKS_QUALITY_PROPERTY)));
        return ret;
    }

    public Collection<IConcept> getDescribedQualities(IConcept configuration, DescriptionType type) {
        return OWL.INSTANCE.getRestrictedClasses(configuration, Concepts.p(getDescriptionProperty(type)));
    }

    public Collection<IConcept> getRequiredIdentities(IConcept observable) {
        Set<IConcept> ret = new HashSet<>();
        for (IConcept c : OWL.INSTANCE.getRestrictedClasses(observable, Concepts.p(NS.REQUIRES_IDENTITY_PROPERTY))) {
            if (!Concepts.INSTANCE.isInternal(c)) {
                ret.add(c);
            }
        }
        return ret;
    }

    /**
     * Get all qualities, processes or events affected by a process, <em>including</em> those
     * created by it.
     * 
     * @param process
     * @return
     */
    public Collection<IConcept> getAffected(ISemantic process) {
        Set<IConcept> ret = new HashSet<>();
        for (IConcept c : OWL.INSTANCE.getRestrictedClasses(process.getType(), Concepts.p(NS.AFFECTS_PROPERTY))) {
            if (!Concepts.INSTANCE.isInternal(c)) {
                ret.add(c);
            }
        }
        for (IConcept c : OWL.INSTANCE.getRestrictedClasses(process.getType(), Concepts.p(NS.CREATES_PROPERTY))) {
            if (!Concepts.INSTANCE.isInternal(c)) {
                ret.add(c);
            }
        }
        return ret;
    }

    /**
     * Get <em>only</em> the qualities, processes or events created by a process.
     * 
     * @param process
     * @return
     */
    public Collection<IConcept> getCreated(ISemantic process) {
        Set<IConcept> ret = new HashSet<>();
        for (IConcept c : OWL.INSTANCE.getRestrictedClasses(process.getType(), Concepts.p(NS.CREATES_PROPERTY))) {
            if (!Concepts.INSTANCE.isInternal(c)) {
                ret.add(c);
            }
        }
        return ret;
    }

    private String getDescriptionProperty(DescriptionType type) {
        String ret = null;
        switch(type) {
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
     * Check for compatibility of context1 and context2 as the context for an observation of focus
     * (i.e., focus can be observed by an observation process that happens in context1). Works like
     * isCompatible, but if context1 is an occurrent, it will let through situations where it
     * affects focus in whatever context it is, or where the its own context is the same as
     * context2, thereby there is a common context to refer to.
     * 
     * @param focus the focal observable whose context we are checking
     * @param context1 the specific context of the observation (model) that will observe focus
     * @param context2 the mandated context of focus
     * 
     * @return true if focus can be observed by an observation process that happens in context1.
     */
    public boolean isContextuallyCompatible(IConcept focus, IConcept context1, IConcept context2) {
        boolean ret = isCompatible(context1, context2, 0);
        if (!ret && isOccurrent(context1)) {
            ret = isAffectedBy(focus, context1);
            IConcept itsContext = getContext(context1);
            if (!ret) {
                if (itsContext != null) {
                    ret = isCompatible(itsContext, context2);
                }
            }
        }
        return ret;
    }

    @Override
    public boolean isCompatible(IConcept o1, IConcept o2) {
        return isCompatible(o1, o2, 0);
    }

    @Override
    public boolean isCompatible(IConcept o1, IConcept o2, int flags) {

        if (o1 == o2 || o1.equals(o2)) {
            return true;
        }

        boolean mustBeSameCoreType = (flags & REQUIRE_SAME_CORE_TYPE) != 0;
        boolean useRoleParentClosure = (flags & USE_ROLE_PARENT_CLOSURE) != 0;
        // boolean acceptRealmDifferences = (flags & ACCEPT_REALM_DIFFERENCES) != 0;

        // TODO unsupported
        boolean useTraitParentClosure = (flags & USE_TRAIT_PARENT_CLOSURE) != 0;

        if ((!o1.is(Type.OBSERVABLE) || !o2.is(Type.OBSERVABLE)) && !(o1.is(Type.CONFIGURATION) && o2.is(Type.CONFIGURATION))) {
            return false;
        }

        IConcept core1 = getCoreObservable(o1);
        IConcept core2 = getCoreObservable(o2);

        if (core1 == null || core2 == null || !(mustBeSameCoreType ? core1.equals(core2) : core1.is(core2))) {
            return false;
        }

        IConcept cc1 = getContextType(o1.getType());
        IConcept cc2 = getContextType(o2);

        // candidate may have no context; if both have them, they must be compatible
        if (cc1 == null && cc2 != null) {
            return false;
        }
        if (cc1 != null && cc2 != null) {
            if (!isCompatible(cc1, cc2, ACCEPT_REALM_DIFFERENCES)) {
                return false;
            }
        }

        IConcept ic1 = getInherentType(o1);
        IConcept ic2 = getInherentType(o2);

        // same with inherency
        if (ic1 == null && ic2 != null) {
            return false;
        }
        if (ic1 != null && ic2 != null) {
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

    /**
     * Get the first base observable without direct traits, roles, and any modifiers. Remove
     * operators if there are any.
     * 
     * @param c
     * @return
     */
    public IConcept getBaseObservable(IConcept c) {
        Collection<IConcept> traits = Traits.INSTANCE.getDirectTraits(c);
        Collection<IConcept> roles = Roles.INSTANCE.getDirectRoles(c);
        if (traits.size() == 0 && roles.size() == 0 && !Concepts.INSTANCE.isDerived(c)) {
            return c;
        }
        return getBaseObservable(c.getParent());
    }

    /**
     * Remove any attribute or explicit restriction and return the raw observable, without digging
     * down to the core definition.
     * 
     * @param c
     * @return
     */
    public IConcept getRawObservable(IConcept c) {
        String def = c.getMetadata().get(NS.CORE_OBSERVABLE_PROPERTY, String.class);
        IConcept ret = c;
        if (def != null) {
            ret = Concepts.c(def);
        }
        return ret;
    }

    @Override
    public IConcept getCoreObservable(IConcept c) {
        String def = c.getMetadata().get(NS.CORE_OBSERVABLE_PROPERTY, String.class);
        IConcept ret = c;
        while(def != null) {
            ret = Concepts.c(def);
            if (ret.getMetadata().get(NS.CORE_OBSERVABLE_PROPERTY) != null && !ret.getDefinition().equals(def)) {
                def = ret.getMetadata().get(NS.CORE_OBSERVABLE_PROPERTY, String.class);
            } else {
                break;
            }
        }
        return ret;
    }

    @Override
    public Collection<IConcept> getApplicableObservables(IConcept main) {
        return OWL.INSTANCE.getRestrictedClasses((IConcept) main, Concepts.p(NS.APPLIES_TO_PROPERTY));
    }

    @Override
    public Class<? extends IObservation> getObservationClass(IObservable observable) {
        if (observable.getType().is(Type.SUBJECT)) {
            return ISubject.class;
        } else if (observable.getType().is(Type.QUALITY)) {
            return IState.class;
        } else if (observable.getType().is(Type.EVENT)) {
            return IEvent.class;
        } else if (observable.getType().is(Type.PROCESS)) {
            return IProcess.class;
        } else if (observable.getType().is(Type.RELATIONSHIP)) {
            return IRelationship.class;
        } else if (observable.getType().is(Type.CONFIGURATION)) {
            return IConfiguration.class;
        }
        return null;
    }

    @Override
    public Class<? extends IObservation> getObservationClass(IResolvable resolvable) {
        if (resolvable instanceof IObservable) {
            return getObservationClass((IObservable) resolvable);
        } else if (resolvable instanceof IAcknowledgement) {
            return getObservationClass(((IAcknowledgement) resolvable).getObservable());
        } else if (resolvable instanceof IModel) {
            return getObservationClass(((IModel) resolvable).getObservables().get(0));
        }
        return null;
    }

    @Override
    public Type getObservableType(IObservable observable, boolean acceptTraits) {
        if (observable.getArtifactType().equals(IArtifact.Type.VOID)) {
            return Type.NOTHING;
        }
        EnumSet<Type> type = EnumSet.copyOf(((Concept) observable.getType()).getTypeSet());
        type.retainAll(IKimConcept.BASE_MODELABLE_TYPES);
        if (type.size() != 1) {
            throw new IllegalArgumentException("trying to extract the observable type from non-observable " + observable);
        }
        return type.iterator().next();
    }

    @Override
    public IConcept getRelationshipSource(IConcept relationship) {
        Collection<IConcept> ret = getRelationshipSources(relationship);
        return ret.size() == 0 ? null : ret.iterator().next();
    }

    @Override
    public IConcept getRelationshipTarget(IConcept relationship) {
        Collection<IConcept> ret = getRelationshipTargets(relationship);
        return ret.size() == 0 ? null : ret.iterator().next();
    }

    @Override
    public Collection<IConcept> getRelationshipSources(IConcept relationship) {
        return CollectionUtils.join(OWL.INSTANCE.getDirectRestrictedClasses(relationship, Concepts.p(NS.IMPLIES_SOURCE_PROPERTY)),
                OWL.INSTANCE.getRestrictedClasses(relationship, Concepts.p(NS.IMPLIES_SOURCE_PROPERTY)));
    }

    @Override
    public Collection<IConcept> getRelationshipTargets(IConcept relationship) {
        return CollectionUtils.join(
                OWL.INSTANCE.getDirectRestrictedClasses(relationship, Concepts.p(NS.IMPLIES_DESTINATION_PROPERTY)),
                OWL.INSTANCE.getRestrictedClasses(relationship, Concepts.p(NS.IMPLIES_DESTINATION_PROPERTY)));
    }

    /**
     * Set the "applied to" clause for a trait or observable. Should also validate.
     * 
     * @param type
     * @param applicables
     */
    public void setApplicableObservables(IConcept type, List<IConcept> applicables, Ontology ontology) {
        // TODO validate
        OWL.INSTANCE.restrictSome(type, Concepts.p(NS.APPLIES_TO_PROPERTY), LogicalConnector.UNION, applicables, ontology);
    }

    public void defineRelationship(Concept relationship, IConcept source, IConcept target, Ontology ontology) {
        IProperty hasSource = Concepts.p(NS.IMPLIES_SOURCE_PROPERTY);
        IProperty hasTarget = Concepts.p(NS.IMPLIES_DESTINATION_PROPERTY);
        OWL.INSTANCE.restrictSome(relationship, hasSource, LogicalConnector.UNION, Collections.singleton(source), ontology);
        OWL.INSTANCE.restrictSome(relationship, hasTarget, LogicalConnector.UNION, Collections.singleton(target), ontology);
    }

    // /**
    // * Copy all the observation logical context (inherent, context, caused, ...
    // * including traits and roles) from a concept to another. Assumes that the
    // * target concept has none of these.
    // *
    // * @param type
    // * @param target
    // */
    // public void copyContext(IConcept type, IConcept target, Ontology ontology) {
    //
    // IConcept inherent = getInherentType(type);
    // IConcept context = getContextType(type);
    // IConcept goal = getGoalType(type);
    // IConcept causant = getCausantType(type);
    // IConcept caused = getCausedType(type);
    // IConcept compresent = getCompresentType(type);
    // IConcept adjacent = getAdjacentType(type);
    // IConcept described = getDescribedType(type);
    //
    // if (inherent != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.IS_INHERENT_TO_PROPERTY),
    // inherent,
    // ontology);
    // }
    // if (context != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.HAS_CONTEXT_PROPERTY),
    // context, ontology);
    // }
    // if (caused != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.HAS_CAUSED_PROPERTY), caused,
    // ontology);
    // }
    // if (causant != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.HAS_CAUSANT_PROPERTY),
    // causant, ontology);
    // }
    // if (compresent != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.HAS_COMPRESENT_PROPERTY),
    // compresent,
    // ontology);
    // }
    // if (goal != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.HAS_PURPOSE_PROPERTY), goal,
    // ontology);
    // }
    // if (adjacent != null) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.IS_ADJACENT_TO_PROPERTY),
    // adjacent,
    // ontology);
    // }
    // if (described != null) {
    // OWL.INSTANCE.restrictSome(target,
    // Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), described,
    // ontology);
    // }
    //
    // Collection<IConcept> identities = Traits.INSTANCE.getIdentities(type);
    // Collection<IConcept> realms = Traits.INSTANCE.getRealms(type);
    // Collection<IConcept> attributes = Traits.INSTANCE.getAttributes(type);
    // Collection<IConcept> acceptedRoles = Roles.INSTANCE.getRoles(type);
    //
    // if (identities.size() > 0) {
    // Traits.INSTANCE.restrict(target, Concepts.p(NS.HAS_IDENTITY_PROPERTY),
    // LogicalConnector.UNION, identities,
    // ontology);
    // }
    // if (realms.size() > 0) {
    // Traits.INSTANCE.restrict(target, Concepts.p(NS.HAS_REALM_PROPERTY),
    // LogicalConnector.UNION,
    // realms,
    // ontology);
    // }
    // if (attributes.size() > 0) {
    // Traits.INSTANCE.restrict(target, Concepts.p(NS.HAS_ATTRIBUTE_PROPERTY),
    // LogicalConnector.UNION, attributes,
    // ontology);
    // }
    // if (acceptedRoles.size() > 0) {
    // OWL.INSTANCE.restrictSome(target, Concepts.p(NS.HAS_ROLE_PROPERTY),
    // LogicalConnector.UNION,
    // acceptedRoles,
    // ontology);
    // }
    // }

    /**
     * Register any configuration directly or through inherency.
     * 
     * @param concept
     * @deprecated
     */
    public void registerConfigurations(IConcept concept) {

        if (concept.is(Type.CONFIGURATION)) {
            registerConfiguration(concept);
        } else {
            IConcept inherent = getInherentType(concept);
            if (inherent != null && inherent.is(Type.CONFIGURATION)) {
                Configuration descriptor = configurations.get(inherent.getDefinition());
                if (descriptor == null) {
                    registerConfiguration(inherent);
                    descriptor = configurations.get(inherent.getDefinition());
                }
                boolean found = false;
                if (descriptor != null) {
                    for (IConcept target : descriptor.targets) {
                        if (concept.equals(target)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found && descriptor != null) {
                    descriptor.targets.add(concept);
                }
            }
        }
    }

    @Deprecated
    private void registerConfiguration(IConcept concept) {
        // register the configuration with the configuration detector
        if (!concept.isAbstract()) {
            IConcept inherent = getInherentType(concept);
            if (inherent != null) {
                // TODO separate out operands if it's a union or intersection
                Configuration descriptor = new Configuration();
                descriptor.targets.add(inherent);
                descriptor.configuration = concept;
                configurations.put(concept.getDefinition(), descriptor);
            }
        }
        for (IConcept child : concept.getChildren()) {
            registerConfiguration(child);
        }
    }

    class ConfigurationMatch {
        public ConfigurationMatch(IConcept configuration, int nt, int sd) {
            this.configuration = configuration;
            this.nTargets = nt;
            this.totalDistance = sd;
        }

        int nTargets;
        int totalDistance;
        IConcept configuration;
    }

    /**
     * Configuration detector. Called after each instantiation to examine the known configurations
     * and allow the instantiation of a configuration observation for any that matches.
     * 
     * Configurations are detected using decreasing specificity for the target concepts.
     * 
     * @param instances
     * @param context
     * @return the matching configuration info, including the configuration concept and the
     *         observation targets in the context, or null.
     */
    public Pair<IConcept, Set<IObservation>> detectConfigurations(IObservation instances, IDirectObservation context) {

        List<ConfigurationMatch> matches = new ArrayList<>();
        for (Configuration configuration : configurations.values()) {
            int nt = 0;
            int sd = 0;
            for (IConcept target : configuration.targets) {
                if (instances.getObservable().getType().is(target)) {
                    nt++;
                    sd += Concepts.INSTANCE.getAssertedDistance(instances.getObservable().getType(), target);
                }
            }
            if (nt > 0) {
                matches.add(new ConfigurationMatch(configuration.configuration, nt, sd));
            }
        }

        if (matches.size() > 0) {
            // Sort matches by decreasing ntargets and increasing distance; return first in
            // list
            matches.sort(new Comparator<ConfigurationMatch>(){

                @Override
                public int compare(ConfigurationMatch o1, ConfigurationMatch o2) {
                    if (o1.nTargets == o2.nTargets) {
                        return Integer.compare(o1.totalDistance, o2.totalDistance);
                    }
                    return Integer.compare(o2.nTargets, o1.nTargets);
                }
            });

            ConfigurationMatch first = matches.iterator().next();

            Set<IObservation> targets = new HashSet<>();
            targets.add(instances);
            Configuration configuration = configurations.get(first.configuration.getDefinition());
            for (IConcept c : configuration.targets) {
                for (IObservation o : context.getChildren(IObservation.class)) {
                    if (o.getObservable().is(c)) {
                        targets.add(o);
                    }
                }
            }
            return new Pair<>(first.configuration, targets);
        }

        return null;
    }

    @Override
    public Observable contextualizeTo(IObservable observable, IConcept newContext, boolean isExplicit, IMonitor monitor) {

        if (!OWL.INSTANCE.isSemantic(observable)) {
            return (Observable) observable;
        }

        IConcept originalContext = getContextType(observable.getType());
        if (originalContext != null && originalContext.equals(newContext)) {
            return (Observable) observable;
        }
        if (originalContext != null) {
            if (!isCompatible(newContext, originalContext)) {
                throw new IllegalStateException("cannot contextualize " + observable + " to " + newContext);
            }
        }

        String originalName = observable.getName();
        String originalReferenceName = observable.getReferenceName();

        /*
         * Direct observables can be contextualized to anything and to nothing, so just check
         * compatibility.
         */
        if (!isExplicit || observable.is(Type.DIRECT_OBSERVABLE)) {
            return (Observable) observable;
        }

        return (Observable) new ObservableBuilder((Observable) observable, monitor).withUrl(((Observable) observable).getUrl())
                .within(newContext).named(originalName, originalReferenceName).buildObservable();
    }

    public String getDisplayName(IObservable observable) {
        String ret = observable.getName();
        int dollar = ret.indexOf('$');
        if (dollar >= 0) {
            // results from disambiguation; revert to original name
            ret = ret.substring(0, dollar);
        }
        return ret;

    }

    /**
     * True if the passed concept has been tagged with a distributed inherency declaration (of
     * each).
     * 
     * @param candidate
     * @return
     */
    public boolean hasDistributedInherency(IConcept candidate) {
        return candidate.getMetadata().get(NS.INHERENCY_IS_DISTRIBUTED, " false").equals("true");
    }

    /**
     * True if affecting affects affected. Uses inference when checking. Also true if the concept is
     * a quality describing anything that is affected.
     * 
     * @param affected
     * @param affecting
     * @return true if affected.
     */
    public boolean isAffectedBy(ISemantic affected, ISemantic affecting) {
        IConcept described = getDescribedType(affected.getType());
        for (IConcept c : getAffected(affecting.getType())) {
            if (affected.getType().is(c) || (described != null && described.getType().is(c))) {
                return true;
            }
        }
        return false;
    }

    /**
     * True if affecting creates affected. Uses inference when checking. Also true if the concept is
     * a quality describing anything that is created or the affecting type itself (this last
     * condition only holds for created, as the affecting type, an occurrent, must occur for its
     * derived quality to exist).
     * 
     * @param affected
     * @param affecting
     * @return true if created.
     */
    public boolean isCreatedBy(ISemantic affected, ISemantic affecting) {
        IConcept described = getDescribedType(affected.getType());
        if (described != null && described.getType().is(affecting)) {
            return true;
        }
        for (IConcept c : getCreated(affecting.getType())) {
            if (affected.getType().is(c) || (described != null && described.getType().is(c))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IConcept getContext(IConcept concept) {
        IConcept ret = getDirectContextType(concept);
        if (ret != null) {
            return ret;
        }
        ret = getContextType(concept);
        IConcept inherent = getDirectInherentType(concept);
        return inherent != null && ret != null && inherent.is(ret) ? null : ret;
    }

    @Override
    public IConcept getInherency(IConcept concept) {
        IConcept ret = getDirectInherentType(concept);
        if (ret != null) {
            return ret;
        }
        return getInherentType(concept);
    }

    public String describe(IConcept concept) {

        IConcept described = Observables.INSTANCE.getDescribedType(concept);
        IConcept comparison = Observables.INSTANCE.getComparisonType(concept);

        String ret = "";
        ret += " OWL identifier: " + concept + " (may not be unique)\n";
        ret += "k.IM definition: " + concept.getDefinition() + "\n";
        ret += "Core observable: " + Observables.INSTANCE.getCoreObservable(concept).getDefinition() + "\n";
        ret += "Syntactic types: " + Arrays.toString(((Concept) concept.getType()).getTypeSet().toArray()) + "\n\n";

        if (described != null) {
            ret += "           Describes: " + described.getDefinition()
                    + (comparison == null ? "" : (" vs. " + comparison.getDefinition())) + "\n";
        }
        ret += "        Context type: " + decl(Observables.INSTANCE.getContextType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectContextType(concept.getType())) + "; in resolution: "
                + decl(Observables.INSTANCE.getContext(concept)) + "]\n";
        ret += "       Inherent type: " + decl(Observables.INSTANCE.getInherentType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectInherentType(concept.getType())) + "]\n";
        ret += "        Causant type: " + decl(Observables.INSTANCE.getCausantType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectCausantType(concept.getType())) + "]\n";
        ret += "         Caused type: " + decl(Observables.INSTANCE.getCausedType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectCausedType(concept.getType())) + "]\n";
        ret += "           Goal type: " + decl(Observables.INSTANCE.getGoalType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectGoalType(concept.getType())) + "]\n";
        ret += "       Adjacent type: " + decl(Observables.INSTANCE.getAdjacentType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectAdjacentType(concept.getType())) + "]\n";
        ret += "     Compresent type: " + decl(Observables.INSTANCE.getCompresentType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectCompresentType(concept.getType())) + "]\n";
        ret += "   Co-occurrent type: " + decl(Observables.INSTANCE.getCooccurrentType(concept.getType())) + " [direct: "
                + decl(Observables.INSTANCE.getDirectCooccurrentType(concept.getType())) + "]\n";

        Collection<IConcept> allTraits = Traits.INSTANCE.getTraits(concept.getType());
        Collection<IConcept> dirTraits = Traits.INSTANCE.getDirectTraits(concept.getType());
        if (!allTraits.isEmpty()) {
            ret += "\nTraits:\n";
            for (IConcept trait : allTraits) {
                ret += "    " + trait.getDefinition() + (dirTraits.contains(trait) ? " [direct]" : " [indirect]") + " "
                        + ((Concept) trait).getTypeSet() + "\n";
            }
        }

        Collection<IConcept> allRoles = Roles.INSTANCE.getRoles(concept.getType());
        Collection<IConcept> dirRoles = Roles.INSTANCE.getDirectRoles(concept.getType());
        if (!allRoles.isEmpty()) {
            ret += "\nRoles:\n";
            for (IConcept trait : allRoles) {
                ret += "    " + trait.getDefinition() + (dirRoles.contains(trait) ? " [direct]" : " [indirect]") + "\n";
            }
        }

        Collection<IConcept> affected = Observables.INSTANCE.getAffected(concept.getType());
        if (!affected.isEmpty()) {
            ret += "\nAffects:\n";
            for (IConcept quality : affected) {
                ret += "    " + quality.getDefinition() + "\n";
            }
        }

        Collection<IConcept> required = Observables.INSTANCE.getRequiredIdentities(concept.getType());
        if (!required.isEmpty()) {
            ret += "\nRequired identities:\n";
            for (IConcept identity : required) {
                ret += "    " + identity.getDefinition() + "\n";
            }
        }

        ret += "\nMetadata:\n";
        for (String key : concept.getMetadata().keySet()) {
            ret += "   " + key + ": " + concept.getMetadata().get(key) + "\n";
        }

        Unit unit = Units.INSTANCE.getDefaultUnitFor(concept);
        if (unit != null) {
            ret += "\nDefault unit: " + unit + "\n";
        }

        return ret;
    }

    private String decl(IConcept concept) {
        return concept == null ? "NONE" : concept.getDefinition();
    }

    /**
     * Take an observable whose units were assigned by default and check if it is being aggregated
     * by a countable observable whose spatial and/or temporal nature imply a unit distribution
     * change; if so, modify it to have the appropriate units.
     * 
     * @param observable
     * @param scale scale of contextualization
     */
    public void contextualizeUnitsForAggregation(Observable observable, IScale scale) {

        if (observable.getUnit() == null) {
            return;
        }

        List<IConcept> ops = new ArrayList<>();
        for (Pair<ValueOperator, Object> operator : observable.getValueOperators()) {

            if (operator.getFirst() == ValueOperator.BY) {
                Object operand = operator.getSecond();
                if (operand instanceof IConcept) {
                    ops.add((IConcept) operand);
                } else if (operand instanceof List) {
                    for (Object o : ((List<?>) operand)) {
                        if (o instanceof IConcept) {
                            ops.add((IConcept) o);
                        }
                    }
                }
            }

        }

        CoreOntology coreOntology = Resources.INSTANCE.getUpperOntology();

        if (coreOntology == null) {
            return;
        }

        IUnit originalUnit = observable.getUnit();
        boolean changed = false;

        ExtentDimension dspatial = null;
        ITime.Resolution dtemporal = null;

        for (IConcept dop : ops) {

            ExtentDimension spatial = null;
            ITime.Resolution temporal = null;

            if (dop.is(Type.COUNTABLE)) {
                if (dop.is(Type.EVENT)) {
                    temporal = coreOntology.getTemporalNature(dop);
                }
                spatial = coreOntology.getSpatialNature(dop);
            } else if (dop.is(Type.QUALITY)) {
                spatial = scale.getSpace() == null
                        ? null
                        : (scale.isSpatiallyDistributed() ? ExtentDimension.spatial(scale.getSpace().getDimensionality()) : null);
                temporal = scale.getTime() == null
                        ? null
                        : (scale.isTemporallyDistributed() ? scale.getTime().getResolution() : null);
            }

            if (spatial != null || temporal != null) {

                if (changed) {

                    /*
                     * verify that we are not mixing classifiers with different dimensionalities.
                     */
                    if ((dspatial != null && !dspatial.equals(spatial)) || (dtemporal != null && !dtemporal.equals(temporal))) {
                        throw new KlabContextualizationException(
                                "aggregation: cannot combine classifiers that imply incompatible dimensionalities");
                    }

                } else {

                    dspatial = spatial;
                    dtemporal = temporal;

                    int originalSpatialDimension = Units.INSTANCE.getSpatialDimensionality(originalUnit);
                    int originalTemporalDimension = Units.INSTANCE.getTemporalDimensionality(originalUnit);

                    if (spatial != null && originalSpatialDimension == spatial.dimensionality) {
                        originalUnit = Units.INSTANCE.removeExtents(originalUnit, Collections.singleton(spatial));
                        changed = true;
                    }

                    if (temporal != null && originalTemporalDimension == 1) {
                        originalUnit = Units.INSTANCE.removeExtents(originalUnit,
                                Collections.singleton(ExtentDimension.TEMPORAL));
                        changed = true;
                    }

                }
            }
        }

        if (changed) {
            observable.withUnit(originalUnit);
        }

    }

    /**
     * 
     * @param c
     * @return
     */
    public boolean isOccurrent(ISemantic c) {

        if (c.getType().is(Type.PROCESS) || c.getType().is(Type.EVENT)) {
            return true;
        }

        /*
         * TODO a quality occurs if it's created by a process
         */
        if (c.getType().is(Type.QUALITY)) {
            //
        }

        /*
         * TODO functional relationships should occur (?)
         */

        return false;
    }

    public boolean compareOperators(List<Pair<ValueOperator, Object>> ops1, List<Pair<ValueOperator, Object>> ops2) {
        if (ops1 == null && ops2 == null) {
            return true;
        }
        if ((ops1 == null && ops2 != null) || (ops1 != null && ops2 == null)) {
            return false;
        }

        if (ops1.size() != ops2.size()) {
            return false;
        }
        for (int i = 0; i < ops1.size(); i++) {
            Pair<ValueOperator, Object> op1 = ops1.get(i);
            Pair<ValueOperator, Object> op2 = ops2.get(i);
            if (!op1.getFirst().equals(op2.getFirst())) {
                return false;
            }
            if (!compareOperands(op1.getSecond(), op2.getSecond())) {
                return false;
            }
        }
        return true;
    }

    // /**
    // * If the passed observable references one or more abstract role, find the
    // * correspondent concepts in the resolution scope and return the expanded set
    // of
    // * observables with the role substituted by the concepts that incarnate it,
    // * keeping the original roles as contextual roles in the resulting
    // observables.
    // *
    // * TODO this must also use role models (contextualized on demand) and use the
    // * same mechanism to expand abstract identities.
    // *
    // * @param observable
    // * @param scope
    // * @deprecated bring into resolver
    // * @return
    // */
    // public Collection<IObservable> expandRoles(IObservable observable,
    // IResolutionScope scope) {
    //
    // List<IObservable> ret = new ArrayList<>();
    // Set<IConcept> expand = new HashSet<>();
    // for (IConcept role : Roles.INSTANCE.getDirectRoles(observable.getType())) {
    // if (role.isAbstract()) {
    // expand.add(role);
    // }
    // }
    // for (IConcept identity :
    // Traits.INSTANCE.getDirectTraits(observable.getType())) {
    // if (identity.isAbstract() && identity.is(Type.IDENTITY)) {
    // expand.add(identity);
    // }
    // }
    // if (expand.isEmpty()) {
    // ret.add(observable);
    // } else {
    // /*
    // * ensure the scope incarnates all of the existing abstract roles. If not, we
    // * produce no observables. We match roles by equality, not by inference, which
    // * may require rethinking.
    // */
    // Map<IConcept, Set<IConcept>> incarnated = new LinkedHashMap<>();
    // for (IConcept role : expand) {
    // if (role.is(Type.ROLE)) {
    // Collection<IConcept> known = scope.getRoles().get(role);
    // if (known == null || known.isEmpty()) {
    // // TODO use model
    // return ret;
    // }
    // incarnated.put(role, new HashSet<>(known));
    // } else {
    // // identity: use model only
    // }
    // }
    //
    // List<Set<IConcept>> concepts = new ArrayList<>(incarnated.values());
    // for (List<IConcept> incarnation : Sets.cartesianProduct(concepts)) {
    // Builder builder = observable.getBuilder(scope.getMonitor());
    // int i = 0;
    // for (IConcept orole : incarnated.keySet()) {
    // builder = builder.without(orole);
    // IConcept peer = incarnation.get(i++);
    // if (peer.is(Type.ROLE)) {
    // builder = builder.withRole(peer);
    // } else if (peer.is(Type.TRAIT)) {
    // builder = builder.withTrait(peer);
    // } else {
    // throw new KlabUnsupportedFeatureException(
    // "Abstract role substitution is only allowed for predicates at the moment");
    // }
    // }
    //
    // IObservable result = builder.buildObservable();
    // result.getContextualRoles().addAll(incarnated.keySet());
    // ret.add(result);
    // }
    //
    // }
    // return ret;
    // }

    /**
     * AARGH all to compare concepts by definition and not by identity
     * 
     * @param o1
     * @param o2
     * @return
     */
    private boolean compareOperands(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
            return false;
        }

        if (!o1.getClass().equals(o2.getClass())) {
            return false;
        }

        if (o1 instanceof Collection) {
            if (((Collection<?>) o1).size() != (((Collection<?>) o2).size())) {
                return false;
            }
            Iterator<?> i1 = ((Collection<?>) o1).iterator();
            Iterator<?> i2 = ((Collection<?>) o1).iterator();
            while(i1.hasNext()) {
                if (!compareOperands(i1.next(), i2.next())) {
                    return false;
                }
            }
        }

        if (o1 instanceof IConcept) {
            if (!((IConcept) o1).getDefinition().equals(((IConcept) o2).getDefinition())) {
                return false;
            }
        }

        if (o1 instanceof IObservable) {
            if (!(new ObservedConcept((IObservable) o1, Mode.RESOLUTION)
                    .equals(new ObservedConcept((IObservable) o2, Mode.RESOLUTION)))) {
                return false;
            }
        }

        return true;
    }

    /**
     * If the observable has value operators, remove them.
     * 
     * @param observable
     * @return
     */
    public IObservable removeValueOperators(IObservable observable, IMonitor monitor) {
        if (observable.getValueOperators().isEmpty()) {
            return observable;
        }
        return observable.getBuilder(monitor).withoutValueOperators().buildObservable();
    }

    /**
     * Make an observable from either a string, a IKimConcept or a IKimObservable. Promote a concept
     * or return an observable unaltered. Use to easily obtain an observable from a k.IM code
     * parameter value.
     * 
     * @param g
     * @param monitor
     * @return
     */
    public IObservable parseObservable(Object g, IMonitor monitor) {
        IObservable ret = null;
        if (g instanceof String) {
            ret = declare(parseDeclaration((String) g), monitor);
        } else if (g instanceof IKimConcept) {
            IConcept c = Concepts.INSTANCE.declare((IKimConcept) g);
            ret = Observable.promote(c);
        } else if (g instanceof IKimObservable) {
            ret = declare((IKimObservable) g, monitor);
        } else if (g instanceof IConcept) {
            ret = Observable.promote((IConcept) g);
        } else if (g instanceof IObservable) {
            ret = (IObservable) g;
        }
        return ret;
    }

    @Override
    public boolean isExtensive(IObservable observable) {
        boolean ret = observable.getType().is(Type.EXTENSIVE_PROPERTY) || observable.getType().is(Type.MONEY)
                || observable.getType().is(Type.MONETARY_VALUE);
        if (ret && Observables.INSTANCE.getDirectInherentType(observable.getType()) != null) {
            ret = false;
        }
        return ret;

    }

    public void registerConfiguration(IKimConceptStatement statement, IConcept concept) {
        this.configurationDetector.registerConfiguration(statement, concept);
    }

    /**
     * Convert any legitimate statement or object into an observed concept.
     * 
     * @param object
     * @return
     */
    public IObservedConcept asObservedConcept(Object object) {
        if (object instanceof IObservedConcept) {
            return (IObservedConcept) object;
        }
        return new ObservedConcept(asObservable(object));
    }

    /**
     * Convert any legitimate statement or object into an observable.
     * 
     * @param object
     * @return
     */
    public IObservable asObservable(Object object) {
        if (object instanceof IObservedConcept) {
            return ((IObservedConcept) object).getObservable();
        } else if (object instanceof IConcept) {
            return Observable.promote((IConcept) object);
        } else if (object instanceof IObservable) {
            return (IObservable) object;
        } else if (object instanceof IKimObservable) {
            return declare((IKimObservable) object, Klab.INSTANCE.getRootMonitor());
        } else if (object instanceof IKimConcept) {
            return Observable.promote(Concepts.INSTANCE.declare((IKimConcept) object));
        } else if (object instanceof String) {
            return declare((String) object);
        }
        throw new KlabIllegalArgumentException("cannot interpret " + object + " as an observable");
    }
}
