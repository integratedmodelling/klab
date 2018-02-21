package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.SemanticType;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservableService.Builder;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ObservableBuilder implements Builder {

  private IMonitor                      monitor;

  private Ontology                      ontology;

  private Concept                       main;
  private Concept                       parent;
  private String                        mainId;
  private Set<Type>                     type;
  private boolean                       negated;
  private IConcept                      inherent;
  private IConcept                      context;
  private IConcept                      compresent;
  private IConcept                      causant;
  private IConcept                      caused;
  private IConcept                      goal;
  private IConcept                      classifier;
  private IConcept                      downTo;
  private IConcept                      comparison;
  private boolean                       optional;

  private List<IConcept>                traits    = new ArrayList<>();
  private List<IConcept>                roles     = new ArrayList<>();
  private List<KlabValidationException> errors    = new ArrayList<>();

  private boolean                       isTrivial = true;

  // This is only for reporting
  private IKimConcept                   declaration;

  public ObservableBuilder(Concept main, Ontology ontology) {
    this.main = main;
    this.ontology = ontology;
    this.type = ((Concept) main).type;
  }

  public ObservableBuilder(String main, Concept parent, Ontology ontology) {
    this.mainId = main;
    this.ontology = ontology;
    this.parent = parent;
    this.type = ((Concept) parent).type;
  }

  public ObservableBuilder(String main, Set<Type> parent, Ontology ontology) {
    this.mainId = main;
    this.ontology = ontology;
    this.parent = Workspaces.INSTANCE.getUpperOntology().getCoreType(parent);
    this.type = parent;
  }

  @Override
  public Builder withDeclaration(IKimConcept declaration, IMonitor monitor) {
    this.monitor = monitor;
    return this;
  }

  @Override
  public Builder of(IConcept concept) {
    this.inherent = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder within(IConcept concept) {
    this.context = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder to(IConcept concept) {
    this.caused = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder from(IConcept concept) {
    this.causant = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder negated() {
    negated = true;
    return this;
  }

  @Override
  public Builder with(IConcept concept) {
    this.compresent = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder as(IConcept concept) {
    if (!concept.is(Type.ROLE)) {
      errors.add(new KlabValidationException("cannot use concept " + concept + " as a role"));
    }
    this.roles.add(concept);
    isTrivial = false;
    return this;
  }

  @Override
  public Builder downTo(IConcept concept) {
    this.downTo = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder by(IConcept concept) {
    // must be a trait and must be unique
    this.classifier = concept;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder withGoal(IConcept goal) {
    this.goal = goal;
    isTrivial = false;
    return this;
  }

  @Override
  public Builder contextualizedTo(IConcept context) {
    // TODO Auto-generated method stub
    return this;
  }

  @Override
  public Builder as(UnarySemanticOperator type, IConcept... participants) {

    if (participants != null) {
      this.comparison = participants[0];
      if (participants.length > 1) {
        throw new KlabRuntimeException(
            "cannot handle more than one participant concept in semantic operator");
      }
    }

    if (resolveMain()) {

      switch (type) {
        case ASSESSMENT:
          this.main = makeAssessment(this.main, false);
          break;
        case COUNT:
          this.main = makeCount(this.main, false);
          break;
        case DISTANCE:
          this.main = makeDistance(this.main, false);
          break;
        case OCCURRENCE:
          this.main = makeOccurrence(this.main, false);
          break;
        case PRESENCE:
          this.main = makePresence(this.main, false);
          break;
        case PROBABILITY:
          this.main = makeProbability(this.main, false);
          break;
        case PROPORTION:
          this.main = makeProportion(this.main, this.comparison, false);
          break;
        case RATIO:
          this.main = makeRatio(this.main, this.comparison, false);
          break;
        case UNCERTAINTY:
          this.main = makeUncertainty(this.main, false);
          break;
        case VALUE:
          this.main = makeValue(this.main, this.comparison, false);
          break;
        case OBSERVABILITY:
          break;
        case TYPE:
          this.main = makeType(this.main, false);
          break;
        default:
          break;
      }
    }

    return this;
  }

  @Override
  public Collection<KlabValidationException> getErrors() {
    return errors;
  }

  @Override
  public Builder without(Collection<IConcept> concepts) {
    return without(concepts.toArray(new IConcept[concepts.size()]));
  }

  @Override
  public Builder without(IConcept... concepts) {
    if (resolveMain()) {
      // TODO perform the extraction right here
    }
    return this;
  }

  @Override
  public Builder withTrait(IConcept... concepts) {
    for (IConcept concept : concepts) {
      if (!concept.is(Type.TRAIT)) {
        errors.add(new KlabValidationException("cannot use concept " + concept + " as a trait"));
      } else {
        traits.add(concept);
      }
    }
    isTrivial = false;
    return this;
  }

  @Override
  public Builder withTrait(Collection<IConcept> concepts) {
    return withTrait(concepts.toArray(new IConcept[concepts.size()]));
  }

  /**
   * Turn a concept into its assessment if it's not already one, implementing the corresponding
   * semantic operator.
   * 
   * @param concept the untransformed concept
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeAssessment(IConcept concept, boolean addDefinition) {

    String cName = cleanInternalId(concept.getName()) + "Assessment";

    if (!concept.is(Type.QUALITY)) {
      return null;
    }

    /*
     * make new type. Abstract quality also goes away.
     */
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.ASSESSMENT.name());

    /*
     * make a ConceptAssessment if not there, and ensure it's a continuously quantifiable quality.
     * Must be in same ontology as the original concept.
     */
    Concept ret = (Concept) concept.getOntology().getConcept(cName);

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_ASSESSMENT, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.ASSESSMENT.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = (Concept) concept.getOntology().getConcept(cName);
      OWL.INSTANCE.restrictSome(ret, Concepts.p(CoreOntology.NS.OBSERVES_PROPERTY), concept);
    }

    return ret;
  }

  /**
   * Turn a concept into its count/numerosity if it's not already one, implementing the
   * corresponding semantic operator. Also makes the original concept the numerosity's inherent.
   * 
   * @param concept the untransformed concept
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeCount(IConcept concept, boolean addDefinition) {

    /*
     * first, ensure we're counting countable things.
     */
    if (!concept.is(Type.QUALITY)) {
      return null;
    }
    if (concept.is(Type.NUMEROSITY)) {
      return (Concept) concept;
    }

    String cName = cleanInternalId(concept.getName()) + "Count";
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.COUNT.name());

    /*
     * make a ConceptCount if not there, and ensure it's a continuously quantifiable quality. Must
     * be in same ontology as the original concept.
     */
    Concept ret = (Concept) concept.getOntology().getConcept(cName);

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_COUNT, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.COUNT.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = (Concept) concept.getOntology().getConcept(cName);

      /*
       * numerosity is inherent to the thing that's counted.
       */
      OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_INHERENT_TO_PROPERTY), concept);
    }

    return ret;

  }

  /**
   * Turn a concept into the distance from it if it's not already one, implementing the
   * corresponding semantic operator.
   * 
   * @param concept the untransformed concept
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeDistance(IConcept concept, boolean addDefinition) {

    if (concept.is(Type.DISTANCE)) {
      return (Concept) concept;
    }

    if (!concept.is(Type.COUNTABLE)) {
      throw new KlabRuntimeException("cannot compute the distance to a non-countable observable");
    }

    String cName = "DistanceTo" + cleanInternalId(concept.getName());
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.DISTANCE.name());

    /*
     * make a ConceptCount if not there, and ensure it's a continuously quantifiable quality. Must
     * be in same ontology as the original concept.
     */
    IConcept ret = concept.getOntology().getConcept(cName);

    if (ret == null) {
      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_DISTANCE, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.DISTANCE.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);
    }

    return (Concept) ret;
  }

  /**
   * Turn a concept into its presence if it's not already one, implementing the corresponding
   * semantic operator. Also makes the original concept the numerosity's inherent.
   * 
   * @param concept the untransformed concept
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makePresence(IConcept concept, boolean addDefinition) {

    if (concept.is(Type.PRESENCE)) {
      return (Concept) concept;
    }

    if (concept.is(Type.QUALITY) || concept.is(Type.CONFIGURATION) || concept.is(Type.TRAIT)
        || concept.is(Type.ROLE)) {
      throw new KlabRuntimeException(
          "presence can be observed only for subjects, events, processes and relationships");
    }

    String cName = cleanInternalId(concept.getName()) + "Presence";
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.PRESENCE.name());

    /*
     * make a ConceptCount if not there, and ensure it's a continuously quantifiable quality. Must
     * be in same ontology as the original concept.
     */
    IConcept ret = concept.getOntology().getConcept(cName);

    if (ret == null) {
      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_PRESENCE, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.PRESENCE.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);

      /*
       * presence is inherent to the thing that's present.
       */
      OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_INHERENT_TO_PROPERTY), (IConcept) concept);
    }

    return (Concept) ret;
  }

  /**
   * Turn a concept into its occurrence (probability of presence) if it's not already one,
   * implementing the corresponding semantic operator. Also makes the original concept the
   * numerosity's inherent.
   * 
   * @param concept the untransformed concept. Must be a direct observable.
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeOccurrence(IConcept concept, boolean addDefinition) {

    if (concept.is(Type.OCCURRENCE)) {
      return (Concept) concept;
    }

    if (!concept.is(Type.DIRECT_OBSERVABLE)) {
      throw new KlabRuntimeException(
          "occurrences (probability of presence) can be observed only for subjects, events, processes and relationships");
    }

    String cName = cleanInternalId(concept.getName()) + "Occurrence";
    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.OCCURRENCE.name());

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_OCCURRENCE, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.OCCURRENCE.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);

      /*
       * probability is inherent to the event that's possible.
       */
      OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_INHERENT_TO_PROPERTY), concept);
    }

    return (Concept) ret;
  }

  /**
   * Turn a concept into its observability (a deniable trait) if it's not already one, implementing
   * the corresponding semantic operator. Also makes the original concept the numerosity's inherent.
   * 
   * @param concept the untransformed concept. Must be any observable.
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeObservability(IConcept concept, boolean addDefinition) {

    if (concept.is(Type.OBSERVABILITY)) {
      return (Concept) concept;
    }

    if (!concept.is(Type.OBSERVABLE)) {
      throw new KlabRuntimeException("observabilities can only be defined for observables");
    }

    String cName = cleanInternalId(concept.getName()) + "Observability";
    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.OBSERVABILITY.name());

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_OBSERVABILITY_TRAIT, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.OBSERVABILITY.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);

    }

    return (Concept) ret;
  }

  /**
   * Turn a concept into its probability if it's not already one, implementing the corresponding
   * semantic operator. Also makes the original concept the numerosity's inherent.
   * 
   * @param concept the untransformed concept. Must be an event.
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeProbability(IConcept concept, boolean addDefinition) {

    if (concept.is(Type.PROBABILITY)) {
      return (Concept) concept;
    }

    if (!concept.is(Type.EVENT)) {
      throw new KlabRuntimeException("probabilities can only be observed only for events");
    }

    String cName = cleanInternalId(concept.getName()) + "Probability";
    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.PROBABILITY.name());

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_PROBABILITY, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.PROBABILITY.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);

      /*
       * probability is inherent to the event that's possible.
       */
      OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_INHERENT_TO_PROPERTY), concept);
    }

    return (Concept) ret;
  }

  /**
   * Turn a concept into its uncertainty if it's not already one, implementing the corresponding
   * semantic operator. Also makes the original concept the numerosity's inherent.
   * 
   * @param concept the untransformed concept.
   * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
   *        used from outside the builder
   * @return the transformed concept
   */
  public static Concept makeUncertainty(IConcept concept, boolean addDefinition) {

    if (concept.is(Type.UNCERTAINTY)) {
      return (Concept) concept;
    }


    String cName = cleanInternalId(concept.getName()) + "Uncertainty";
    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.UNCERTAINTY.name());

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_UNCERTAINTY, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.UNCERTAINTY.declaration[0] + " " + concept.getDefinition()));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);
    }

    return (Concept) ret;
  }

  public static Concept makeProportion(IConcept concept, @Nullable IConcept comparison,
      boolean addDefinition) {

    if (concept.is(Type.PROPORTION)) {
      return (Concept) concept;
    }

    if (!(concept.is(Type.QUALITY) || concept.is(Type.TRAIT))
        && (comparison != null && !comparison.is(Type.QUALITY))) {
      throw new KlabRuntimeException("proportion must be of qualities or traits to qualities");
    }

    String cName = cleanInternalId(concept.getName()) + "ProportionIn"
        + (comparison == null ? "" : cleanInternalId(comparison.getName()));
    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.PROPORTION.name());

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_PROPORTION, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.PROPORTION.declaration[0] + " (" + concept.getDefinition() + ")"
                + (comparison == null ? ""
                    : (UnarySemanticOperator.PROPORTION.declaration[1] + " ("
                        + comparison.getDefinition() + ")"))));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);
    }

    return (Concept) ret;
  }

  public static Concept makeRatio(IConcept concept, IConcept comparison, boolean addDefinition) {

    /*
     * accept only two qualities of the same physical nature (TODO)
     */
    if (!(concept.is(Type.QUALITY) || concept.is(Type.TRAIT)) || !comparison.is(Type.QUALITY)) {
      throw new KlabRuntimeException(
          "ratios must be between qualities of the same nature or traits to qualities");
    }

    String cName =
        cleanInternalId(concept.getName()) + "To" + cleanInternalId(comparison.getName()) + "Ratio";
    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.RATIO.name());

    if (ret == null) {

      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_RATIO, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.RATIO.declaration[0] + " (" + concept.getDefinition() + ")"
                + (comparison == null ? ""
                    : (UnarySemanticOperator.RATIO.declaration[1] + " ("
                        + comparison.getDefinition() + ")"))));
      }
      
      // unit for ratios of physical properties
      if ((concept.is(Type.EXTENSIVE_PROPERTY) || concept.is(Type.INTENSIVE_PROPERTY))
          && (comparison.is(Type.EXTENSIVE_PROPERTY) || comparison.is(Type.INTENSIVE_PROPERTY))) {
        Object unit1 = Concepts.INSTANCE.getMetadata(concept, NS.SI_UNIT_PROPERTY);
        Object unit2 = Concepts.INSTANCE.getMetadata(comparison, NS.SI_UNIT_PROPERTY);
        if (unit1 != null && unit2 != null) {
          String unit = unit1 + "/" + unit2;
          ax.add(Axiom.AnnotationAssertion(cName, NS.SI_UNIT_PROPERTY, unit));
        }
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);
    }

    return (Concept) ret;
  }

  public static Concept makeValue(IConcept concept, IConcept comparison, boolean addDefinition) {

    if (concept.is(Type.VALUE)) {
      return (Concept) concept;
    }

    String cName = "ValueOf" + cleanInternalId(concept.getName())
        + (comparison == null ? "" : ("Over" + cleanInternalId(comparison.getName())));

    IConcept ret = concept.getOntology().getConcept(cName);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.VALUE.name());

    if (ret == null) {
      ArrayList<IAxiom> ax = new ArrayList<>();
      ax.add(Axiom.ClassAssertion(cName, newType));
      ax.add(Axiom.SubClass(NS.CORE_VALUE, cName));
      ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
      if (addDefinition) {
        ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.VALUE.declaration[0] + " (" + concept.getDefinition() + ")"
                + (comparison == null ? ""
                    : (UnarySemanticOperator.VALUE.declaration[1] + " ("
                        + comparison.getDefinition() + ")"))));
      }
      concept.getOntology().define(ax);
      ret = concept.getOntology().getConcept(cName);
    }

    return (Concept) ret;
  }

  public static Concept makeType(IConcept trait, boolean addDefinition) {

    if (trait.is(Type.CLASS)) {
      return (Concept) trait;
    }

    if (!trait.is(Type.TRAIT)) {
      throw new KlabRuntimeException("types can only be declared for traits");
    }


    String traitID = cleanInternalId(trait.getName()) + "Type";
    IConcept ret = trait.getOntology().getConcept(traitID);
    EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.TYPE.name());

    if (ret == null) {

      List<IAxiom> axioms = new ArrayList<>();
      axioms.add(Axiom.ClassAssertion(traitID, newType));
      axioms.add(Axiom.SubClass(NS.CORE_TYPE, traitID));
      axioms.add(Axiom.AnnotationAssertion(traitID, NS.BASE_DECLARATION, "true"));
      axioms.add(Axiom.AnnotationAssertion(traitID, NS.IS_TYPE_DELEGATE, "true"));
      if (addDefinition) {
        axioms.add(Axiom.AnnotationAssertion(traitID, NS.CONCEPT_DEFINITION_PROPERTY,
            UnarySemanticOperator.TYPE.declaration[0] + " " + trait.getDefinition()));
      }
      trait.getOntology().define(axioms);
      ret = trait.getOntology().getConcept(traitID);
      OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.EXPOSES_TRAIT_PROPERTY), trait);

      /*
       * types inherit the context from their trait
       */
      IConcept context = Observables.INSTANCE.getContextType(trait);
      if (context != null) {
        OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CONTEXT_PROPERTY), context);
      }
    }

    return (Concept) ret;
  }

  private boolean resolveMain() {

    if (main != null) {
      return true;
    }

    if (ontology == null) {
      if (mainId != null) {
        if (mainId.contains(":")) {
          SemanticType st = new SemanticType(mainId);
          ontology = OWL.INSTANCE.getOntology(st.getNamespace());
          mainId = st.getName();
          if ((main = ontology.getConcept(mainId)) != null) {
            mainId = null;
          }
        }
        if (ontology == null) {
          errors.add(new KlabValidationException(
              "cannot create a new concept from an ID if the ontology is not specified"));
        }
      }
    }

    return main != null;
  }

  // just remove the starting 'i' if any.
  private static String cleanInternalId(String s) {
    return s.startsWith("i") ? s.substring(1) : s;
  }

  @Override
  public Concept build() throws KlabValidationException {

    if (errors.size() > 0) {
      String message = "";
      for (KlabValidationException error : errors) {
        message += (message.isEmpty() ? "" : "\n") + error.getLocalizedMessage();
      }
      throw new KlabValidationException(message);
    }

    resolveMain();

    /*
     * correctly support trival case so we can use this without checking.
     */
    if (isTrivial()) {
      return main;
    }

    Set<IConcept> identities = new HashSet<>();
    Set<IConcept> attributes = new HashSet<>();
    Set<IConcept> realms = new HashSet<>();

    /*
     * to ensure traits are not conflicting
     */
    Set<IConcept> baseTraits = new HashSet<>();

    /*
     * to ensure we know if we concretized any abstract traits so we can properly compute our
     * abstract status.
     */
    Set<IConcept> abstractTraitBases = new HashSet<>();

    Concept ret = main;
    ArrayList<String> tids = new ArrayList<>();
    ArrayList<IConcept> keep = new ArrayList<IConcept>();

    /*
     * preload any base traits we already have. If any of them is abstract, take notice so we can
     * see if they are all concretized later.
     */
    for (IConcept c : Traits.INSTANCE.getTraits(main)) {
      IConcept base = Traits.INSTANCE.getBaseParentTrait(c);
      baseTraits.add(base);
      if (c.isAbstract()) {
        abstractTraitBases.add(base);
      }
    }

    /*
     * name and display label for the finished concept
     */
    String cId = "";
    String cDs = "";

    /*
     * we also add a non-by, non-down-to concept (untrasformed) if absent, so that we can
     * reconstruct an observable without transformations but with all traits and roles if required.
     * This is returned by getUntransformedObservable().
     * 
     * TODO this is related to the observable not being the same as the declared - see what's the
     * best way to handle this. Better to build the concept using only the declaration, then create
     * the other using the same axioms + by/downto if needed. No need for a special uId - just do it
     * after.
     */
    String uId = "";

    if (traits != null && traits.size() > 0) {

      for (IConcept t : traits) {

        if (t.equals(main)) {
          continue;
        }

        if (Traits.INSTANCE.getTraits(main).contains(t)) {
          throw new KlabValidationException("concept " + Concepts.INSTANCE.getDisplayName(main)
              + " already adopts trait " + Concepts.INSTANCE.getDisplayName(t));
        }

        if (t.is(Type.IDENTITY)) {
          identities.add(t);
        } else if (t.is(Type.REALM)) {
          realms.add(t);
        } else if (!t.is(Type.SUBJECTIVE)) {
          attributes.add(t);
        }

        IConcept base = Traits.INSTANCE.getBaseParentTrait(t);

        if (base == null) {
          throw new KlabValidationException("base declaration for trait " + t + " cannot be found");
        }

        if (!baseTraits.add(base)) {
          throw new KlabValidationException("cannot add trait "
              + Concepts.INSTANCE.getDisplayName(t) + " to concept " + main
              + " as it already adopts a trait of type " + Concepts.INSTANCE.getDisplayName(base));
        }

        if (t.isAbstract()) {
          abstractTraitBases.add(base);
        } else {
          abstractTraitBases.remove(base);
        }

        tids.add(Concepts.INSTANCE.getDisplayName(t));

      }
    }

    /*
     * FIXME using the display name to build an ID is wrong and forces us to use display names that
     * are legal for concept names. The two should work independently.
     */
    if (tids.size() > 0) {
      Collections.sort(tids);
      for (String s : tids) {
        cId += cleanInternalId(s);
        cDs += cleanInternalId(s);
        uId += cleanInternalId(s);
      }
    }

    /*
     * add the main identity to the ID after all traits and before any context
     */
    cId += cleanInternalId(main.getName());
    cDs += cleanInternalId(main.getName());
    uId += cleanInternalId(main.getName());

    /*
     * handle context, inherency etc.
     */
    if (inherent != null) {
      IConcept other = Observables.INSTANCE.getInherentType(main);
      if (other != null && !Observables.INSTANCE.isCompatible(inherent, other)) {
        throw new KlabValidationException(
            "cannot add inherent type " + Concepts.INSTANCE.getDisplayName(inherent)
                + " to concept " + Concepts.INSTANCE.getDisplayName(main)
                + " as it already has an incompatible inherency: "
                + Concepts.INSTANCE.getDisplayName(other));
      }
      cId += "Of" + cleanInternalId(inherent.getName());
      cDs += "Of" + cleanInternalId(inherent.getName());
      uId += "Of" + cleanInternalId(inherent.getName());
    }

    if (context != null) {
      IConcept other = Observables.INSTANCE.getContextType(main);
      if (other != null && !Observables.INSTANCE.isCompatible(context, other)) {
        throw new KlabValidationException(
            "cannot add context " + Concepts.INSTANCE.getDisplayName(context) + " to concept "
                + Concepts.INSTANCE.getDisplayName(main)
                + " as it already has an incompatible context: "
                + Concepts.INSTANCE.getDisplayName(other));
      }
      cId += "In" + cleanInternalId(context.getName());
      cDs += "In" + cleanInternalId(context.getName());
      uId += "In" + cleanInternalId(context.getName());
    }

    if (compresent != null) {
      IConcept other = Observables.INSTANCE.getCompresentType(main);
      if (other != null && !Observables.INSTANCE.isCompatible(compresent, other)) {
        throw new KlabValidationException(
            "cannot add compresent " + Concepts.INSTANCE.getDisplayName(compresent) + " to concept "
                + Concepts.INSTANCE.getDisplayName(main)
                + " as it already has an incompatible compresent type: "
                + Concepts.INSTANCE.getDisplayName(other));
      }
      cId += "With" + cleanInternalId(compresent.getName());
      cDs += "With" + cleanInternalId(compresent.getName());
      uId += "With" + cleanInternalId(compresent.getName());
    }

    if (goal != null) {
      // TODO transform as necessary
      IConcept other = Observables.INSTANCE.getGoalType(main);
      if (other != null && !Observables.INSTANCE.isCompatible(goal, other)) {
        throw new KlabValidationException(
            "cannot add goal " + Concepts.INSTANCE.getDisplayName(goal) + " to concept "
                + Concepts.INSTANCE.getDisplayName(main)
                + " as it already has an incompatible goal type: "
                + Concepts.INSTANCE.getDisplayName(other));
      }
      cId += "For" + cleanInternalId(goal.getName());
      cDs += "For" + cleanInternalId(goal.getName());
      uId += "For" + cleanInternalId(goal.getName());
    }

    if (caused != null) {
      IConcept other = Observables.INSTANCE.getCausedType(main);
      if (other != null && !Observables.INSTANCE.isCompatible(caused, other)) {
        throw new KlabValidationException(
            "cannot add caused " + Concepts.INSTANCE.getDisplayName(caused) + " to concept "
                + Concepts.INSTANCE.getDisplayName(main)
                + " as it already has an incompatible caused type: "
                + Concepts.INSTANCE.getDisplayName(other));
      }
      cId += "To" + cleanInternalId(caused.getName());
      cDs += "To" + cleanInternalId(caused.getName());
      uId += "To" + cleanInternalId(caused.getName());
    }

    if (causant != null) {
      IConcept other = Observables.INSTANCE.getCausantType(main);
      if (other != null && !Observables.INSTANCE.isCompatible(causant, other)) {
        throw new KlabValidationException(
            "cannot add causant " + Concepts.INSTANCE.getDisplayName(causant) + " to concept "
                + Concepts.INSTANCE.getDisplayName(main)
                + " as it already has an incompatible causant type: "
                + Concepts.INSTANCE.getDisplayName(other));
      }
      cId += "From" + cleanInternalId(causant.getName());
      cDs += "From" + cleanInternalId(causant.getName());
      uId += "From" + cleanInternalId(causant.getName());
    }

    String roleIds = "";
    List<String> rids = new ArrayList<>();
    Set<IConcept> acceptedRoles = new HashSet<>();

    if (roles != null && roles.size() > 0) {
      for (IConcept role : roles) {
        if (Roles.INSTANCE.getRoles(main).contains(role)) {
          throw new KlabValidationException("concept " + Concepts.INSTANCE.getDisplayName(main)
              + " already has role " + Concepts.INSTANCE.getDisplayName(role));
        }
        rids.add(Concepts.INSTANCE.getDisplayName(role));
        acceptedRoles.add(role);
      }
    }

    if (rids.size() > 0) {
      Collections.sort(rids);
      for (String s : rids) {
        roleIds += s;
      }
    }

    /*
     * add the main identity to the ID after all traits and before any context
     */
    if (!roleIds.isEmpty()) {
      cId += "As" + roleIds;
      // only add role names to user description if roles are not from the
      // root of the worldview
      if (!rolesAreFundamental(roles)) {
        cDs = roleIds + Concepts.INSTANCE.getDisplayName(main);
      }
    }

    /*
     * Add a lowercase prefix to the ID to ensure no conflict can exist with a situation like
     * "TraitConcept is Trait Concept". The name without prefix will be in the display label
     * annotation.
     */
    cId = "i" + cId;
    ret = ontology.getConcept(cId);

    if (ret == null) {

      List<IAxiom> axioms = new ArrayList<>();
      axioms.add(Axiom.ClassAssertion(cId, type));
      axioms.add(Axiom.AnnotationAssertion(cId, NS.DISPLAY_LABEL_PROPERTY, cDs));

      /*
       * add the core observable concept ID using NS.CORE_OBSERVABLE_PROPERTY
       */
      axioms.add(Axiom.AnnotationAssertion(cId, NS.CORE_OBSERVABLE_PROPERTY, main.toString()));

      // /*
      // * if there is a 'by', this is the child of the class that exposes it, not the
      // * original concept's.
      // */
      // axioms.add(Axiom
      // .SubClass((byTrait == null ? main.toString() : makeTypeFor(byTrait).toString()), cId));
      //
      // if (needUntransformed) {
      // axioms.add(Axiom.SubClass(main.toString(), uId));
      // }
      //

      if (type.contains(Type.ABSTRACT)) {
        axioms.add(Axiom.AnnotationAssertion(cId, NS.IS_ABSTRACT, "true"));
      }

      // Set<IConcept> allowedDetail = new HashSet<>();
      //
      // if (byTrait != null) {
      //
      // if (!NS.isTrait(byTrait)) {
      // throw new KlabValidationException("the concept in a 'by' clause must be a base abstract
      // trait");
      // }
      //
      // /*
      // * TODO trait must be a base trait and abstract.
      // */
      // if (!NS.isBaseDeclaration(byTrait) || !byTrait.isAbstract()) {
      // throw new KlabValidationException("traits used in a 'by' clause must be abstract and
      // declared
      // at
      // root level");
      // }
      // cId += "By" + cleanInternalId(byTrait.getLocalName());
      // cDs += "By" + cleanInternalId(byTrait.getLocalName());
      // byDefinition = byTrait.getDefinition();
      // makeAbstract = true;
      // }
      //
      // if (downTo != null) {
      // IConcept trait = byTrait == null ? main : byTrait;
      // if (!NS.isTrait(trait)) {
      // throw new KlabValidationException("cannot use 'down to' on non-trait observables");
      // }
      // allowedDetail.addAll(Types.getChildrenAtLevel(trait, Types.getDetailLevel(trait, downTo)));
      // cId += "DownTo" + cleanInternalId(downTo.getLocalName());
      // // display label stays the same
      // downToDefinition = downTo.getDefinition();
      // }

      ontology.define(axioms);
      ret = ontology.getConcept(cId);

      /*
       * restrictions
       */

      if (identities.size() > 0) {
        Traits.INSTANCE.restrict(ret, Concepts.p(NS.HAS_IDENTITY_PROPERTY),
            LogicalConnector.INTERSECTION, identities);
      }
      if (realms.size() > 0) {
        Traits.INSTANCE.restrict(ret, Concepts.p(NS.HAS_REALM_PROPERTY),
            LogicalConnector.INTERSECTION, realms);
      }
      if (attributes.size() > 0) {
        Traits.INSTANCE.restrict(ret, Concepts.p(NS.HAS_ATTRIBUTE_PROPERTY),
            LogicalConnector.INTERSECTION, attributes);
      }
      if (acceptedRoles.size() > 0) {
        OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_ROLE_PROPERTY),
            LogicalConnector.INTERSECTION, acceptedRoles);
      }
      if (inherent != null) {
        OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_INHERENT_TO_PROPERTY), inherent);
      }
      if (context != null) {
        OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CONTEXT_PROPERTY), context);
      }

      // if (byTrait != null) {
      // OWL.restrictSome(ret, KLAB.p(NS.REPRESENTED_BY_PROPERTY), byTrait);
      // }
      // if (downTo != null) {
      // OWL.restrictSome(ret, KLAB.p(NS.LIMITED_BY_PROPERTY), LogicalConnector.UNION,
      // allowedDetail);
      // }

      if (monitor != null && !Reasoner.INSTANCE.isSatisfiable(ret)) {
        monitor.error("this declaration has logical errors and is inconsistent", declaration);
      }

    }

    if (negated) {
      // TODO - add Not to the ids
    }

    return ret;
  }

  private static boolean rolesAreFundamental(Collection<IConcept> roles) {
    for (IConcept c : roles) {
      if (Workspaces.INSTANCE.getWorldview() != null
          && !c.getNamespace().equals(Workspaces.INSTANCE.getWorldview().getName())) {
        return false;
      }
    }
    return true;
  }

  private boolean isTrivial() {
    return isTrivial;
  }

  @Override
  public Builder optional() {
    this.optional = true;
    return this;
  }
}
