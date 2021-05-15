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
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Ontologies;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.Builder;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;

public class ObservableBuilder implements IObservable.Builder {

    private IMonitor monitor;

    private Ontology ontology;

    private Concept main;
    private String mainId;
    private Set<Type> type;
    private IConcept inherent;
    private IConcept context;
    private IConcept compresent;
    private IConcept causant;
    private IConcept caused;
    private IConcept goal;
    private IConcept cooccurrent;
    private IConcept adjacent;
    private IConcept comparison;
    private IConcept relationshipSource;
    private IConcept relationshipTarget;
    private boolean optional;
    private String name;
    private IConcept targetPredicate;
    private IConcept temporalInherent;
    private boolean mustContextualize = false;
    private String statedName;

    private List<IConcept> traits = new ArrayList<>();
    private List<IConcept> roles = new ArrayList<>();
    private List<IConcept> removed = new ArrayList<>();
    private List<Pair<ValueOperator, Object>> valueOperators = new ArrayList<>();
    private List<KlabValidationException> errors = new ArrayList<>();
    private IUnit unit;
    private ICurrency currency;
    private List<IAnnotation> annotations = new ArrayList<>();
    private String dereifiedAttribute;
    private boolean isTrivial = true;
    private boolean distributedInherency = false;
    private KimConcept declaration;
    private boolean axiomsAdded = false;

    // this gets set to true if a finished declaration is set using
    // withDeclaration() and the
    // builder is merely building it.
    private boolean declarationIsComplete = false;

    public static ObservableBuilder getBuilder(IObservable observable, IMonitor monitor) {
        return new ObservableBuilder((Observable) observable, monitor);
    }

    public static ObservableBuilder getBuilder(IConcept concept, IMonitor monitor) {
        return new ObservableBuilder(concept, monitor);
    }

    public ObservableBuilder(Concept main, Ontology ontology, IMonitor monitor) {
        this.main = main;
        this.ontology = ontology;
        this.declaration = Concepts.INSTANCE.getDeclaration(main);
        this.type = ((Concept) main).type;
        this.monitor = monitor;
    }

    public ObservableBuilder(IConcept main, IMonitor monitor) {
        this.main = (Concept) main;
        this.ontology = (Ontology) main.getOntology();
        this.declaration = Concepts.INSTANCE.getDeclaration(main);
        this.type = ((Concept) main).type;
        this.monitor = monitor;
    }

    /**
     * Copies all info from the first level of specification of the passed observable. Will retain
     * the original semantics, so it won't separate prefix operators from the original observables:
     * at the moment it will simply collect the traits, roles, and operands of infix operators.
     * 
     * @param observable
     */
    public ObservableBuilder(Observable observable, IMonitor monitor) {

        /*
         * TODO the operator should be separated based on the type, and added back when
         * reconstructing.
         */

        this.main = (Concept) Observables.INSTANCE.getRawObservable(observable.getType());
        this.type = this.main.getTypeSet();
        this.ontology = (Ontology) observable.getType().getOntology();
        this.context = Observables.INSTANCE.getDirectContextType(observable.getType());
        this.adjacent = Observables.INSTANCE.getDirectAdjacentType(observable.getType());
        this.inherent = Observables.INSTANCE.getDirectInherentType(observable.getType());
        this.causant = Observables.INSTANCE.getDirectCausantType(observable.getType());
        this.caused = Observables.INSTANCE.getDirectCausedType(observable.getType());
        this.cooccurrent = Observables.INSTANCE.getDirectCooccurrentType(observable.getType());
        this.goal = Observables.INSTANCE.getDirectGoalType(observable.getType());
        this.compresent = Observables.INSTANCE.getDirectCompresentType(observable.getType());
        this.declaration = Concepts.INSTANCE.getDeclaration(observable.getType());
        this.mustContextualize = observable.mustContextualizeAtResolution();
        this.temporalInherent = observable.getTemporalInherent();
        this.statedName = observable.getStatedName();
        this.annotations.addAll(observable.getAnnotations());

        for (IConcept role : Roles.INSTANCE.getDirectRoles(observable.getType())) {
            this.roles.add(role);
        }
        for (IConcept trait : Traits.INSTANCE.getDirectTraits(observable.getType())) {
            this.traits.add(trait);
        }

        this.isTrivial = this.context == null && this.adjacent == null && this.inherent == null && this.causant == null
                && this.caused == null && this.cooccurrent == null && this.goal == null && this.compresent == null
                && this.roles.isEmpty() && this.traits.isEmpty();

        // these are only used if buildObservable() is called
        this.unit = observable.getUnit();
        this.currency = observable.getCurrency();
        this.valueOperators.addAll(observable.getValueOperators());
        this.monitor = monitor;
    }

    public ObservableBuilder(ObservableBuilder other) {

        this.main = other.main;
        this.adjacent = other.adjacent;
        this.causant = other.causant;
        this.caused = other.caused;
        this.comparison = other.comparison;
        this.compresent = other.compresent;
        this.context = other.context;
        this.inherent = other.inherent;
        this.cooccurrent = other.cooccurrent;
        this.goal = other.goal;
        this.traits.addAll(other.traits);
        this.roles.addAll(other.roles);
        this.ontology = other.ontology;
        this.type = other.type;
        this.declaration = other.declaration;
        this.monitor = other.monitor;
        this.valueOperators.addAll(other.valueOperators);
        this.mustContextualize = other.mustContextualize;
        this.annotations.addAll(other.annotations);
        this.temporalInherent = other.temporalInherent;
        this.statedName = other.statedName;

        checkTrivial();
    }

    @Override
    public Builder withDeclaration(IKimConcept declaration, IMonitor monitor) {
        this.declaration = (KimConcept) declaration;
        this.monitor = monitor;
        this.declarationIsComplete = true;
        return this;
    }

