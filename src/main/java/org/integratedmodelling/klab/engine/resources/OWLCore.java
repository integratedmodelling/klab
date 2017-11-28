package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.OWL;

/**
 * The core workspace only contains OWL ontologies and is read from the classpath.
 * 
 * @author ferdinando.villa
 *
 */
public class OWLCore implements IWorkspace {

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
         * Annotation contains the ID of the property (in same ontology) that will be used to
         * create restrictions to adopt the trait carrying the annotation.
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
         * Only class that subsumes both observables and observations. It's bfo:entity in
         * label.
         */
        public static final String CORE_PARTICULAR                        = "bfo:BFO_0000001";

        /**
         * Subsumes traits, domains and configurations. BFO does not still address universals,
         * so we provide it in observation.
         */
        public static final String CORE_UNIVERSAL                         = "observation:universal";

        /**
         * the root domain for the ontologies. For many reasons it becomes very difficult to
         * keep it in the imcore namespace, so we use the most general abstract in DOLCE.
         */
        public static final String CORE_DOMAIN                            = "observation:Domain";

        // core concepts to map with the language keywords
        public static final String CORE_OBSERVABLE                        = "observation:Observable";
        public static final String CORE_OBSERVATION                       = "observation:Observation";
        public static final String CORE_OBJECT                            = "observation:DirectObservable";
        public static final String CORE_PROCESS                           = "observation:Process";
        public static final String CORE_QUALITY                           = "observation:Quality";
        public static final String CORE_EVENT                             = "observation:Event";
        public static final String CORE_TRAIT                             = "observation:Trait";
        public static final String CORE_IDENTITY_TRAIT                    = "observation:Identity";
        // public static final String SUBJECTIVE_TRAIT = "observation:SubjectiveTrait";
        public static final String CORE_QUANTITY                          = "observation:QuantifiableQuality";
        public static final String CORE_NUMERIC_QUANTITY                  = "observation:ContinuousNumericallyQuantifiableQuality";
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
        public static final String CORE_PATTERN                           = "observation:Configuration";
        public static final String CORE_RELATIONSHIP                      = "observation:Relationship";
        public static final String CORE_FUNCTIONAL_RELATIONSHIP           = "observation:FunctionalRelationship";
        public static final String CORE_STRUCTURAL_RELATIONSHIP           = "observation:StructuralRelationship";
        public static final String TYPE                                   = "observation:Type";
        public static final String ORDERING                               = "observation:Ordering";
        public static final String CORE_REALM_TRAIT                       = "observation:Realm";
        public static final String ATTRIBUTE_TRAIT                        = "observation:Attribute";
        public static final String ROLE_TRAIT                             = "observation:Role";
        public static final String CORE_COUNT                             = "observation:Numerosity";
        public static final String CORE_PROPORTION                        = "observation:Proportion";
        public static final String CORE_RATIO                             = "observation:Ratio";
        public static final String CORE_PRESENCE                          = "observation:Presence";
        public static final String CORE_VALUE                             = "observation:Value";
        public static final String CORE_DISTANCE                          = "observation:Distance";
        public static final String BASE_AGENT                             = "observation:Agent";
        public static final String REACTIVE_AGENT                         = "observation:ReactiveAgent";
        public static final String DELIBERATIVE_AGENT                     = "observation:DeliberativeAgent";
        public static final String INTERACTIVE_AGENT                      = "observation:InteractiveAgent";
        public static final String CORE_UNCERTAINTY                       = "observation:Uncertainty";
        public static final String OBSERVABILITY_TRAIT                    = "observation:Observability";
        public static final String ABSENCE_TRAIT                          = "observation:Absence";
        public static final String EXTENT                                 = "observation:Extent";
    }
    
    public OWLCore(File directory) {
        super(directory);
    }

    @Override
    public List<INamespace> load(boolean incremental) throws IOException {
        List<INamespace> ret = new ArrayList<>();
        if (!synced) {
            synced = true;
            Resources.extractKnowledgeFromClasspath(getRoot());
        }
        try {
            OWL.INSTANCE.initialize(getRoot());
        } catch (KlabException e) {
            throw new IOException(e);
        }
        
        for (INamespace ns : OWL.INSTANCE.getNamespaces()) {
            Namespaces.INSTANCE.registerNamespace(ns);
            ret.add(ns);
        }
        
        Klab.INSTANCE.info(ret.size() + " ontologies read from classpath");
        
        return ret;
    }

}
