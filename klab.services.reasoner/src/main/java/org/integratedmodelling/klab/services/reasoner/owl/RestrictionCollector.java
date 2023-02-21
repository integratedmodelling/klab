//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.semanticweb.owlapi.model.OWLClass;
//import org.semanticweb.owlapi.model.OWLClassExpression;
//import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
//import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
//import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
//import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
//import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
//import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
//import org.semanticweb.owlapi.model.OWLOntology;
//import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
//import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;
//
///**
// * Visit the hierarchy to collect all restrictions.
// * 
// * @author ferdinando.villa
// */
//public class RestrictionCollector
//        extends OWLClassExpressionVisitorAdapter {
//
//    private Set<OWLOntology>                                                  onts;
//    private Set<OWLClass>                                                     processedClasses = new HashSet<>();
//    private Set<OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression>> restrictions     = new HashSet<>();
//    private OWLClass                                                          filler;
//
//    public Collection<OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression>> getRestrictions() {
//        return this.restrictions;
//    }
//
//    public RestrictionCollector(IConcept concept) {
//        this.onts = OWL.INSTANCE.manager.getOntologies();
//        ((Concept) concept)._owl.accept(this);
//    }
//
//    @Override
//    public void visit(OWLClass desc) {
//
//        if (!processedClasses.contains(desc)) {
//            processedClasses.add(desc);
//
//            Set<OWLClassExpression> set = desc.getSuperClasses(onts);
//            for (OWLClassExpression s : set) {
//                if (!s.equals(desc)) {
//                    // break;
//                    // } else {
//                    s.accept(this);
//                }
//            }
//        }
//    }
//
//    private void visitRestriction(OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression> desc) {
//        // if (desc.getFiller().equals(this.filler)) {
//        this.restrictions.add(desc);
//        // }
//    }
//
//    @Override
//    public void visit(OWLObjectAllValuesFrom desc) {
//        visitRestriction(desc);
//    }
//
//    @Override
//    public void visit(OWLObjectExactCardinality desc) {
//        visitRestriction(desc);
//    }
//
//    @Override
//    public void visit(OWLObjectMaxCardinality desc) {
//        visitRestriction(desc);
//    }
//
//    @Override
//    public void visit(OWLObjectMinCardinality desc) {
//        visitRestriction(desc);
//    }
//
//    public void visit(OWLObjectCardinalityRestriction desc) {
//        visitRestriction(desc);
//    }
//
//    @Override
//    public void visit(OWLObjectSomeValuesFrom desc) {
//        visitRestriction(desc);
//    }
//}
