package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.OWL;

/**
 * The core workspace only contains OWL ontologies and is read from the classpath.
 * 
 * @author ferdinando.villa
 *
 */
public class CoreOntology extends AbstractWorkspace {

    private boolean synced = false;
    private Map<Type, Concept> worldviewCoreConcepts = Collections.synchronizedMap(new HashMap<>());
    private static Map<Type, String> coreConceptIds = Collections.synchronizedMap(new HashMap<>());

    public static final String CORE_ONTOLOGY_NAME = "observation";

    static {
        coreConceptIds.put(Type.PROCESS, NS.CORE_PROCESS);
        coreConceptIds.put(Type.SUBJECT, NS.CORE_SUBJECT);
        coreConceptIds.put(Type.EVENT, NS.CORE_EVENT);
        coreConceptIds.put(Type.FUNCTIONAL, NS.CORE_FUNCTIONAL_RELATIONSHIP);
        coreConceptIds.put(Type.STRUCTURAL, NS.CORE_STRUCTURAL_RELATIONSHIP);
        coreConceptIds.put(Type.RELATIONSHIP, NS.CORE_RELATIONSHIP);
        coreConceptIds.put(Type.EXTENSIVE_PROPERTY, NS.CORE_EXTENSIVE_PHYSICAL_PROPERTY);
        coreConceptIds.put(Type.INTENSIVE_PROPERTY, NS.CORE_INTENSIVE_PHYSICAL_PROPERTY);
        coreConceptIds.put(Type.IDENTITY, NS.CORE_IDENTITY);
        coreConceptIds.put(Type.ATTRIBUTE, NS.CORE_ATTRIBUTE);
        coreConceptIds.put(Type.REALM, NS.CORE_REALM);
        coreConceptIds.put(Type.ORDERING, NS.CORE_ORDERING);
        coreConceptIds.put(Type.ROLE, NS.CORE_ROLE);
        coreConceptIds.put(Type.CONFIGURATION, NS.CORE_CONFIGURATION);
        coreConceptIds.put(Type.CLASS, NS.CORE_TYPE);
        coreConceptIds.put(Type.QUANTITY, NS.CORE_QUANTITY);
        coreConceptIds.put(Type.DOMAIN, NS.CORE_DOMAIN);
        coreConceptIds.put(Type.ENERGY, NS.CORE_ENERGY);
        coreConceptIds.put(Type.ENTROPY, NS.CORE_ENTROPY);
        coreConceptIds.put(Type.LENGTH, NS.CORE_LENGTH);
        coreConceptIds.put(Type.MASS, NS.CORE_MASS);
        coreConceptIds.put(Type.VOLUME, NS.CORE_VOLUME);
        coreConceptIds.put(Type.WEIGHT, NS.CORE_WEIGHT);
        coreConceptIds.put(Type.MONEY, NS.CORE_MONETARY_VALUE);
        coreConceptIds.put(Type.DURATION, NS.CORE_DURATION);
        coreConceptIds.put(Type.AREA, NS.CORE_AREA);
        coreConceptIds.put(Type.ACCELERATION, NS.CORE_ACCELERATION);
        coreConceptIds.put(Type.PRIORITY, NS.CORE_PRIORITY);
        coreConceptIds.put(Type.ELECTRIC_POTENTIAL, NS.CORE_ELECTRIC_POTENTIAL);
        coreConceptIds.put(Type.CHARGE, NS.CORE_CHARGE);
        coreConceptIds.put(Type.RESISTANCE, NS.CORE_RESISTANCE);
        coreConceptIds.put(Type.RESISTIVITY, NS.CORE_RESISTIVITY);
        coreConceptIds.put(Type.PRESSURE, NS.CORE_PRESSURE);
        coreConceptIds.put(Type.ANGLE, NS.CORE_ANGLE);
        coreConceptIds.put(Type.VELOCITY, NS.CORE_SPEED);
        coreConceptIds.put(Type.TEMPERATURE, NS.CORE_TEMPERATURE);
        coreConceptIds.put(Type.VISCOSITY, NS.CORE_VISCOSITY);
        coreConceptIds.put(Type.AGENT, NS.CORE_AGENT);
        coreConceptIds.put(Type.DELIBERATIVE, NS.CORE_DELIBERATIVE_AGENT);
        coreConceptIds.put(Type.INTERACTIVE, NS.CORE_INTERACTIVE_AGENT);
        coreConceptIds.put(Type.REACTIVE, NS.CORE_REACTIVE_AGENT);
        coreConceptIds.put(Type.UNCERTAINTY, NS.CORE_UNCERTAINTY);
        coreConceptIds.put(Type.PROBABILITY, NS.CORE_PROBABILITY);
        coreConceptIds.put(Type.PROPORTION, NS.CORE_PROPORTION);
        coreConceptIds.put(Type.NUMEROSITY, NS.CORE_COUNT);
        coreConceptIds.put(Type.DISTANCE, NS.CORE_DISTANCE);
        coreConceptIds.put(Type.RATIO, NS.CORE_RATIO);
        coreConceptIds.put(Type.VALUE, NS.CORE_VALUE);
        coreConceptIds.put(Type.CHANGE, NS.CORE_CHANGE);
        coreConceptIds.put(Type.OCCURRENCE, NS.CORE_OCCURRENCE);
        coreConceptIds.put(Type.PRESENCE, NS.CORE_PRESENCE);
        coreConceptIds.put(Type.EXTENT, NS.CORE_EXTENT);
        // coreConceptIds.put(Type.ASSESSMENT, NS.CORE_ASSESSMENT);
    }