    @Override
    public Builder of(IConcept concept) {
        this.inherent = concept;
        if (!declarationIsComplete) {
            this.declaration.setInherent((KimConcept) Concepts.INSTANCE.getDeclaration(concept));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder optional(boolean optional) {
        this.optional = optional;
        return this;
    }

    @Override
    public Builder within(IConcept concept) {
        this.context = concept;
        if (this.declaration != null) {
            this.declaration.setContext((KimConcept) Concepts.INSTANCE.getDeclaration(concept));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withTemporalInherent(IConcept concept) {
        this.temporalInherent = concept;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder to(IConcept concept) {
        this.caused = concept;
        if (!declarationIsComplete) {
            this.declaration.setCaused((KimConcept) Concepts.INSTANCE.getDeclaration(concept));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder from(IConcept concept) {
        this.causant = concept;
        if (!declarationIsComplete) {
            this.declaration.setCausant((KimConcept) Concepts.INSTANCE.getDeclaration(concept));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder with(IConcept concept) {
        this.compresent = concept;
        if (!declarationIsComplete) {
            this.declaration.setCompresent((KimConcept) Concepts.INSTANCE.getDeclaration(concept));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withRole(IConcept concept) {
        if (!concept.is(Type.ROLE)) {
            errors.add(new KlabValidationException("cannot use concept " + concept + " as a role"));
        }
        if (!declarationIsComplete) {
            this.declaration.getRoles().add(Concepts.INSTANCE.getDeclaration(concept));
        }
        this.roles.add(concept);
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withGoal(IConcept goal) {
        this.goal = goal;
        if (!declarationIsComplete) {
            this.declaration.setMotivation((KimConcept) Concepts.INSTANCE.getDeclaration(goal));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withCooccurrent(IConcept cooccurrent) {
        this.cooccurrent = cooccurrent;
        if (!declarationIsComplete) {
            this.declaration.setCooccurrent((KimConcept) Concepts.INSTANCE.getDeclaration(cooccurrent));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder withAdjacent(IConcept adjacent) {
        this.adjacent = adjacent;
        if (!declarationIsComplete) {
            this.declaration.setAdjacent((KimConcept) Concepts.INSTANCE.getDeclaration(adjacent));
        }
        isTrivial = false;
        return this;
    }

    @Override
    public Builder linking(IConcept source, IConcept target) {
        this.relationshipSource = source;
        this.relationshipTarget = target;
        isTrivial = false;
        return this;
    }

    @Override
    public Builder as(UnarySemanticOperator type, IConcept... participants) throws KlabValidationException {

        Concept argument = null;
        if (resolveMain()) {
            argument = getArgumentBuilder().buildConcept();
        }

        if (!declarationIsComplete) {
            this.declaration.setObservationType(type);
        }

        if (participants != null && participants.length > 0) {
            this.comparison = participants[0];
            if (!declarationIsComplete) {
                this.declaration.setOtherConcept(Concepts.INSTANCE.getDeclaration(participants[0]));
            }
            if (participants.length > 1) {
                throw new KlabValidationException("cannot handle more than one participant concept in semantic operator");
            }
        }

        if (argument != null) {

            switch(type) {
            case ASSESSMENT:
                reset(makeAssessment(argument, true));
                break;
            case CHANGE:
                reset(makeChange(argument, true));
                break;
            case COUNT:
                reset(makeCount(argument, true));
                break;
            case DISTANCE:
                reset(makeDistance(argument, true));
                break;
            case OCCURRENCE:
                reset(makeOccurrence(argument, true));
                break;
            case PRESENCE:
                reset(makePresence(argument, true));
                break;
            case PROBABILITY:
                reset(makeProbability(argument, true));
                break;
            case PROPORTION:
                reset(makeProportion(argument, this.comparison, true, false));
                break;
            case PERCENTAGE:
                reset(makeProportion(argument, this.comparison, true, true));
                break;
            case RATIO:
                reset(makeRatio(argument, this.comparison, true));
                break;
            case UNCERTAINTY:
                reset(makeUncertainty(argument, true));
                break;
            case VALUE:
            case MONETARY_VALUE:
                reset(makeValue(argument, this.comparison, true, type == UnarySemanticOperator.MONETARY_VALUE));
                break;
            case OBSERVABILITY:
                reset(makeObservability(argument, true));
                break;
            case MAGNITUDE:
                reset(makeMagnitude(argument, true));
                break;
            case LEVEL:
                reset(makeLevel(argument, true));
                break;
            case TYPE:
                reset(makeType(argument, true));
                break;
            default:
                break;
            }
        }

        return this;
    }

    /**
     * Copy the builder exactly but revise the declaration so that it does not include the operator.
     * 
     * @return a new builder for the concept w/o operator
     */
    private ObservableBuilder getArgumentBuilder() {
        ObservableBuilder ret = new ObservableBuilder(this);
        ret.declaration = declaration.removeOperator();
        ret.type = ret.declaration.getType();
        return ret;
    }

    private void reset(Concept main) {
        this.main = main;
        this.type = main.type;
        traits.clear();
        roles.clear();
        comparison = context = inherent = /* classifier = downTo = */ caused = compresent = inherent = null;
        isTrivial = true;
        // declaration remains the same
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
    public Builder without(ObservableRole... roles) {

        KimConcept newDeclaration = this.declaration.removeComponents(roles);
        ObservableBuilder ret = new ObservableBuilder(Concepts.INSTANCE.declare(newDeclaration), monitor);

        /*
         * copy the rest
         */
        ret.unit = unit;
        ret.currency = currency;
        ret.valueOperators.addAll(valueOperators);
        ret.name = name;
        ret.targetPredicate = targetPredicate;
        // ret.filteredObservable = filteredObservable;
        ret.optional = this.optional;
        ret.mustContextualize = mustContextualize;
        ret.annotations.addAll(annotations);

        return ret;
    }

    @Override
    public Builder withoutAny(Collection<IConcept> concepts) {
        return withoutAny(concepts.toArray(new IConcept[concepts.size()]));
    }

    @Override
    public Builder without(IConcept... concepts) {

        ObservableBuilder ret = new ObservableBuilder(this);
        List<ObservableRole> removedRoles = new ArrayList<>();
        for (IConcept concept : concepts) {
            Pair<Collection<IConcept>, Collection<IConcept>> tdelta = Concepts.INSTANCE.copyWithout(ret.traits, concept);
            ret.traits = new ArrayList<>(tdelta.getFirst());
            ret.removed.addAll(tdelta.getSecond());
            for (int i = 0; i < tdelta.getSecond().size(); i++) {
                removedRoles.add(ObservableRole.TRAIT);
            }
            Pair<Collection<IConcept>, Collection<IConcept>> rdelta = Concepts.INSTANCE.copyWithout(ret.roles, concept);
            ret.roles = new ArrayList<>(rdelta.getFirst());
            ret.removed.addAll(rdelta.getSecond());
            for (int i = 0; i < rdelta.getSecond().size(); i++) {
                removedRoles.add(ObservableRole.ROLE);
            }
            if (ret.context != null && ret.context.equals(concept)) {
                ret.context = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.CONTEXT);
            }
            if (ret.inherent != null && ret.inherent.equals(concept)) {
                ret.inherent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.INHERENT);
            }
            if (ret.adjacent != null && ret.adjacent.equals(concept)) {
                ret.adjacent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.ADJACENT);
            }
            if (ret.caused != null && ret.caused.equals(concept)) {
                ret.caused = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.CAUSED);
            }
            if (ret.causant != null && ret.causant.equals(concept)) {
                ret.causant = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.CAUSANT);
            }
            if (ret.compresent != null && ret.compresent.equals(concept)) {
                ret.compresent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.COMPRESENT);
            }
            if (ret.goal != null && ret.goal.equals(concept)) {
                ret.goal = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.GOAL);
            }
            if (ret.cooccurrent != null && ret.cooccurrent.equals(concept)) {
                ret.cooccurrent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.COOCCURRENT);
            }
            if (ret.temporalInherent != null && ret.temporalInherent.is(concept)) {
                ret.temporalInherent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.TEMPORAL_INHERENT);
            }
        }
        if (ret.removed.size() > 0) {
            List<String> declarations = new ArrayList<>();
            for (IConcept r : ret.removed) {
                declarations.add(r.getDefinition());
            }
            ret.declaration = ret.declaration.removeComponents(declarations, removedRoles);
        }

        ret.checkTrivial();

        return ret;
    }

    @Override
    public Builder withoutAny(IKimConcept.Type... concepts) {

        ObservableBuilder ret = new ObservableBuilder(this);
        List<ObservableRole> removedRoles = new ArrayList<>();
        for (IKimConcept.Type concept : concepts) {
            Pair<Collection<IConcept>, Collection<IConcept>> tdelta = Concepts.INSTANCE.copyWithoutAny(ret.traits, concept);
            ret.traits = new ArrayList<>(tdelta.getFirst());
            ret.removed.addAll(tdelta.getSecond());
            for (int i = 0; i < tdelta.getSecond().size(); i++) {
                removedRoles.add(ObservableRole.TRAIT);
            }
            Pair<Collection<IConcept>, Collection<IConcept>> rdelta = Concepts.INSTANCE.copyWithoutAny(ret.roles, concept);
            ret.roles = new ArrayList<>(rdelta.getFirst());
            ret.removed.addAll(rdelta.getSecond());
            for (int i = 0; i < tdelta.getSecond().size(); i++) {
                removedRoles.add(ObservableRole.ROLE);
            }
            if (ret.context != null && ret.context.is(concept)) {
                ret.removed.add(ret.context);
                ret.context = null;
                removedRoles.add(ObservableRole.CONTEXT);
            }
            if (ret.inherent != null && ret.inherent.is(concept)) {
                ret.removed.add(ret.inherent);
                ret.inherent = null;
                removedRoles.add(ObservableRole.INHERENT);
            }
            if (ret.adjacent != null && ret.adjacent.is(concept)) {
                ret.removed.add(ret.adjacent);
                ret.adjacent = null;
                removedRoles.add(ObservableRole.ADJACENT);
            }
            if (ret.caused != null && ret.caused.is(concept)) {
                ret.removed.add(ret.caused);
                ret.caused = null;
                removedRoles.add(ObservableRole.CAUSED);
            }
            if (ret.causant != null && ret.causant.is(concept)) {
                ret.removed.add(ret.causant);
                ret.causant = null;
                removedRoles.add(ObservableRole.CAUSANT);
            }
            if (ret.compresent != null && ret.compresent.is(concept)) {
                ret.removed.add(ret.compresent);
                ret.compresent = null;
                removedRoles.add(ObservableRole.COMPRESENT);
            }
            if (ret.goal != null && ret.goal.is(concept)) {
                ret.removed.add(ret.goal);
                ret.goal = null;
                removedRoles.add(ObservableRole.GOAL);
            }
            if (ret.cooccurrent != null && ret.cooccurrent.is(concept)) {
                ret.removed.add(ret.cooccurrent);
                ret.cooccurrent = null;
                removedRoles.add(ObservableRole.COOCCURRENT);
            }
            if (ret.temporalInherent != null && ret.temporalInherent.is(concept)) {
                ret.temporalInherent = null;
                ret.removed.add(ret.temporalInherent);
                removedRoles.add(ObservableRole.TEMPORAL_INHERENT);
            }
        }
        if (ret.removed.size() > 0) {
            List<String> declarations = new ArrayList<>();
            for (IConcept r : ret.removed) {
                declarations.add(r.getDefinition());
            }
            ret.declaration = ret.declaration.removeComponents(declarations, removedRoles);
        }

        ret.checkTrivial();

        return ret;

    }

    @Override
    public Builder withoutAny(IConcept... concepts) {

        ObservableBuilder ret = new ObservableBuilder(this);
        List<ObservableRole> removedRoles = new ArrayList<>();
        for (IConcept concept : concepts) {
            Pair<Collection<IConcept>, Collection<IConcept>> tdelta = Concepts.INSTANCE.copyWithoutAny(ret.traits, concept);
            ret.traits = new ArrayList<>(tdelta.getFirst());
            ret.removed.addAll(tdelta.getSecond());
            for (int i = 0; i < tdelta.getSecond().size(); i++) {
                removedRoles.add(ObservableRole.TRAIT);
            }
            Pair<Collection<IConcept>, Collection<IConcept>> rdelta = Concepts.INSTANCE.copyWithoutAny(ret.roles, concept);
            ret.roles = new ArrayList<>(rdelta.getFirst());
            ret.removed.addAll(rdelta.getSecond());
            for (int i = 0; i < tdelta.getSecond().size(); i++) {
                removedRoles.add(ObservableRole.ROLE);
            }
            if (ret.context != null && ret.context.is(concept)) {
                ret.context = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.CONTEXT);
            }
            if (ret.inherent != null && ret.inherent.is(concept)) {
                ret.inherent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.INHERENT);
            }
            if (ret.adjacent != null && ret.adjacent.is(concept)) {
                ret.adjacent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.ADJACENT);
            }
            if (ret.caused != null && ret.caused.is(concept)) {
                ret.caused = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.CAUSED);
            }
            if (ret.causant != null && ret.causant.is(concept)) {
                ret.causant = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.CAUSANT);
            }
            if (ret.compresent != null && ret.compresent.is(concept)) {
                ret.compresent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.COMPRESENT);
            }
            if (ret.goal != null && ret.goal.is(concept)) {
                ret.goal = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.GOAL);
            }
            if (ret.cooccurrent != null && ret.cooccurrent.is(concept)) {
                ret.cooccurrent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.COOCCURRENT);
            }
            if (ret.temporalInherent != null && ret.temporalInherent.is(concept)) {
                ret.temporalInherent = null;
                ret.removed.add(concept);
                removedRoles.add(ObservableRole.TEMPORAL_INHERENT);
            }
        }
        if (ret.removed.size() > 0) {
            List<String> declarations = new ArrayList<>();
            for (IConcept r : ret.removed) {
                declarations.add(r.getDefinition());
            }
            ret.declaration = ret.declaration.removeComponents(declarations, removedRoles);
        }

        ret.checkTrivial();

        return ret;

    }

