//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IProperty;
//import org.semanticweb.owlapi.model.OWLClass;
//import org.semanticweb.owlapi.model.OWLClassExpression;
//import org.semanticweb.owlapi.model.OWLEntity;
//import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
//import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
//import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
//import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
//import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
//import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
//import org.semanticweb.owlapi.model.OWLOntology;
//import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
//import org.semanticweb.owlapi.util.OWLClassExpressionCollector;
//import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;
//
///**
// * Visit the hierarchy to collect only the topmost object restriction on the passed
// * property and return its filler as a IConcept. Recurse the asserted hierarchy when
// * looking for parents. If the filler is a union, return the flattened set of
// * concepts.
// * 
// * @author ferdinando.villa
// */
//public class ShallowRestrictionVisitor
//        extends OWLClassExpressionCollector {
//
//    private Set<OWLOntology>     onts;
//    private Set<OWLClass>        processedClasses = new HashSet<>();
//    private boolean              done             = false;
//    private OWLEntity            property;
//    private Collection<IConcept> result           = null;
//
//    public Collection<IConcept> getResult() {
//        return result == null ? new HashSet<>() : result;
//    }
//
//    public ShallowRestrictionVisitor(IConcept concept, IProperty property) {
//        this.onts = OWL.INSTANCE.manager.getOntologies();
//        this.property = ((Property) property)._owl;
//        ((Concept) concept)._owl.accept(this);
//    }
//
//    @Override
//    public void visit(OWLClass desc) {
//
//        if (!done && !processedClasses.contains(desc)) {
//
//            processedClasses.add(desc);
//
//            Set<OWLClassExpression> set = desc.getSuperClasses(onts);
//            for (OWLClassExpression s : set) {
//                if (s.equals(desc)) {
//                    break;
//                } else if (!done && !(s.isAnonymous() || s.asOWLClass().isBuiltIn()))
//                    s.asOWLClass().accept(this);
//            }
//        }
//    }
//
//    private void visitRestriction(OWLQuantifiedRestriction<?, ?, ? extends OWLClassExpression> desc) {
//        if (desc.getProperty().equals(property)) {
//            done = true;
//            result = OWL.INSTANCE.unwrap(desc.getFiller());
//        }
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
//
//}