package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.owl.OWL;

/**
 * The core workspace only contains OWL ontologies and is read from the classpath.
 * 
 * @author ferdinando.villa
 *
 */
public class CoreOntology extends AbstractWorkspace {

    boolean synced = false;

    public static interface NS {

        // domain concepts for known extents
        public static final String SPACE_DOMAIN                           = "observation:Space";
        public static final String TIME_DOMAIN                            = "observation:Time";

        // core properties
        public static final String IS_ABSTRACT                            = "observation:isAbstract";
        public static final String BASE_DECLARATION                       = "observation:baseDeclaration";
        public static final String ORDER_PROPERTY                         = "observation:orderingRank";
        public static final String HAS_REALM_PROPERTY                     = "observation:hasRealm";
        public static final String HAS_IDENTITY_PROPERTY                  = "observation:hasIdentity";
        public static final String HAS_ATTRIBUTE_PROPERTY                 = "observation:hasAttribute";
        public static final String HAS_CONTEXT_PROPERTY                   = "observation:hasContext";
        public static final String HAS_SUBJECTIVE_TRAIT_PROPERTY          = "observation:hasSubjectiveTrait";
        public static final String IS_SUBJECTIVE                          = "observation:isSubjectiveTrait";
        public static final String IS_INHERENT_TO_PROPERTY                = "observation:isInherentTo";
        public static final String HAS_ROLE_PROPERTY                      = "observation:hasRole";
        public static final String EXPOSES_TRAIT_PROPERTY                 = "observation:exposesTrait";
        public static final String DENIABILITY_PROPERTY                   = "observation:isDeniable";
        public static final String IMPLIES_OBSERVABLE_PROPERTY            = "observation:impliesObservable";
        public static final String IMPLIES_ROLE_PROPERTY                  = "observation:impliesRole";
        public static final String APPLIES_TO_PROPERTY                    = "observation:appliesTo";
        public static final String IMPLIES_SOURCE_PROPERTY                = "observation:impliesSource";
        public static final String IMPLIES_DESTINATION_PROPERTY           = "observation:impliesDestination";
        public static final String CONFERS_TRAIT_PROPERTY                 = "observation:confersTrait";
        public static final String DESCRIBES_QUALITY_PROPERTY             = "observation:describesQuality";
        public static final String LIMITED_BY_PROPERTY                    = "observation:limitedBy";
        public static final String REPRESENTED_BY_PROPERTY                = "observation:representedBy";
        public static final String IS_TYPE_DELEGATE                       = "observation:isTypeDelegate";

        // core observation ontology
        public static final String OBSERVATION                            = "observation:Observation";
        public static final String DIRECT_OBSERVATION                     = "observation:DirectObservation";
        public static final String INDIRECT_OBSERVATION                   = "observation:IndirectObservation";
        public static final String CLASSIFICATION                         = "observation:Classification";
        public static final String MEASUREMENT                            = "observation:Measurement";
        public static final String QUANTIFICATION                         = "observation:Quantification";
        public static final String RANKING                                = "observation:Ranking";
        public static final String COUNT_OBSERVATION                      = "observation:CountObservation";
        public static final String PERCENTAGE_OBSERVATION                 = "observation:PercentageObservation";
        public static final String PROPORTION_OBSERVATION                 = "observation:ProportionObservation";
        public static final String RATIO_OBSERVATION                      = "observation:RatioObservation";
        public static final String DISTANCE_OBSERVATION                   = "observation:DistanceMeasurement";
        public static final String VALUE_OBSERVATION                      = "observation:Valuation";
        public static final String PROBABILITY_OBSERVATION                = "observation:ProbabilityObservation";
        public static final String UNCERTAINTY_OBSERVATION                = "observation:UncertaintyObservation";
        public static final String PRESENCE_OBSERVATION                   = "observation:PresenceObservation";

        // annotation property that specifies the base SI unit for a physical property
        public static final String SI_UNIT_PROPERTY                       = "observation:unit";