    public static interface NS {

        // core properties
        public static final String IS_ABSTRACT = "odo:isAbstract";
        // TODO move to k.LAB
        public static final String BASE_DECLARATION = "observation:baseDeclaration";
        public static final String ORDER_PROPERTY = "observation:orderingRank";
        public static final String HAS_REALM_PROPERTY = "observation:hasRealm";
        public static final String HAS_IDENTITY_PROPERTY = "observation:hasIdentity";
        public static final String HAS_ATTRIBUTE_PROPERTY = "observation:hasAttribute";
        public static final String HAS_CONTEXT_PROPERTY = "observation:hasContext";
        public static final String HAS_COMPRESENT_PROPERTY = "observation:hasCompresent";
        public static final String HAS_CAUSANT_PROPERTY = "observation:hasCausant";
        public static final String HAS_CAUSED_PROPERTY = "observation:hasCaused";
        public static final String HAS_PURPOSE_PROPERTY = "observation:hasPurpose";
        public static final String OCCURS_DURING_PROPERTY = "observation:occursDuring";
        public static final String IS_ADJACENT_TO_PROPERTY = "observation:isAdjacentTo";
        public static final String HAS_SUBJECTIVE_TRAIT_PROPERTY = "observation:hasSubjectiveTrait";
        public static final String IS_SUBJECTIVE = "observation:isSubjectiveTrait";
        public static final String IS_INHERENT_TO_PROPERTY = "observation:isInherentTo";

        // TODO check the next two for redundancy
        public static final String DESCRIBES_OBSERVABLE_PROPERTY = "observation:describesObservable";
        public static final String DESCRIBES_QUALITY_PROPERTY = "observation:describesQuality";

        public static final String IS_COMPARED_TO_PROPERTY = "observation:isComparedTo";
        public static final String HAS_ROLE_PROPERTY = "observation:hasRole";
        public static final String DENIABILITY_PROPERTY = "observation:isDeniable";
        public static final String IMPLIES_ROLE_PROPERTY = "observation:impliesRole";
        public static final String APPLIES_TO_PROPERTY = "observation:appliesTo";
        public static final String IMPLIES_SOURCE_PROPERTY = "observation:impliesSource";
        public static final String IMPLIES_DESTINATION_PROPERTY = "observation:impliesDestination";
        public static final String CONFERS_TRAIT_PROPERTY = "observation:confersTrait";
        public static final String PROPORTIONAL_QUALITY_PROPERTY = "observation:proportionalQuality";
        public static final String INVERSELY_PROPORTIONAL_QUALITY_PROPERTY = "observation:inverselyProportionalQuality";
        public static final String CLASSIFIES_QUALITY_PROPERTY = "observation:classifiesQuality";
        public static final String REQUIRES_IDENTITY_PROPERTY = "observation:requiresIdentity";
        public static final String DISCRETIZES_QUALITY_PROPERTY = "observation:discretizesQuality";
        public static final String MARKS_QUALITY_PROPERTY = "observation:marksQuality";
        public static final String IS_TYPE_DELEGATE = "observation:isTypeDelegate";
        public static final String IS_NEGATION_OF = "observation:isNegationOf";
        public static final String INHERENCY_IS_DISTRIBUTED = "observation:inherencyIsDistributed";
        public static final String AFFECTS_PROPERTY = "observation:affects";
        public static final String CREATES_PROPERTY = "observation:creates";
        public static final String CHANGES_PROPERTY = "observation:changes";

