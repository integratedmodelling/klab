package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.SemanticOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.services.IObservableService.Builder;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ObservableBuilder implements Builder {

    private IConcept                      main;
    private IConcept                      parent;
    private String                        mainId;
    private Set<Type>                     type;

    private IConcept                      inherent;
    private IConcept                      context;
    private IConcept                      compresent;
    private IConcept                      causant;
    private IConcept                      caused;
    private IConcept                      goal;
    private IConcept                      classifier;
    private IConcept                      downTo;

    private List<IConcept>                traits         = new ArrayList<>();
    private List<IConcept>                roles          = new ArrayList<>();
    private List<KlabValidationException> errors         = new ArrayList<>();

    public ObservableBuilder(IConcept main) {
        this.main = main;
        this.type = ((Concept) main).type;
    }

    public ObservableBuilder(String main, IConcept parent) {
        this.mainId = main;
        this.parent = parent;
        this.type = ((Concept) parent).type;
    }

    public ObservableBuilder(String main, Set<Type> parent) {
        this.mainId = main;
        this.parent = Workspaces.INSTANCE.getUpperOntology().getCoreType(parent);
        this.type = parent;
    }

    @Override
    public Builder of(IConcept concept) {
        this.inherent = concept;
        return this;
    }

    @Override
    public Builder within(IConcept concept) {
        this.context = concept;
        return this;
    }

    @Override
    public Builder to(IConcept concept) {
        this.caused = concept;
        return this;
    }

    @Override
    public Builder from(IConcept concept) {
        this.causant = concept;
        return this;
    }

    @Override
    public IConcept build() {
        return null;
    }

    @Override
    public Builder with(IConcept concept) {
        this.compresent = concept;
        return this;
    }

    @Override
    public Builder as(IConcept concept) {
        if (!concept.is(Type.ROLE)) {
            errors.add(new KlabValidationException("cannot use concept " + concept + " as a role"));
        }
        this.roles.add(concept);
        return this;
    }

    @Override
    public Builder downTo(IConcept concept) {
        this.downTo = concept;
        return this;
    }

    @Override
    public Builder by(IConcept concept) {
        // must be a trait and must be unique
        this.classifier = concept;
        return this;
    }

    @Override
    public IConcept build(IOntology ontology) throws KlabValidationException {
        if (errors.size() > 0) {
            throw errors.get(0);
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Builder withGoal(IConcept goal) {
        this.goal = goal;
        return this;
    }

    @Override
    public Builder contextualizedTo(IConcept context) {
        // TODO Auto-generated method stub
        return this;
    }
    
    @Override
    public Builder as(SemanticOperator type, IConcept... participants) {
        
        switch (type) {
        case ASSESSMENT:
            this.main = makeAssessment(this.main);
            break;
        case COUNT:
            break;
        case DISTANCE:
            break;
        case OCCURRENCE:
            break;
        case PRESENCE:
            break;
        case PROBABILITY:
            break;
        case PROPORTION:
            break;
        case RATIO:
            break;
        case UNCERTAINTY:
            break;
        case VALUE:
            break;
        default:
            break;
        }
        
        return this;
    }

    @Override
    public Collection<KlabValidationException> getErrors() {
        return errors;
    }

    @Override
    public Builder without(Collection<IConcept> concepts) {
        // TODO perform the extraction right here
        return this;
    }

    @Override
    public Builder without(IConcept... concepts) {
        // TODO perform the extraction right here
        return this;
    }

    @Override
    public Builder withTrait(IConcept... concepts) {
        for (IConcept concept : concepts) {
            if (!concept.is(Type.TRAIT)) {
                errors.add(new KlabValidationException("cannot use concept " + concept + " as a role"));
            } else {
                traits.add(concept);
            }
        }
        return this;
    }

    @Override
    public Builder withTrait(Collection<IConcept> concepts) {
        for (IConcept concept : concepts) {
            if (!concept.is(Type.TRAIT)) {
                errors.add(new KlabValidationException("cannot use concept " + concept + " as a role"));
            } else {
                traits.add(concept);
            }
        }
        return this;
    }
    
    private static IConcept makeAssessment(IConcept concept) {

        String cName = cleanInternalId(concept.getLocalName()) + "Assessment";

        if (!concept.is(Type.QUALITY)) {
            return null;
        }
        
        /*
         * TODO make new type - remove all qualities and make it a process
         */
        EnumSet<Type> newType = EnumSet.copyOf(((Concept)concept).type);

        /*
         * make a ConceptAssessment if not there, and ensure it's a continuously
         * quantifiable quality. Must be in same ontology as the original concept.
         */
        IConcept ret = concept.getOntology().getConcept(cName);

        if (ret == null) {

            ArrayList<IAxiom> ax = new ArrayList<>();
            ax.add(Axiom.ClassAssertion(cName, newType));
            ax.add(Axiom.SubClass(NS.CORE_ASSESSMENT, cName));
//            ax.add(Axiom.AnnotationAssertion(cName, NS.CONCEPT_DEFINITION_PROPERTY, Qualities.ASSESSMENT
//                    .name().toLowerCase() + " " + ((Concept) concept).getAssertedDefinition()));
            ax.add(Axiom.AnnotationAssertion(cName, NS.BASE_DECLARATION, "true"));
            concept.getOntology().define(ax);
            ret = concept.getOntology().getConcept(cName);
            OWL.INSTANCE.restrictSome(ret, Concepts.p(CoreOntology.NS.OBSERVES_PROPERTY), concept);
        }

        return ret;

    }
    
    // just remove the starting 'i' if any.
    private static String cleanInternalId(String s) {
        return s.startsWith("i") ? s.substring(1) : s;
    }



}