        // im ontology annotations affecting the ranking system. Used as key in
        // maps, so
        // they don't depend on the ontology being in the system.
        public static final String LEXICAL_SCOPE                          = "im:lexical-scope";
        public static final String TRAIT_CONCORDANCE                      = "im:trait-concordance";
        public static final String SEMANTIC_DISTANCE                      = "im:semantic-concordance";
        public static final String SCALE_COVERAGE                         = "im:scale-coverage";
        public static final String SCALE_SPECIFICITY                      = "im:scale-specificity";
        public static final String INHERENCY                              = "im:inherency";
        public static final String EVIDENCE                               = "im:evidence";
        public static final String NETWORK_REMOTENESS                     = "im:network-remoteness";
        public static final String SCALE_COHERENCY                        = "im:scale-coherency";
        public static final String SUBJECTIVE_CONCORDANCE                 = "im:subjective-concordance";

        // only annotation used for subjective ranking in the default behavior
        public static final String RELIABILITY                            = "im:reliability";

        /*
         * model objects for function return types and the like
         */
        public static final String INTEGER                                = "klab:ShortInteger";
        public static final String FLOAT                                  = "klab:ShortFloat";
        public static final String TEXT                                   = "klab:Text";
        public static final String LONG                                   = "klab:LongInteger";
        public static final String DOUBLE                                 = "klab:LongFloat";
        public static final String BOOLEAN                                = "klab:Boolean";
        public static final String NUMBER                                 = "klab:Number";
        public static final String SHAPE                                  = "klab:Shape";

        public static final String STATE_CONTEXTUALIZER                   = "klab:StateContextualizer";
        public static final String SUBJECT_CONTEXTUALIZER                 = "klab:SubjectContextualizer";
        public static final String PROCESS_CONTEXTUALIZER                 = "klab:ProcessContextualizer";
        public static final String EVENT_INSTANTIATOR                     = "klab:EventInstantiator";
        public static final String SUBJECT_INSTANTIATOR                   = "klab:SubjectInstantiator";
        public static final String EVENT_CONTEXTUALIZER                   = "klab:EventContextualizer";
        public static final String RELATIONSHIP_INSTANTIATOR              = "klab:RelationshipInstantiator";
        public static final String FUNCTIONAL_RELATIONSHIP_CONTEXTUALIZER = "klab:FunctionalRelationshipContextualizer";
        public static final String STRUCTURAL_RELATIONSHIP_CONTEXTUALIZER = "klab:StructuralRelationshipContextualizer";
        public static final String DATASOURCE                             = "klab:DataSource";
        public static final String OBJECTSOURCE                           = "klab:ObjectSource";
        public static final String LOOKUP_TABLE                           = "klab:LookupTable";

        /*
         * annotation properties supporting k.LAB functions
         */

        public static final String CONCEPT_DEFINITION_PROPERTY            = "klab:conceptDefinition";
        public static final String LOCAL_ALIAS_PROPERTY                   = "klab:localAlias";
        public static final String DISPLAY_LABEL_PROPERTY                 = "klab:displayLabel";
        public static final String AUTHORITY_ID_PROPERTY                  = "klab:authorityId";
        public static final String UNTRANSFORMED_CONCEPT_PROPERTY         = "klab:untransformedConceptId";
        public static final String ORIGINAL_TRAIT                         = "klab:originalTrait";

        /**
         * Annotation contains the ID of the property (in same ontology) that will be used to create
         * restrictions to adopt the trait carrying the annotation.
         */
        public static final String TRAIT_RESTRICTING_PROPERTY             = "klab:restrictingProperty";

        /*
         * the core properties we use internally to establish observation semantics
         */
        /**
         * The property that links an observation to its observable.
         */
        public static final String CONTEXTUALIZES                         = "observation:contextualizes";
        public static final String INHERENT_IN                            = "observation:isInherentTo";
        public static final String OBSERVED_INTO                          = "observation:hasContext";
        public static final String PART_OF                                = "observation:isPartOf";
        public static final String CONSTITUENT_OF                         = "observation:isConstituentOf";
        public static final String STRUCTURING_PROPERTY                   = "observation:structuringObjectProperty";
        public static final String MAXIMUM_TRAIT_CONCEPT                  = "IM.MAXIMUM_TRAIT";
        public static final String DEPENDS_ON_PROPERTY                    = "observation:dependsOn";
        public static final String RELATES_TO_PROPERTY                    = "observation:relatesTo";
        public static final String CONTAINS_PART_PROPERTY                 = "observation:containsPart";
        public static final String CONTAINS_PART_SPATIALLY_PROPERTY       = "observation:containsPartSpatially";
        public static final String OBSERVES_PROPERTY                      = "observation:observes";