        // TODO move to klab
        public static final String IS_CORE_KIM_TYPE = "observation:isCoreKimType";

        // contextual identities: FIXME revise, if possible remove, at worst move away
        public static final String TEMPORAL_IDENTITY = "observation:TemporalIdentity";
        public static final String SPATIAL_IDENTITY = "observation:SpatialIdentity";
        public static final String PUNTAL_IDENTITY = "observation:Puntal";
        public static final String LINEAL_IDENTITY = "observation:Lineal";
        public static final String AREAL_IDENTITY = "observation:Areal";
        public static final String VOLUMETRIC_IDENTITY = "observation:Volumetric";
        public static final String YEARLY_IDENTITY = "observation:Yearly";
        public static final String MONTHLY_IDENTITY = "observation:Monthly";
        public static final String WEEKLY_IDENTITY = "observation:Weekly";
        public static final String DAILY_IDENTITY = "observation:Daily";
        public static final String HOURLY_IDENTITY = "observation:Hourly";

        // annotation property that specifies the base SI unit for a physical property
        public static final String SI_UNIT_PROPERTY = "observation:unit";

        /*
         * Annotations affecting the ranking system. Used as keys in maps, so they don't depend on
         * the ontology being in the system.
         */
        public static final String LEXICAL_SCOPE = "im:lexical-scope";
        public static final String TRAIT_CONCORDANCE = "im:trait-concordance";
        public static final String SEMANTIC_DISTANCE = "im:semantic-concordance";

        public static final String INHERENCY = "im:inherency";
        public static final String EVIDENCE = "im:evidence";
        public static final String NETWORK_REMOTENESS = "im:network-remoteness";
        public static final String SUBJECTIVE_CONCORDANCE = "im:subjective-concordance";

        // Scale criteria are an aggregation of time + space (and potentially others)
        public static final String SCALE_COVERAGE = "im:scale-coverage";
        public static final String SCALE_SPECIFICITY = "im:scale-specificity";
        public static final String SCALE_COHERENCY = "im:scale-coherency";

        /*
         * using space and time explicitly should be alternative to using scale criteria. All are
         * computed anyway and can be used together if wished.
         */
        public static final String SPACE_COVERAGE = "im:space-coverage";
        public static final String SPACE_SPECIFICITY = "im:space-specificity";
        public static final String SPACE_COHERENCY = "im:space-coherency";
        public static final String TIME_COVERAGE = "im:time-coverage";
        public static final String TIME_SPECIFICITY = "im:time-specificity";
        public static final String TIME_COHERENCY = "im:time-coherency";

        // only annotation used for subjective ranking in the default behavior
        public static final String RELIABILITY = "im:reliability";

        /*
         * annotation properties supporting k.LAB functions
         */
        public static final String CORE_OBSERVABLE_PROPERTY = "klab:coreObservable";
        public static final String CONCEPT_DEFINITION_PROPERTY = "klab:conceptDefinition";
        public static final String LOCAL_ALIAS_PROPERTY = "klab:localAlias";
        public static final String DISPLAY_LABEL_PROPERTY = "klab:displayLabel";
        public static final String REFERENCE_NAME_PROPERTY = "klab:referenceName";
        public static final String AUTHORITY_ID_PROPERTY = "klab:authorityId";
        public static final String UNTRANSFORMED_CONCEPT_PROPERTY = "klab:untransformedConceptId";
        public static final String ORIGINAL_TRAIT = "klab:originalTrait";