    void checkTrivial() {
        this.isTrivial = causant == null && adjacent == null && caused == null && comparison == null && compresent == null
                && context == null && inherent == null && cooccurrent == null & goal == null && traits.isEmpty()
                && roles.isEmpty();
    }

    @Override
    public Builder withTrait(IConcept... concepts) {
        for (IConcept concept : concepts) {
            if (!concept.is(Type.TRAIT)) {
                errors.add(new KlabValidationException("cannot use concept " + concept + " as a trait"));
            } else {
                traits.add(concept);
                if (!declarationIsComplete) {
                    this.declaration.getTraits().add(Concepts.INSTANCE.getDeclaration(concept));
                }
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
     * Turn a concept into its change if it's not already one, implementing the corresponding
     * semantic operator.
     * 
     * @param concept the untransformed concept
     * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
     *        used from outside the builder
     * @return the transformed concept
     */
    public Concept makeChange(IConcept concept, boolean addDefinition) {

        String cName = getCleanId(concept) + "Change";

        if (!concept.is(Type.QUALITY)) {
            return null;
        }

        String definition = UnarySemanticOperator.CHANGE.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);
        IConcept context = Observables.INSTANCE.getContextType(concept);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.CHANGE.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_CHANGE, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));

            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);

            IConcept ret = ontology.getConcept(conceptId);