        /**
         * The ontology for all the core concepts (which depends only on BFO).
         */
        public static final String CORE_ONTOLOGY                          = "observation";

        /**
         * Only class that subsumes both observables and observations. It's bfo:entity in label.
         */
        public static final String CORE_PARTICULAR                        = "bfo:BFO_0000001";

        /**
         * Subsumes traits, domains and configurations. BFO does not still address universals, so we provide
         * it in observation.
         */
        public static final String CORE_UNIVERSAL                         = "observation:universal";

        /**
         * the root domain for the ontologies. For many reasons it becomes very difficult to keep it in the
         * imcore namespace, so we use the most general abstract in DOLCE.
         */
        public static final String CORE_DOMAIN                            = "observation:Domain";
        public static final String CORE_OBSERVABLE                        = "observation:Observable";
        public static final String CORE_OBSERVATION                       = "observation:Observation";
        public static final String CORE_OBJECT                            = "observation:DirectObservable";
        public static final String CORE_PROCESS                           = "observation:Process";
        public static final String CORE_QUALITY                           = "observation:Quality";
        public static final String CORE_EVENT                             = "observation:Event";
        public static final String CORE_TRAIT                             = "observation:Trait";
        public static final String CORE_IDENTITY                          = "observation:Identity";
        public static final String CORE_QUANTITY                          = "observation:ContinuousNumericallyQuantifiableQuality";
        public static final String CORE_ASSERTED_QUALITY                  = "observation:AssertedQuality";
        public static final String CORE_SUBJECT                           = "observation:Subject";
        public static final String CORE_PHYSICAL_OBJECT                   = "observation:PhysicalObject";
        public static final String CORE_PHYSICAL_PROPERTY                 = "observation:PhysicalProperty";
        public static final String CORE_EXTENSIVE_PHYSICAL_PROPERTY       = "observation:ExtensivePhysicalProperty";
        public static final String CORE_INTENSIVE_PHYSICAL_PROPERTY       = "observation:IntensivePhysicalProperty";
        public static final String CORE_ENERGY                            = "observation:Energy";
        public static final String CORE_ENTROPY                           = "observation:Entropy";
        public static final String CORE_LENGTH                            = "observation:Length";
        public static final String CORE_MASS                              = "observation:Mass";
        public static final String CORE_PROBABILITY                       = "observation:Probability";
        public static final String CORE_RELATIVE_QUANTITY                 = "observation:RelativeQuantity";
        public static final String CORE_VOLUME                            = "observation:Volume";
        public static final String CORE_WEIGHT                            = "observation:Weight";
        public static final String CORE_DURATION                          = "observation:Duration";
        public static final String CORE_MONETARY_VALUE                    = "observation:MonetaryValue";
        public static final String CORE_PREFERENCE_VALUE                  = "observation:PreferenceValue";
        public static final String CORE_ACCELERATION                      = "observation:Acceleration";
        public static final String CORE_AREA                              = "observation:Area";
        public static final String CORE_DENSITY                           = "observation:Density";
        public static final String CORE_ELECTRIC_POTENTIAL                = "observation:ElectricPotential";
        public static final String CORE_CHARGE                            = "observation:Charge";
        public static final String CORE_RESISTANCE                        = "observation:Resistance";
        public static final String CORE_RESISTIVITY                       = "observation:Resistivity";
        public static final String CORE_PRESSURE                          = "observation:Pressure";
        public static final String CORE_ANGLE                             = "observation:Angle";
        public static final String CORE_ASSESSMENT                        = "observation:Assessment";
        public static final String CORE_SPEED                             = "observation:Speed";
        public static final String CORE_TEMPERATURE                       = "observation:Temperature";
        public static final String CORE_VISCOSITY                         = "observation:Viscosity";
        public static final String CORE_AGENT                             = "observation:Agent";
        public static final String CORE_CONFIGURATION                     = "observation:Configuration";
        public static final String CORE_RELATIONSHIP                      = "observation:Relationship";
        public static final String CORE_FUNCTIONAL_RELATIONSHIP           = "observation:FunctionalRelationship";
        public static final String CORE_STRUCTURAL_RELATIONSHIP           = "observation:StructuralRelationship";
        public static final String CORE_TYPE                              = "observation:Type";
        public static final String CORE_ORDERING                          = "observation:Ordering";
        public static final String CORE_REALM                             = "observation:Realm";
        public static final String CORE_ATTRIBUTE                         = "observation:Attribute";
        public static final String CORE_ROLE                              = "observation:Role";
        public static final String CORE_PRIORITY                          = "observation:Priority";
        public static final String CORE_COUNT                             = "observation:Numerosity";
        public static final String CORE_PROPORTION                        = "observation:Proportion";
        public static final String CORE_RATIO                             = "observation:Ratio";
        public static final String CORE_PRESENCE                          = "observation:Presence";
        public static final String CORE_OCCURRENCE                        = "observation:Occurrence";
        public static final String CORE_VALUE                             = "observation:Value";
        public static final String CORE_DISTANCE                          = "observation:Distance";
        public static final String CORE_BASE_AGENT                        = "observation:Agent";
        public static final String CORE_REACTIVE_AGENT                    = "observation:ReactiveAgent";
        public static final String CORE_DELIBERATIVE_AGENT                = "observation:DeliberativeAgent";
        public static final String CORE_INTERACTIVE_AGENT                 = "observation:InteractiveAgent";
        public static final String CORE_UNCERTAINTY                       = "observation:Uncertainty";
        public static final String CORE_OBSERVABILITY_TRAIT               = "observation:Observability";
        public static final String CORE_ABSENCE_TRAIT                     = "observation:Absence";
        public static final String CORE_EXTENT                            = "observation:Extent";
    }