        /**
         * Annotation contains the ID of the property (in same ontology) that will be used to create
         * restrictions to adopt the trait carrying the annotation.
         */
        public static final String TRAIT_RESTRICTING_PROPERTY = "klab:restrictingProperty";

        /*
         * the core properties we use internally to establish observation semantics
         */
        // TODO used to reconstruct the "subject" after mod with unary operator. Modify name, move to klab (?)
        public static final String OBSERVES_PROPERTY = "observation:observes";

        /**
         * the root domain for the ontologies.
         */
        public static final String CORE_DOMAIN = "observation:Domain";
        public static final String CORE_VOID = "observation:Void";
        public static final String CORE_OBSERVABLE = "observation:Observable";
        public static final String CORE_OBJECT = "observation:DirectObservable";
        public static final String CORE_PROCESS = "observation:Process";
        public static final String CORE_QUALITY = "observation:Quality";
        public static final String CORE_EVENT = "observation:Event";
        public static final String CORE_TRAIT = "observation:Trait";
        public static final String CORE_IDENTITY = "observation:Identity";
        public static final String CORE_QUANTITY = "observation:ContinuousNumericallyQuantifiableQuality";
        public static final String CORE_ASSERTED_QUALITY = "observation:AssertedQuality";
        public static final String CORE_SUBJECT = "observation:Subject";
        public static final String CORE_PHYSICAL_PROPERTY = "observation:PhysicalProperty";
        public static final String CORE_EXTENSIVE_PHYSICAL_PROPERTY = "observation:ExtensivePhysicalProperty";
        public static final String CORE_INTENSIVE_PHYSICAL_PROPERTY = "observation:IntensivePhysicalProperty";
        public static final String CORE_ENERGY = "observation:Energy";
        public static final String CORE_ENTROPY = "observation:Entropy";
        public static final String CORE_LENGTH = "observation:Length";
        public static final String CORE_MASS = "observation:Mass";
        public static final String CORE_PROBABILITY = "observation:Probability";
        public static final String CORE_MAGNITUDE = "observation:Magnitude";
        public static final String CORE_LEVEL = "observation:Level";
        public static final String CORE_VOLUME = "observation:Volume";
        public static final String CORE_WEIGHT = "observation:Weight";
        public static final String CORE_DURATION = "observation:Duration";
        public static final String CORE_MONETARY_VALUE = "observation:MonetaryValue";
        public static final String CORE_PREFERENCE_VALUE = "observation:PreferenceValue";
        public static final String CORE_ACCELERATION = "observation:Acceleration";
        public static final String CORE_AREA = "observation:Area";
        public static final String CORE_ELECTRIC_POTENTIAL = "observation:ElectricPotential";
        public static final String CORE_CHARGE = "observation:Charge";
        public static final String CORE_RESISTANCE = "observation:Resistance";
        public static final String CORE_RESISTIVITY = "observation:Resistivity";
        public static final String CORE_PRESSURE = "observation:Pressure";
        public static final String CORE_ANGLE = "observation:Angle";
//        public static final String CORE_ASSESSMENT = "observation:Assessment";
        public static final String CORE_CHANGE = "observation:Change";
        public static final String CORE_CHANGED_EVENT = "observation:ChangeEvent";
        public static final String CORE_CHANGE_RATE = "observation:ChangeRate";
        public static final String CORE_SPEED = "observation:Speed";
        public static final String CORE_TEMPERATURE = "observation:Temperature";
        public static final String CORE_VISCOSITY = "observation:Viscosity";
        public static final String CORE_AGENT = "observation:Agent";
        public static final String CORE_CONFIGURATION = "observation:Configuration";
        public static final String CORE_RELATIONSHIP = "observation:Relationship";
        public static final String CORE_FUNCTIONAL_RELATIONSHIP = "observation:FunctionalRelationship";
        public static final String CORE_STRUCTURAL_RELATIONSHIP = "observation:StructuralRelationship";
        public static final String CORE_TYPE = "observation:Type";
        public static final String CORE_ORDERING = "observation:Ordering";
        public static final String CORE_REALM = "observation:Realm";
        public static final String CORE_ATTRIBUTE = "observation:Attribute";
        public static final String CORE_ROLE = "observation:Role";
        public static final String CORE_PRIORITY = "observation:Priority";
        public static final String CORE_COUNT = "observation:Numerosity";
        public static final String CORE_PROPORTION = "observation:Proportion";
        public static final String CORE_RATIO = "observation:Ratio";
        public static final String CORE_PRESENCE = "observation:Presence";
        public static final String CORE_OCCURRENCE = "observation:Occurrence";
        public static final String CORE_VALUE = "observation:Value";
        public static final String CORE_DISTANCE = "observation:Distance";
        public static final String CORE_BASE_AGENT = "observation:Agent";
        public static final String CORE_REACTIVE_AGENT = "observation:ReactiveAgent";
        public static final String CORE_DELIBERATIVE_AGENT = "observation:DeliberativeAgent";
        public static final String CORE_INTERACTIVE_AGENT = "observation:InteractiveAgent";
        public static final String CORE_UNCERTAINTY = "observation:Uncertainty";
        public static final String CORE_EXTENT = "observation:Extent";