            OWL.INSTANCE.restrictSome(ret, Concepts.p(CoreOntology.NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
            OWL.INSTANCE.restrictSome(ret, Concepts.p(CoreOntology.NS.CHANGES_PROPERTY), concept, ontology);

            /*
             * context of the change is the same context as the quality it describes - FIXME this
             * shouldn't be needed as the inherency is an alternative place to look for context.
             */
            if (context != null) {
                OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CONTEXT_PROPERTY), context, ontology);
            }

        }

        return ontology.getConcept(conceptId);
    }

    /**
     * Turn a concept into its assessment if it's not already one, implementing the corresponding
     * semantic operator (legacy and eventually, probably, deprecated)
     * 
     * @param concept the untransformed concept
     * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
     *        used from outside the builder
     * @return the transformed concept
     */
    public Concept makeAssessment(IConcept concept, boolean addDefinition) {

        String cName = getCleanId(concept) + "Assessment";

        if (!concept.is(Type.QUALITY)) {
            return null;
        }

        String definition = UnarySemanticOperator.ASSESSMENT.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.ASSESSMENT.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_ASSESSMENT, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));

            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);
            OWL.INSTANCE.restrictSome(ret, Concepts.p(CoreOntology.NS.OBSERVES_PROPERTY), concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makeCount(IConcept concept, boolean addDefinition) {

        /*
         * first, ensure we're counting countable things.
         */
        if (!concept.is(Type.COUNTABLE)) {
            monitor.error("cannot count a non-countable observable", declaration);
        }

        String cName = getCleanId(concept) + "Count";

        /*
         * make a ConceptCount if not there, and ensure it's a continuously quantifiable quality.
         * Must be in same ontology as the original concept.
         */
        String definition = UnarySemanticOperator.COUNT.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.COUNT.name());
            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_COUNT, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);

            /*
             * numerosity is inherent to the thing that's counted.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
        }

        return ontology.getConcept(conceptId);

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
    public Concept makeDistance(IConcept concept, boolean addDefinition) {

        if (!concept.is(Type.COUNTABLE)) {
            monitor.error("cannot compute the distance to a non-countable observable", declaration);
        }

        String cName = "DistanceTo" + getCleanId(concept);
        String definition = UnarySemanticOperator.DISTANCE.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.DISTANCE.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_DISTANCE, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);

            IConcept ret = ontology.getConcept(conceptId);
            /*
             * distance is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), (IConcept) concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makePresence(IConcept concept, boolean addDefinition) {

        if (concept.is(Type.QUALITY) || concept.is(Type.CONFIGURATION) || concept.is(Type.TRAIT) || concept.is(Type.ROLE)) {
            monitor.error("presence can be observed only for subjects, events, processes and relationships", declaration);
        }

        String cName = getCleanId(concept) + "Presence";
        String definition = UnarySemanticOperator.PRESENCE.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.PRESENCE.name());
            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_PRESENCE, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);

            /*
             * presence is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), (IConcept) concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makeOccurrence(IConcept concept, boolean addDefinition) {

        if (!concept.is(Type.DIRECT_OBSERVABLE)) {
            monitor.error(
                    "occurrences (probability of presence) can be observed only for subjects, events, processes and relationships",
                    declaration);
        }

        String cName = getCleanId(concept) + "Occurrence";
        String definition = UnarySemanticOperator.OCCURRENCE.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);
            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.OCCURRENCE.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_OCCURRENCE, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));

            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);

            /*
             * occurrence is inherent to the event that's possible.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
        }

        return ontology.getConcept(conceptId);
    }

    /**
     * Turn a concept into its observability (a deniable trait) if it's not already one,
     * implementing the corresponding semantic operator. Also makes the original concept the
     * numerosity's inherent.
     * 
     * @param concept the untransformed concept. Must be any observable.
     * @param addDefinition add the {@link NS#CONCEPT_DEFINITION_PROPERTY} annotation; pass true if
     *        used from outside the builder
     * @return the transformed concept
     */
    public Concept makeObservability(IConcept concept, boolean addDefinition) {

        if (!concept.is(Type.OBSERVABLE)) {
            monitor.error("observabilities can only be defined for observables", declaration);
        }

        String cName = getCleanId(concept) + "Observability";
        String definition = UnarySemanticOperator.OBSERVABILITY.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.OBSERVABILITY.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_OBSERVABILITY_TRAIT, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);

            IConcept ret = ontology.getConcept(conceptId);
            /*
             * observability is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), (IConcept) concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makeMagnitude(IConcept concept, boolean addDefinition) {

        if (Kim.intersection(((Concept) concept).getTypeSet(), IKimConcept.CONTINUOUS_QUALITY_TYPES).size() == 0) {
            monitor.error("magnitudes can only be observed only for quantifiable qualities", declaration);
        }

        String cName = getCleanId(concept) + "Magnitude";
        String definition = UnarySemanticOperator.MAGNITUDE.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.MAGNITUDE.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_MAGNITUDE, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);

            /*
             * probability is inherent to the event that's possible.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makeLevel(IConcept concept, boolean addDefinition) {

        if (Kim.intersection(((Concept) concept).getTypeSet(), IKimConcept.CONTINUOUS_QUALITY_TYPES).size() == 0) {
            monitor.error("magnitudes can only be observed only for quantifiable qualities", declaration);
        }

        String cName = getCleanId(concept) + "Level";
        String definition = UnarySemanticOperator.LEVEL.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.LEVEL.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_LEVEL, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);

            /*
             * probability is inherent to the event that's possible.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makeProbability(IConcept concept, boolean addDefinition) {

        if (!concept.is(Type.EVENT)) {
            monitor.error("probabilities can only be observed only for events", declaration);
        }

        String cName = getCleanId(concept) + "Probability";
        String definition = UnarySemanticOperator.PROBABILITY.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.PROBABILITY.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_PROBABILITY, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);

            /*
             * probability is inherent to the event that's possible.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
        }

        return ontology.getConcept(conceptId);
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
    public Concept makeUncertainty(IConcept concept, boolean addDefinition) {

        String cName = "UncertaintyOf" + getCleanId(concept);
        String definition = UnarySemanticOperator.UNCERTAINTY.declaration[0] + " " + concept.getDefinition();
        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);
            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.UNCERTAINTY.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_UNCERTAINTY, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);
            IConcept ret = ontology.getConcept(conceptId);
            /*
             * uncertainty is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), (IConcept) concept, ontology);
        }

        return ontology.getConcept(conceptId);
    }

    public Concept makeProportion(IConcept concept, @Nullable IConcept comparison, boolean addDefinition, boolean isPercentage) {

        if (!(concept.is(Type.QUALITY) || concept.is(Type.TRAIT)) && (comparison != null && !comparison.is(Type.QUALITY))) {
            monitor.error("proportion must be of qualities or traits to qualities", declaration);
        }

        String cName = getCleanId(concept) + (isPercentage ? "Percentage" : "Proportion")
                + (comparison == null ? "" : getCleanId(comparison));

        String definition = (isPercentage
                ? UnarySemanticOperator.PERCENTAGE.declaration[0]
                : UnarySemanticOperator.PROPORTION.declaration[0])
                + " (" + concept.getDefinition() + ")"
                + (comparison == null
                        ? ""
                        : ((isPercentage
                                ? UnarySemanticOperator.PERCENTAGE.declaration[1]
                                : UnarySemanticOperator.PROPORTION.declaration[1]) + " (" + comparison.getDefinition() + ")"));

        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE
                    .getType(isPercentage ? UnarySemanticOperator.PERCENTAGE.name() : UnarySemanticOperator.PROPORTION.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_PROPORTION, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(ax);

            IConcept ret = ontology.getConcept(conceptId);
            /*
             * proportion is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), (IConcept) concept, ontology);
            if (comparison != null) {
                OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_COMPARED_TO_PROPERTY), comparison, ontology);
            }
        }

        return ontology.getConcept(conceptId);
    }

    public Concept makeRatio(IConcept concept, IConcept comparison, boolean addDefinition) {

        /*
         * accept only two qualities of the same physical nature (TODO)
         */
        if (!(concept.is(Type.QUALITY) || concept.is(Type.TRAIT)) || !comparison.is(Type.QUALITY)) {
            monitor.error("ratios must be between qualities of the same nature or traits to qualities", declaration);
        }

        String cName = getCleanId(concept) + "To" + getCleanId(comparison) + "Ratio";

        String definition = UnarySemanticOperator.RATIO.declaration[0] + " (" + concept.getDefinition() + ")"
                + (comparison == null
                        ? ""
                        : (UnarySemanticOperator.RATIO.declaration[1] + " (" + comparison.getDefinition() + ")"));

        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.RATIO.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(NS.CORE_RATIO, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }

            // unit for ratios of physical properties
            if ((concept.is(Type.EXTENSIVE_PROPERTY) || concept.is(Type.INTENSIVE_PROPERTY))
                    && (comparison.is(Type.EXTENSIVE_PROPERTY) || comparison.is(Type.INTENSIVE_PROPERTY))) {
                Object unit1 = Concepts.INSTANCE.getMetadata(concept, NS.SI_UNIT_PROPERTY);
                Object unit2 = Concepts.INSTANCE.getMetadata(comparison, NS.SI_UNIT_PROPERTY);
                if (unit1 != null && unit2 != null) {
                    String unit = unit1 + "/" + unit2;
                    ax.add(Axiom.AnnotationAssertion(conceptId, NS.SI_UNIT_PROPERTY, unit));
                }
            }

            ontology.define(ax);

            IConcept ret = ontology.getConcept(conceptId);

            /*
             * ratio is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_COMPARED_TO_PROPERTY), comparison, ontology);

        }

        return ontology.getConcept(conceptId);
    }

    public Concept makeValue(IConcept concept, IConcept comparison, boolean addDefinition, boolean monetary) {

        String cName = (monetary ? "MonetaryValueOf" : "ValueOf") + getCleanId(concept)
                + (comparison == null ? "" : ("Vs" + getCleanId(comparison)));

        String definition = (monetary
                ? UnarySemanticOperator.MONETARY_VALUE.declaration[0]
                : UnarySemanticOperator.VALUE.declaration[0])
                + " (" + concept.getDefinition() + ")"
                + (comparison == null
                        ? ""
                        : (UnarySemanticOperator.VALUE.declaration[1] + " (" + comparison.getDefinition() + ")"));

        Ontology ontology = (Ontology) concept.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE
                    .getType(monetary ? UnarySemanticOperator.MONETARY_VALUE.name() : UnarySemanticOperator.VALUE.name());

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(conceptId, newType));
            ax.add(Axiom.SubClass(monetary ? NS.CORE_MONETARY_VALUE : NS.CORE_VALUE, conceptId));
            ax.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            ax.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cName));
            if (addDefinition) {
                ax.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            concept.getOntology().define(ax);

            IConcept ret = ontology.getConcept(conceptId);

            /*
             * value is inherent to the thing that's present.
             */
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), concept, ontology);
            if (comparison != null) {
                OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_COMPARED_TO_PROPERTY), comparison, ontology);
            }
        }

        return ontology.getConcept(conceptId);
    }

    /**
     * type of <trait> makes a CLASS that incarnates the trait as a quality and whose values are the
     * concrete children of the trait.
     * 
     * @param classified
     * @param addDefinition
     * @return
     */
    public Concept makeType(IConcept classified, boolean addDefinition) {

        String traitID = getCleanId(classified) + "Type";
        String definition = UnarySemanticOperator.TYPE.declaration[0] + " " + classified.getDefinition();
        Ontology ontology = (Ontology) classified.getOntology();
        String conceptId = ontology.getIdForDefinition(definition);

        if (conceptId == null) {

            conceptId = ontology.createIdForDefinition(definition);

            EnumSet<Type> newType = Kim.INSTANCE.getType(UnarySemanticOperator.TYPE.name());

            List<IAxiom> axioms = new ArrayList<>();
            axioms.add(Axiom.ClassAssertion(conceptId, newType));
            axioms.add(Axiom.SubClass(NS.CORE_TYPE, conceptId));
            axioms.add(Axiom.AnnotationAssertion(conceptId, NS.BASE_DECLARATION, "true"));
            axioms.add(Axiom.AnnotationAssertion(conceptId, NS.IS_TYPE_DELEGATE, "true"));
            axioms.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", traitID));
            if (addDefinition) {
                axioms.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, definition));
            }
            ontology.define(axioms);
            IConcept ret = ontology.getConcept(conceptId);

            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.DESCRIBES_OBSERVABLE_PROPERTY), classified, ontology);

            /*
             * types inherit the context from their trait
             */
            IConcept context = Observables.INSTANCE.getContextType(classified);
            if (context != null) {
                OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CONTEXT_PROPERTY), context, ontology);
            }
        }

        return ontology.getConcept(conceptId);

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

    @Override
    public Concept buildConcept() throws KlabValidationException {

        if (errors.size() > 0) {

            // build anyway but leave errors for notification

            String message = "";
            for (KlabValidationException error : errors) {
                message += (message.isEmpty() ? "" : "\n") + error.getLocalizedMessage();
            }
            monitor.error(message, declaration);
        }

        if (!resolveMain()) {
            return null;
        }

        /*
         * correctly support trival case so we can use this without checking.
         */
        if (isTrivial()) {
            return main;
        }

        this.ontology = getTargetOntology();

        /*
         * retrieve the ID for the declaration; if present, just return the corresponding concept
         */
        String conceptId = this.ontology.getIdForDefinition(declaration.getDefinition());
        if (conceptId != null && this.ontology.getConcept(conceptId) != null) {
            return this.ontology.getConcept(conceptId);
        }

        // System.out.println("building " + declaration + " in " + ontology);

        conceptId = this.ontology.createIdForDefinition(declaration.getDefinition());

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

        /*
         * preload any base traits we already have. If any of them is abstract, take notice so we
         * can see if they are all concretized later.
         */
        for (IConcept c : Traits.INSTANCE.getTraits(main)) {
            IConcept base = Traits.INSTANCE.getBaseParentTrait(c);
            baseTraits.add(base);
            if (c.isAbstract()) {
                abstractTraitBases.add(base);
            }
        }

        /*
         * name and display label for the finished concept. NOTE: since 0.10.0 these are no longer
         * guaranteed unique. The authoritative name is the ontology-attributed ID.
         */
        String cId = "";
        String cDs = "";

        if (traits != null && traits.size() > 0) {

            for (IConcept t : traits) {

                if (t.equals(main)) {
                    continue;
                }

                if (Traits.INSTANCE.getTraits(main).contains(t)) {
                    continue;
                    // monitor.error("concept " + Concepts.INSTANCE.getDisplayName(main) + " already
                    // adopts trait "
                    // + Concepts.INSTANCE.getDisplayName(t), declaration);
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
                    monitor.error("base declaration for trait " + t + " could not be found", declaration);
                }

                if (!baseTraits.add(base)) {
                    monitor.error(
                            "cannot add trait " + Concepts.INSTANCE.getDisplayName(t) + " to concept " + main
                                    + " as it already adopts a trait of type " + Concepts.INSTANCE.getDisplayName(base),
                            declaration);
                }

                if (t.isAbstract()) {
                    abstractTraitBases.add(base);
                } else {
                    abstractTraitBases.remove(base);
                }

                tids.add(getCleanId(t));

            }
        }

        /*
         * FIXME using the display name to build an ID is wrong and forces us to use display names
         * that are legal for concept names. The two should work independently.
         */
        if (tids.size() > 0) {
            Collections.sort(tids);
            for (String s : tids) {
                cId += s;
                cDs += s;
                // uId += s;
            }
        }

        /*
         * add the main identity to the ID after all traits and before any context
         */
        String cleanId = getCleanId(main);
        cId += cleanId;
        cDs += cleanId;
        // uId += cleanId;

        /*
         * handle context, inherency etc.
         */
        if (inherent != null) {
            IConcept other = Observables.INSTANCE.getInherentType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(inherent, other)) {
                monitor.error("cannot set the inherent type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(inherent) + " as it already has an incompatible inherency: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(inherent);
            cId += (distributedInherency ? "OfEach" : "Of") + cleanId;
            cDs += (distributedInherency ? "OfEach" : "Of") + cleanId;
        }

        if (context != null) {
            IConcept other = Observables.INSTANCE.getContextType(main);
            // use the version of isCompatible that allows for observations that are
            // compatible with
            // the context's context if the context is an occurrent (e.g. Precipitation of
            // Storm)
            if (other != null && !Observables.INSTANCE.isContextuallyCompatible(main, context, other)) {
                monitor.error("cannot set the context type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(context) + " as it already has an incompatible context: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(context);
            cId += "In" + cleanId;
            cDs += "In" + cleanId;
            // uId += "In" + cleanId;
        }

        if (compresent != null) {
            IConcept other = Observables.INSTANCE.getCompresentType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(compresent, other)) {
                monitor.error("cannot set the compresent type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(context) + " as it already has an incompatible compresent type: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(compresent);
            cId += "With" + cleanId;
            cDs += "With" + cleanId;
        }

        if (goal != null) {
            // TODO transform as necessary
            IConcept other = Observables.INSTANCE.getGoalType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(goal, other)) {
                monitor.error("cannot set the goal type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(goal) + " as it already has an incompatible goal type: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(goal);
            cId += "For" + cleanId;
            cDs += "For" + cleanId;
        }

        if (caused != null) {
            IConcept other = Observables.INSTANCE.getCausedType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(caused, other)) {
                monitor.error("cannot set the caused type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(caused) + " as it already has an incompatible caused type: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(caused);
            cId += "To" + cleanId;
            cDs += "To" + cleanId;
        }

        if (causant != null) {
            IConcept other = Observables.INSTANCE.getCausantType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(causant, other)) {
                monitor.error("cannot set the causant type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(causant) + " as it already has an incompatible causant type: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(causant);
            cId += "From" + cleanId;
            cDs += "From" + cleanId;
        }

        if (adjacent != null) {
            IConcept other = Observables.INSTANCE.getAdjacentType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(adjacent, other)) {
                monitor.error(
                        "cannot set the adjacent type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                                + Concepts.INSTANCE.getDisplayName(adjacent)
                                + " as it already has an incompatible adjacent type: " + Concepts.INSTANCE.getDisplayName(other),
                        declaration);
            }
            cleanId = getCleanId(adjacent);
            cId += "AdjacentTo" + cleanId;
            cDs += "AdjacentTo" + cleanId;
        }

        if (cooccurrent != null) {
            IConcept other = Observables.INSTANCE.getCooccurrentType(main);
            if (other != null && !Observables.INSTANCE.isCompatible(cooccurrent, other)) {
                monitor.error("cannot set the co-occurrent type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                        + Concepts.INSTANCE.getDisplayName(cooccurrent) + " as it already has an incompatible co-occurrent type: "
                        + Concepts.INSTANCE.getDisplayName(other), declaration);
            }
            cleanId = getCleanId(cooccurrent);
            cId += "During" + cleanId;
            cDs += "During" + cleanId;
        }

        if (relationshipSource != null) {
            IConcept other = Observables.INSTANCE.getRelationshipSource(main);
            if (other != null && !Observables.INSTANCE.isCompatible(relationshipSource, other)) {
                monitor.error(
                        "cannot set the relationship source type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                                + Concepts.INSTANCE.getDisplayName(relationshipSource)
                                + " as it already has an incompatible source type: " + Concepts.INSTANCE.getDisplayName(other),
                        declaration);
            }
            IConcept other2 = Observables.INSTANCE.getRelationshipTarget(main);
            if (other2 != null && !Observables.INSTANCE.isCompatible(relationshipTarget, other2)) {
                monitor.error(
                        "cannot set the relationship target type of " + Concepts.INSTANCE.getDisplayName(main) + " to "
                                + Concepts.INSTANCE.getDisplayName(relationshipTarget)
                                + " as it already has an incompatible target type: " + Concepts.INSTANCE.getDisplayName(other2),
                        declaration);
            }
            cleanId = getCleanId(relationshipSource);
            cId += "Linking" + cleanId;
            cDs += "Linking" + cleanId;
            String cid2 = getCleanId(relationshipTarget);
            cId += "To" + cid2;
            cDs += "To" + cid2;
        }

        String roleIds = "";
        List<String> rids = new ArrayList<>();
        Set<IConcept> acceptedRoles = new HashSet<>();

        if (roles != null && roles.size() > 0) {
            for (IConcept role : roles) {
                if (Roles.INSTANCE.getRoles(main).contains(role)) {
                    monitor.error("concept " + Concepts.INSTANCE.getDisplayName(main) + " already has role "
                            + Concepts.INSTANCE.getDisplayName(role), declaration);
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
            // if (!rolesAreFundamental(roles)) {
            cDs = roleIds + Concepts.INSTANCE.getDisplayName(main);
            // }
        }

        // if (distributedInherency) {
        // // TODO revise - this must have proper declaration etc.
        // // distinguish the label to avoid conflicts; semantically we are the same, so
        // // the display label remains unchanged.
        // cId += "Classifier";
        // }

        List<IAxiom> axioms = new ArrayList<>();
        axioms.add(Axiom.ClassAssertion(conceptId, type));
        axioms.add(Axiom.AnnotationAssertion(conceptId, NS.DISPLAY_LABEL_PROPERTY, cDs));
        axioms.add(Axiom.AnnotationAssertion(conceptId, "rdfs:label", cId));
        axioms.add(Axiom.SubClass(main.getUrn(), conceptId));
        if (distributedInherency) {
            axioms.add(Axiom.AnnotationAssertion(conceptId, NS.INHERENCY_IS_DISTRIBUTED, "true"));
        }

        /*
         * add the core observable concept ID using NS.CORE_OBSERVABLE_PROPERTY
         */
        axioms.add(Axiom.AnnotationAssertion(conceptId, NS.CORE_OBSERVABLE_PROPERTY, main.toString()));
        axioms.add(Axiom.AnnotationAssertion(conceptId, NS.CONCEPT_DEFINITION_PROPERTY, declaration.getDefinition()));

        if (type.contains(Type.ABSTRACT)) {
            axioms.add(Axiom.AnnotationAssertion(conceptId, NS.IS_ABSTRACT, "true"));
        }

        ontology.define(axioms);
        ret = ontology.getConcept(conceptId);

        this.axiomsAdded = true;

        /*
         * restrictions
         */

        if (identities.size() > 0) {
            Traits.INSTANCE.restrict(ret, Concepts.p(NS.HAS_IDENTITY_PROPERTY), LogicalConnector.UNION, identities, ontology);
        }
        if (realms.size() > 0) {
            Traits.INSTANCE.restrict(ret, Concepts.p(NS.HAS_REALM_PROPERTY), LogicalConnector.UNION, realms, ontology);
        }
        if (attributes.size() > 0) {
            Traits.INSTANCE.restrict(ret, Concepts.p(NS.HAS_ATTRIBUTE_PROPERTY), LogicalConnector.UNION, attributes, ontology);
        }
        if (acceptedRoles.size() > 0) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_ROLE_PROPERTY), LogicalConnector.UNION, acceptedRoles, ontology);
        }
        if (inherent != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_INHERENT_TO_PROPERTY), inherent, ontology);
        }
        if (context != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CONTEXT_PROPERTY), context, ontology);
        }
        if (caused != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CAUSED_PROPERTY), caused, ontology);
        }
        if (causant != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_CAUSANT_PROPERTY), causant, ontology);
        }
        if (compresent != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_COMPRESENT_PROPERTY), compresent, ontology);
        }
        if (goal != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.HAS_PURPOSE_PROPERTY), goal, ontology);
        }
        if (cooccurrent != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.OCCURS_DURING_PROPERTY), cooccurrent, ontology);
        }
        if (adjacent != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IS_ADJACENT_TO_PROPERTY), adjacent, ontology);
        }
        if (relationshipSource != null) {
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IMPLIES_SOURCE_PROPERTY), relationshipSource, ontology);
            OWL.INSTANCE.restrictSome(ret, Concepts.p(NS.IMPLIES_DESTINATION_PROPERTY), relationshipTarget, ontology);
        }

        if (monitor != null && !Reasoner.INSTANCE.isSatisfiable(ret)) {
            monitor.error("this declaration has logical errors and is inconsistent", declaration);
        }

        return ret;
    }

    private Ontology getTargetOntology() {
        return Ontologies.INSTANCE.getTargetOntology(ontology, main, traits, roles, inherent, context, caused, causant,
                compresent, goal, cooccurrent, adjacent);
    }

    public static String getCleanId(IConcept main) {
        String id = main.getMetadata().get(IMetadata.DC_LABEL, String.class);
        if (id == null) {
            id = main.getName();
        }
        return id;
    }

    // private static boolean rolesAreFundamental(Collection<IConcept> roles) {
    // for (IConcept c : roles) {
    // if (Resources.INSTANCE.getWorldview() != null
    // && !c.getNamespace().equals(Resources.INSTANCE.getWorldview().getName())) {
    // return false;
    // }
    // }
    // return true;
    // }

    private boolean isTrivial() {
        return isTrivial;
    }

    public Concept getMainConcept() {
        return main;
    }

    @Override
    public Collection<IConcept> getRemoved() {
        return removed;
    }

    @Override
    public Observable buildObservable() throws KlabValidationException {

        IConcept obs = buildConcept();

        if (obs == null) {
            return null;
        }

        Observable ret = Observable.promote(obs);

        if (currency != null) {
            ret.setCurrency((Currency) currency);
            ret.setDeclaration(ret.getDeclaration() + " in " + ret.getCurrency());
        } else if (unit != null) {
            ret.setUnit((Unit) unit);
            ret.setDeclaration(ret.getDeclaration() + " in " + ret.getUnit());
        }

        for (Pair<ValueOperator, Object> op : valueOperators) {

            ValueOperator valueOperator = op.getFirst();
            Object valueOperand = op.getSecond();

            ret.setDeclaration(ret.getDeclaration() + " " + valueOperator.declaration);
            if (name == null) {
                ret.setName(ret.getName() + "_" + valueOperator.textForm);
            }

            if (valueOperand instanceof IConcept) {

                ret.setDeclaration(ret.getDeclaration() + " " + ((IConcept) valueOperand).getDefinition());
                if (name == null) {
                    ret.setName(ret.getName() + "_" + Concepts.INSTANCE.getDisplayName((Concept) valueOperand)
                            .replaceAll("\\-", "_").replaceAll(" ", "_"));
                }
            } else if (valueOperand instanceof IObservable) {

                ret.setDeclaration(ret.getDeclaration() + " (" + ((Observable) valueOperand).getDeclaration() + ")");
                if (name == null) {
                    ret.setName(ret.getName() + "_" + Observables.INSTANCE.getDisplayName((Observable) valueOperand));
                }
            } else {

                if (valueOperand != null) {
                    ret.setDeclaration(ret.getDeclaration() + " " + valueOperand);
                    if (name == null) {
                        ret.setName(ret.getName() + "_" + valueOperand);
                    }
                }
            }

            ret.getValueOperators().add(new Pair<>(valueOperator, valueOperand));

        }

        if (name != null) {
            ret.setName(name);
        }

        ret.setStatedName(this.statedName);
        ret.setTargetPredicate(targetPredicate);
        ret.setOptional(this.optional);
        ret.setMustContextualizeAtResolution(mustContextualize);
        ret.getAnnotations().addAll(annotations);
        ret.setDistributedInherency(distributedInherency);
        ret.setTemporalInherent(temporalInherent);
        ret.setDereifiedAttribute(this.dereifiedAttribute);

        return ret;
    }

    @Override
    public Builder withUnit(IUnit unit) {
        this.unit = unit;
        return this;
    }

    @Override
    public Builder withCurrency(ICurrency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public Builder named(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Builder withDistributedInherency(boolean b) {
        this.distributedInherency = b;
        return this;
    }

    @Override
    public Builder withValueOperator(ValueOperator operator, Object operand) {
        this.valueOperators.add(new Pair<>(operator, operand));
        return this;
    }

    @Override
    public Builder withoutValueOperators() {
        this.valueOperators.clear();
        return this;
    }

    @Override
    public Builder withTargetPredicate(IConcept targetPredicate) {
        this.targetPredicate = targetPredicate;
        return this;
    }

    @Override
    public Builder withDereifiedAttribute(String dereifiedAttribute) {
        this.dereifiedAttribute = dereifiedAttribute;
        return this;
    }

    @Override
    public boolean axiomsAdded() {
        return this.axiomsAdded;
    }

}