    public CoreOntology(File directory) {
        super(directory);
    }

    @Override
    public List<INamespace> load(boolean incremental) throws KlabException {
        List<INamespace> ret = new ArrayList<>();
        if (!synced) {
            synced = true;
            try {
                Resources.extractKnowledgeFromClasspath(getRoot());
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }
        OWL.INSTANCE.initialize(getRoot());

        for (INamespace ns : OWL.INSTANCE.getNamespaces()) {
            Namespaces.INSTANCE.registerNamespace(ns);
            ret.add(ns);
        }

        Klab.INSTANCE.info(ret.size() + " ontologies read from classpath");

        return ret;
    }

    public IConcept getCoreType(EnumSet<Type> type) {

        if (type.contains(Type.NOTHING)) {
            return OWL.INSTANCE.getNothing();
        }

        String conceptId = null;

        if (type.contains(Type.PROCESS)) {
            conceptId = NS.CORE_PROCESS;
        } else if (type.contains(Type.SUBJECT)) {
            conceptId = NS.CORE_SUBJECT;
        } else if (type.contains(Type.EVENT)) {
            conceptId = NS.CORE_EVENT;
        } else if (type.contains(Type.RELATIONSHIP)) {
            if (type.contains(Type.FUNCTIONAL)) {
                conceptId = NS.CORE_FUNCTIONAL_RELATIONSHIP;
            } else if (type.contains(Type.STRUCTURAL)) {
                conceptId = NS.CORE_STRUCTURAL_RELATIONSHIP;
            } else {
                conceptId = NS.CORE_RELATIONSHIP;
            }
        } else if (type.contains(Type.EXTENSIVE_PROPERTY)) {
            conceptId = NS.CORE_EXTENSIVE_PHYSICAL_PROPERTY;
        } else if (type.contains(Type.INTENSIVE_PROPERTY)) {
            conceptId = NS.CORE_INTENSIVE_PHYSICAL_PROPERTY;
        } else if (type.contains(Type.TRAIT)) {
            if (type.contains(Type.IDENTITY)) {
                conceptId = NS.CORE_IDENTITY;
            } else if (type.contains(Type.ATTRIBUTE)) {
                conceptId = NS.CORE_ATTRIBUTE;
            } else if (type.contains(Type.REALM)) {
                conceptId = NS.CORE_REALM;
            } else if (type.contains(Type.ORDERING)) {
                conceptId = NS.CORE_ORDERING;
            }
        } else if (type.contains(Type.ROLE)) {
            conceptId = NS.CORE_ROLE;
        } else if (type.contains(Type.CONFIGURATION)) {
            conceptId = NS.CORE_CONFIGURATION;
        } else if (type.contains(Type.CLASS)) {
            conceptId = NS.CORE_TYPE;
        } else if (type.contains(Type.QUANTITY)) {
            conceptId = NS.CORE_QUANTITY;
        } else if (type.contains(Type.DOMAIN)) {
            conceptId = NS.CORE_DOMAIN;
        } else if (type.contains(Type.ENERGY)) {
            conceptId = NS.CORE_ENERGY;
        } else if (type.contains(Type.ENTROPY)) {
            conceptId = NS.CORE_ENTROPY;
        } else if (type.contains(Type.LENGTH)) {
            conceptId = NS.CORE_LENGTH;
        } else if (type.contains(Type.MASS)) {
            conceptId = NS.CORE_MASS;
        } else if (type.contains(Type.VOLUME)) {
            conceptId = NS.CORE_VOLUME;
        } else if (type.contains(Type.WEIGHT)) {
            conceptId = NS.CORE_WEIGHT;
        } else if (type.contains(Type.MONEY)) {
            conceptId = NS.CORE_MONETARY_VALUE;
        } else if (type.contains(Type.DURATION)) {
            conceptId = NS.CORE_DURATION;
        } else if (type.contains(Type.AREA)) {
            conceptId = NS.CORE_AREA;
        } else if (type.contains(Type.ACCELERATION)) {
            conceptId = NS.CORE_ACCELERATION;
        } else if (type.contains(Type.PRIORITY)) {
            conceptId = NS.CORE_PRIORITY;
        } else if (type.contains(Type.ELECTRIC_POTENTIAL)) {
            conceptId = NS.CORE_ELECTRIC_POTENTIAL;
        } else if (type.contains(Type.CHARGE)) {
            conceptId = NS.CORE_CHARGE;
        } else if (type.contains(Type.RESISTANCE)) {
            conceptId = NS.CORE_RESISTANCE;
        } else if (type.contains(Type.RESISTIVITY)) {
            conceptId = NS.CORE_RESISTIVITY;
        } else if (type.contains(Type.PRESSURE)) {
            conceptId = NS.CORE_PRESSURE;
        } else if (type.contains(Type.ANGLE)) {
            conceptId = NS.CORE_ANGLE;
        } else if (type.contains(Type.VELOCITY)) {
            conceptId = NS.CORE_SPEED;
        } else if (type.contains(Type.TEMPERATURE)) {
            conceptId = NS.CORE_TEMPERATURE;
        } else if (type.contains(Type.VISCOSITY)) {
            conceptId = NS.CORE_VISCOSITY;
        } else if (type.contains(Type.AGENT)) {
            if (type.contains(Type.DELIBERATIVE)) {
                conceptId = NS.CORE_DELIBERATIVE_AGENT;
            } else if (type.contains(Type.INTERACTIVE)) {
                conceptId = NS.CORE_INTERACTIVE_AGENT;
            } else if (type.contains(Type.REACTIVE)) {
                conceptId = NS.CORE_REACTIVE_AGENT;
            } else {
                conceptId = NS.CORE_AGENT;
            }
        } else if (type.contains(Type.UNCERTAINTY)) {
            conceptId = NS.CORE_UNCERTAINTY;
        } else if (type.contains(Type.PROBABILITY)) {
            conceptId = NS.CORE_PROBABILITY;
        } else if (type.contains(Type.PROPORTION)) {
            conceptId = NS.CORE_PROPORTION;
        } else if (type.contains(Type.NUMEROSITY)) {
            conceptId = NS.CORE_COUNT;
        } else if (type.contains(Type.DISTANCE)) {
            conceptId = NS.CORE_DISTANCE;
        } else if (type.contains(Type.RATIO)) {
            conceptId = NS.CORE_RATIO;
        } else if (type.contains(Type.VALUE)) {
            conceptId = NS.CORE_VALUE;
        } else if (type.contains(Type.OCCURRENCE)) {
            conceptId = NS.CORE_OCCURRENCE;
        } else if (type.contains(Type.PRESENCE)) {
            conceptId = NS.CORE_PRESENCE;
        } else if (type.contains(Type.EXTENT)) {
            conceptId = NS.CORE_EXTENT;
        }

        return conceptId == null ? null : Concepts.c(conceptId);
    }

}