        // TODO move to klab
        public static final String CORE_PREDICTED_ATTRIBUTE = "observation:Predicted";

    }

    public CoreOntology(File directory) {
        super("core", directory);
    }

    public void registerCoreConcept(String coreConcept, IConcept worldviewPeer) {
        /*
         * TODO must handle the specialized concepts so that they inherit from the redefined ones,
         * too. E.g. when the AGENT handler is received, it should create and install all the agent
         * types in the same ontology.
         */
    }

    public IKimLoader load(IKimLoader loader, IMonitor monitor) {
        load(monitor);
        return loader;
    }

    @Override
    public IKimLoader load(IMonitor monitor) {
        IKimLoader ret = null;
        if (!synced) {
            synced = true;
            Resources.INSTANCE.extractKnowledgeFromClasspath(getRoot());
        }
        OWL.INSTANCE.initialize(getRoot(), monitor);

        /**
         * This test is unlikely to fail, but its purpose is primarily to preload the core ontology
         * catalogues, so that the k.IM validator will not cause delays when checking core concepts,
         * which makes the validator stop silently (by horrendous XText design) and ignore
         * everything beyond the first delay.
         * 
         * DO NOT REMOVE this test. Removing it will cause seemingly completely unrelated bugs that
         * will take a very long time to figure out.
         */
        IConcept dummy = Concepts.INSTANCE.getConcept(NS.CORE_OBSERVABLE);
        if (dummy == null) {
            throw new KlabIOException("core knowledge: can't find known concepts, ontologies are probably corrupted");
        }

        Logging.INSTANCE.info(OWL.INSTANCE.getOntologies(true).size() + " ontologies read from classpath");

        return ret;
    }

    public Concept getCoreType(Set<Type> type) {

        if (type.contains(Type.NOTHING)) {
            return OWL.INSTANCE.getNothing();
        }

        Type coreType = getRepresentativeCoreType(type);
        if (coreType == null) {
            return null;
        }
        Concept ret = worldviewCoreConcepts.get(coreType);
        if (ret == null) {
            String id = coreConceptIds.get(coreType);
            if (id != null) {
                ret = Concepts.c(id);
            }
        }

        return ret;
    }

