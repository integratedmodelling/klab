package org.integratedmodelling.klab.services.reasoner.owl;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

/**
 * Visit the hierarchy to find the restriction that explicitly uses the
 * passed concept as a filler.
 * 
 * @author ferdinando.villa
 */
public class ConceptRestrictionVisitor
        extends OWLClassExpressionVisitorAdapter {

    private Set<OWLOntology>                                             onts;
    private Set<OWLClass>                                                processedClasses = new HashSet<>();
    private OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression> restriction;
    private OWLClass                                                     filler;

    public OWLQuantifiedRestriction<?, ?, ?> getRestriction() {
        return this.restriction;
    }

    public ConceptRestrictionVisitor(KConcept concept, KConcept filler) {
        this.onts = OWL.INSTANCE.manager.getOntologies();
        this.filler = OWL.INSTANCE.getOWLClass(filler);
        OWL.INSTANCE.getOWLClass(concept).accept(this);
    }

    @Override
    public void visit(OWLClass desc) {

        if (!processedClasses.contains(desc)) {
            processedClasses.add(desc);

            Set<OWLClassExpression> set = desc.getSuperClasses(onts);
            for (OWLClassExpression s : set) {
                if (s.equals(desc)) {
                    break;
                } else {
                    s.accept(this);
                }
            }
        }
    }

    private void visitRestriction(OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression> desc) {
        if (desc.getFiller().equals(this.filler)) {
            this.restriction = desc;
        }
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        visitRestriction(desc);
    }

    @Override
    public void visit(OWLObjectExactCardinality desc) {
        visitRestriction(desc);
    }

    @Override
    public void visit(OWLObjectMaxCardinality desc) {
        visitRestriction(desc);
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
        visitRestriction(desc);
    }

    public void visit(OWLObjectCardinalityRestriction desc) {
        visitRestriction(desc);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        visitRestriction(desc);
    }

    public boolean isDenied() {
        if (this.restriction != null) {
            if (this.restriction instanceof OWLObjectExactCardinality) {
                return ((OWLObjectExactCardinality) this.restriction)
                        .getCardinality() == 0;
            } else if (this.restriction instanceof OWLObjectMaxCardinality) {
                return ((OWLObjectMaxCardinality) this.restriction).getCardinality() == 0;
            }
        }
        return false;
    }

    public boolean isOptional() {
        if (this.restriction instanceof OWLObjectMinCardinality) {
            return ((OWLObjectMinCardinality) this.restriction).getCardinality() == 0;
        }
        return !(this.restriction instanceof OWLObjectCardinalityRestriction);
    }

}