    public Type getRepresentativeCoreType(Set<Type> type) {

        Type ret = null;

        /*
         * FIXME can be made faster using a mask and a switch, although the specialized concepts
         * still require a bit of extra logic.
         */

        if (type.contains(Type.PROCESS)) {
            ret = Type.PROCESS;
        } else if (type.contains(Type.SUBJECT)) {
            ret = Type.SUBJECT;
        } else if (type.contains(Type.EVENT)) {
            ret = Type.EVENT;
        } else if (type.contains(Type.RELATIONSHIP)) {
            ret = Type.RELATIONSHIP;
        } else /* if (type.contains(Type.TRAIT)) { */
        if (type.contains(Type.IDENTITY)) {
            ret = Type.IDENTITY;
        } else if (type.contains(Type.ATTRIBUTE)) {
            ret = Type.ATTRIBUTE;
        } else if (type.contains(Type.REALM)) {
            ret = Type.REALM;
        } else if (type.contains(Type.ORDERING)) {
            ret = Type.ORDERING;
        } else if (type.contains(Type.ROLE)) {
            ret = Type.ROLE;
        } else if (type.contains(Type.CONFIGURATION)) {
            ret = Type.CONFIGURATION;
        } else if (type.contains(Type.CLASS)) {
            ret = Type.CLASS;
        } else if (type.contains(Type.QUANTITY)) {
            ret = Type.QUANTITY;
        } else if (type.contains(Type.DOMAIN)) {
            ret = Type.DOMAIN;
        } else if (type.contains(Type.ENERGY)) {
            ret = Type.ENERGY;
        } else if (type.contains(Type.ENTROPY)) {
            ret = Type.ENTROPY;
        } else if (type.contains(Type.LENGTH)) {
            ret = Type.LENGTH;
        } else if (type.contains(Type.MASS)) {
            ret = Type.LENGTH;
        } else if (type.contains(Type.VOLUME)) {
            ret = Type.VOLUME;
        } else if (type.contains(Type.WEIGHT)) {
            ret = Type.WEIGHT;
        } else if (type.contains(Type.MONEY)) {
            ret = Type.MONEY;
        } else if (type.contains(Type.DURATION)) {
            ret = Type.DURATION;
        } else if (type.contains(Type.AREA)) {
            ret = Type.AREA;
        } else if (type.contains(Type.ACCELERATION)) {
            ret = Type.ACCELERATION;
        } else if (type.contains(Type.PRIORITY)) {
            ret = Type.PRIORITY;
        } else if (type.contains(Type.ELECTRIC_POTENTIAL)) {
            ret = Type.ELECTRIC_POTENTIAL;
        } else if (type.contains(Type.CHARGE)) {
            ret = Type.CHARGE;
        } else if (type.contains(Type.RESISTANCE)) {
            ret = Type.RESISTANCE;
        } else if (type.contains(Type.RESISTIVITY)) {
            ret = Type.RESISTIVITY;
        } else if (type.contains(Type.PRESSURE)) {
            ret = Type.PRESSURE;
        } else if (type.contains(Type.ANGLE)) {
            ret = Type.ANGLE;
        } else if (type.contains(Type.VELOCITY)) {
            ret = Type.VELOCITY;
        } else if (type.contains(Type.TEMPERATURE)) {
            ret = Type.TEMPERATURE;
        } else if (type.contains(Type.VISCOSITY)) {
            ret = Type.VISCOSITY;
        } else if (type.contains(Type.AGENT)) {
            ret = Type.AGENT;
        } else if (type.contains(Type.UNCERTAINTY)) {
            ret = Type.UNCERTAINTY;
        } else if (type.contains(Type.PROBABILITY)) {
            ret = Type.PROBABILITY;
        } else if (type.contains(Type.PROPORTION)) {
            ret = Type.PROPORTION;
        } else if (type.contains(Type.NUMEROSITY)) {
            ret = Type.NUMEROSITY;
        } else if (type.contains(Type.DISTANCE)) {
            ret = Type.DISTANCE;
        } else if (type.contains(Type.RATIO)) {
            ret = Type.RATIO;
        } else if (type.contains(Type.VALUE)) {
            ret = Type.VALUE;
        } else if (type.contains(Type.MONETARY_VALUE)) {
            ret = Type.MONETARY_VALUE;
        } else if (type.contains(Type.OCCURRENCE)) {
            ret = Type.OCCURRENCE;
        } else if (type.contains(Type.PRESENCE)) {
            ret = Type.PRESENCE;
        } else if (type.contains(Type.EXTENT)) {
            ret = Type.EXTENT;
        }
        // THESE COME AFTER ALL THE POSSIBLE SUBCLASSES
        else if (type.contains(Type.EXTENSIVE_PROPERTY)) {
            ret = Type.EXTENSIVE_PROPERTY;
        } else if (type.contains(Type.INTENSIVE_PROPERTY)) {
            ret = Type.INTENSIVE_PROPERTY;
        } /*
           * else if (type.contains(Type.ASSESSMENT)) { ret = Type.ASSESSMENT; }
           */

        return ret;
    }

    public String importOntology(String url, String prefix) {
        try {
            return OWL.INSTANCE.importExternal(url, prefix, Klab.INSTANCE.getRootMonitor());
        } catch (KlabException e) {
            return null;
        }
    }

    @Override
    public IProject createProject(String projectId, IMonitor monitor) {
        throw new IllegalStateException("The core ontology space is read-only");
    }

    public void setAsCoreType(Concept concept) {
        worldviewCoreConcepts.put(getRepresentativeCoreType(concept.getTypeSet()), concept);
    }

    public IConcept alignCoreInheritance(IConcept concept) {
        // if (concept.is(IKimConcept.Type.RELATIONSHIP)) {
        // // parent of core relationship depends on functional/structural nature
        // if (concept.is(IKimConcept.Type.FUNCTIONAL) ||
        // concept.is(IKimConcept.Type.STRUCTURAL)) {
        // concept = getCoreType(EnumSet.of(IKimConcept.Type.RELATIONSHIP));
        // }
        // } else if (concept.is(IKimConcept.Type.AGENT)) {
        // // parent of agent depends on agent typology
        // if (concept.is(IKimConcept.Type.DELIBERATIVE) ||
        // concept.is(IKimConcept.Type.INTERACTIVE)
        // || concept.is(IKimConcept.Type.REACTIVE)) {
        // concept = getCoreType(EnumSet.of(IKimConcept.Type.AGENT));
        // }
        // }
        return concept;
    }

    /**
     * Return the spatial nature, if any, of the passed concept, which should be a countable, or
     * null.
     * 
     * @param concept
     * @return
     */
    public ExtentDimension getSpatialNature(IConcept concept) {
        for (IConcept identity : Traits.INSTANCE.getIdentities(concept)) {
            if (identity.is(Concepts.c(NS.SPATIAL_IDENTITY))) {
                if (identity.is(Concepts.c(NS.AREAL_IDENTITY))) {
                    return ExtentDimension.AREAL;
                } else if (identity.is(Concepts.c(NS.PUNTAL_IDENTITY))) {
                    return ExtentDimension.PUNTAL;
                }
                if (identity.is(Concepts.c(NS.LINEAL_IDENTITY))) {
                    return ExtentDimension.LINEAL;
                }
                if (identity.is(Concepts.c(NS.VOLUMETRIC_IDENTITY))) {
                    return ExtentDimension.VOLUMETRIC;
                }
            }
        }
        return null;
    }

    /**
     * Return the temporal resolution implied in the passed concept, which should be an event, or
     * null.
     * 
     * TODO add the multiplier from (TBI) data properties associated with the identity.
     * 
     * @param concept
     * @return
     */
    public ITime.Resolution getTemporalNature(IConcept concept) {
        for (IConcept identity : Traits.INSTANCE.getIdentities(concept)) {
            if (identity.is(Concepts.c(NS.TEMPORAL_IDENTITY))) {
                if (identity.is(Concepts.c(NS.YEARLY_IDENTITY))) {
                    return Time.resolution(1, ITime.Resolution.Type.YEAR);
                } else if (identity.is(Concepts.c(NS.HOURLY_IDENTITY))) {
                    return Time.resolution(1, ITime.Resolution.Type.HOUR);
                } else if (identity.is(Concepts.c(NS.WEEKLY_IDENTITY))) {
                    return Time.resolution(1, ITime.Resolution.Type.WEEK);
                } else if (identity.is(Concepts.c(NS.MONTHLY_IDENTITY))) {
                    return Time.resolution(1, ITime.Resolution.Type.MONTH);
                } else if (identity.is(Concepts.c(NS.DAILY_IDENTITY))) {
                    return Time.resolution(1, ITime.Resolution.Type.DAY);
                }
            }
        }
        return null;
    }

    @Override
    public IProject loadProject(String projectId, IMonitor monitor) {
        throw new KlabIllegalStateException("the worldview cannot load projects on demand");
    }

}
