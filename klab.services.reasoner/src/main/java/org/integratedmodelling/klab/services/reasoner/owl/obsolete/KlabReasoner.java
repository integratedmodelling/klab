package org.integratedmodelling.klab.services.reasoner.owl.obsolete;
//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.Namespaces;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IKnowledge;
//import org.integratedmodelling.klab.api.knowledge.IOntology;
//import org.integratedmodelling.klab.api.knowledge.IProperty;
//import org.integratedmodelling.klab.api.knowledge.ISemantic;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.model.Namespace;
//import org.semanticweb.HermiT.Reasoner;
//import org.semanticweb.owlapi.model.AxiomType;
//import org.semanticweb.owlapi.model.OWLAxiom;
//import org.semanticweb.owlapi.model.OWLClass;
//import org.semanticweb.owlapi.model.OWLClassExpression;
//import org.semanticweb.owlapi.model.OWLDataProperty;
//import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
//import org.semanticweb.owlapi.model.OWLLiteral;
//import org.semanticweb.owlapi.model.OWLNamedIndividual;
//import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
//import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
//import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
//import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
//import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
//import org.semanticweb.owlapi.reasoner.InferenceType;
//import org.semanticweb.owlapi.reasoner.Node;
//import org.semanticweb.owlapi.reasoner.NodeSet;
//import org.semanticweb.owlapi.reasoner.OWLReasoner;
//import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
//import org.semanticweb.owlapi.reasoner.TimeOutException;
//import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
//import org.semanticweb.owlapi.util.Version;
//
///**
// * Overall reasoner. Only thread safe when the knowledge does not change.
// * 
// * @author ferdinando.villa
// *
// */
//public class KlabReasoner {
//
//    boolean     on;
//    boolean     synchronizing = false;
//    Ontology    overall;
//    OWLReasoner reasoner;
//    
//    private static String ONTOLOGY_ID = "k";
//
//    public synchronized void addOntology(IOntology o) {
//        if (overall != null) {
//            overall.addImport(o);
//            if (reasoner != null && synchronizing) {
//                reasoner.flush();
//            }
//        }
//    }
//
//    public boolean isOn() {
//        return on;
//    }
//
//    public KlabReasoner(OWL owl, IMonitor monitor) {
//        overall = (Ontology) owl.requireOntology(ONTOLOGY_ID, OWL.INTERNAL_ONTOLOGY_PREFIX);
//        overall.setInternal(true);
//        if (Namespaces.INSTANCE.getNamespace(ONTOLOGY_ID) == null) {
//            Namespaces.INSTANCE.registerNamespace(new Namespace(ONTOLOGY_ID, null, overall), monitor);
//        }
//        if (Configuration.INSTANCE.useReasoner()) {
//            this.reasoner = new Reasoner.ReasonerFactory()
//                    .createReasoner(overall.getOWLOntology());
//            on = true;
//        }
//    }
//
//    public IOntology getOntology() {
//        return overall;
//    }
//    
//    /**
//     * {@link IKnowledge#is(ISemantic)} will only
//     * check for direct subsumption. This one defaults to that when the reasoner is not
//     * active.
//     * 
//     * Annotation properties only use asserted methods.
//     * 
//     * TODO handle exceptions and/or default to is() when the ontology is inconsistent and
//     * we're not running in strict mode.
//     * 
//     * @param c1
//     * @param c2
//     * @return true
//     */
//    public boolean is(IKnowledge c1, IKnowledge c2) {
//
//        if (c1 instanceof IConcept && c2 instanceof IConcept) {
//
//            if (reasoner == null) {
//                return c1.is(c2);
//            }
//            return getSubClasses(((Concept) c2.getType()).getOWLClass(), false)
//                    .containsEntity(((Concept) c1.getType()).getOWLClass().asOWLClass());
//
//        } else if (c1 instanceof IProperty && c2 instanceof IProperty) {
//
//            if (reasoner == null || (((IProperty) c1).isAnnotation()
//                    && ((IProperty) c2).isAnnotation())) {
//                return ((IProperty) c1).is(c2);
//            }
//
//            if (((IProperty) c1).isObjectProperty()
//                    && ((IProperty) c2).isObjectProperty()) {
//                return getSubObjectProperties(((Property) c2).getOWLEntity()
//                        .asOWLObjectProperty(), false)
//                                .containsEntity(((Property) c1).getOWLEntity()
//                                        .asOWLObjectProperty());
//            } else if (((IProperty) c1).isLiteralProperty()
//                    && ((IProperty) c2).isLiteralProperty()) {
//                return getSubDataProperties(((Property) c2).getOWLEntity()
//                        .asOWLDataProperty(), false)
//                                .containsEntity(((Property) c1).getOWLEntity()
//                                        .asOWLDataProperty());
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Return if concept is consistent. If reasoner is off, assume true.
//     * 
//     * @param c
//     * @return true if concept is consistent.
//     */
//    public boolean isSatisfiable(IConcept c) {
//        return reasoner == null ? true : isSatisfiable(((Concept) c.getType()).getOWLClass());
//    }
//
//    /**
//     * Synonym of getAllParents(), in IConcept; defaults to asserted parents if reasoner
//     * is off. Will not return owl:Thing (which should never appear anyway) or
//     * owl:Nothing, even if the concept is inconsistent.
//     * 
//     * @param main
//     * @return the parent closure of the concept
//     */
//    public Set<IConcept> getParentClosure(IConcept main) {
//        Set<IConcept> ret = new HashSet<>();
//        if (reasoner != null) {
//            for (OWLClass cls : getSuperClasses(((Concept) main.getType()).getOWLClass(), false)
//                    .getFlattened()) {
//                if (cls.isBottomEntity() || cls.isTopEntity()) {
//                    continue;
//                }
//                IConcept cc = OWL.INSTANCE.getConceptFor(cls, false);
//                if (cc != null) {
//                    ret.add(cc);
//                }
//            }
//        } else {
//            ret.addAll(main.getAllParents());
//        }
//        return ret;
//
//    }
//
//    /**
//     * Synonym of getSubclasses, in IConcepts; defaults to asserted children if reasoner
//     * is off. Will not return owl:Thing (which should never appear anyway) or
//     * owl:Nothing, which is a child of everything.
//     * 
//     * @param main
//     * @return the semantic closure of the concept
//     */
//    public Set<IConcept> getSemanticClosure(IConcept main) {
//        if (reasoner != null) {
//            Set<IConcept> ret = new HashSet<>();
//            for (OWLClass cls : getSubClasses(((Concept) main.getType()).getOWLClass(), false)
//                    .getFlattened()) {
//                if (cls.isBottomEntity() || cls.isTopEntity()) {
//                    continue;
//                }
//                IConcept cc = OWL.INSTANCE.getConceptFor(cls, false);
//                if (cc != null) {
//                    ret.add(cc);
//                }
//            }
//            return ret;
//        }
//        return main.getSemanticClosure();
//    }
//
//    /*
//     * Delegate methods. TODO align with k.LAB API instead of OWLAPI.
//     */
//
//    public void flush() {
//        if (reasoner != null) {
//            reasoner.flush();
//        }
//    }
//
//    public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getDataPropertyDomains(arg0, arg1);
//    }
//
//    public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual arg0, OWLDataProperty arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getDataPropertyValues(arg0, arg1);
//    }
//
//    public NodeSet<OWLNamedIndividual> getDifferentIndividuals(OWLNamedIndividual arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getDifferentIndividuals(arg0);
//    }
//
//    public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression arg0)
//            throws ReasonerInterruptedException,
//            TimeOutException, FreshEntitiesException, InconsistentOntologyException {
//        return reasoner.getDisjointClasses(arg0);
//    }
//
//    public NodeSet<OWLDataProperty> getDisjointDataProperties(OWLDataPropertyExpression arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getDisjointDataProperties(arg0);
//    }
//
//    public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(OWLObjectPropertyExpression arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getDisjointObjectProperties(arg0);
//    }
//
//    public Node<OWLClass> getEquivalentClasses(OWLClassExpression arg0)
//            throws InconsistentOntologyException, ClassExpressionNotInProfileException,
//            FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
//        return reasoner.getEquivalentClasses(arg0);
//    }
//
//    public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getEquivalentDataProperties(arg0);
//    }
//
//    public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(OWLObjectPropertyExpression arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getEquivalentObjectProperties(arg0);
//    }
//
//    public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression arg0, boolean arg1)
//            throws InconsistentOntologyException, ClassExpressionNotInProfileException,
//            FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
//        return reasoner.getInstances(arg0, arg1);
//    }
//
//    public Node<OWLObjectPropertyExpression> getInverseObjectProperties(OWLObjectPropertyExpression arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getInverseObjectProperties(arg0);
//    }
//
//    public NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getObjectPropertyDomains(arg0, arg1);
//    }
//
//    public NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getObjectPropertyRanges(arg0, arg1);
//    }
//
//    public NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual arg0, OWLObjectPropertyExpression arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getObjectPropertyValues(arg0, arg1);
//    }
//
//    public String getReasonerName() {
//        return reasoner.getReasonerName();
//    }
//
//    public Version getReasonerVersion() {
//        return reasoner.getReasonerVersion();
//    }
//
//    public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual arg0)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getSameIndividuals(arg0);
//    }
//
//    public NodeSet<OWLClass> getSubClasses(OWLClassExpression arg0, boolean arg1)
//            throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException,
//            InconsistentOntologyException, ClassExpressionNotInProfileException {
//        return reasoner.getSubClasses(arg0, arg1);
//    }
//
//    public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getSubDataProperties(arg0, arg1);
//    }
//
//    public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(OWLObjectPropertyExpression arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getSubObjectProperties(arg0, arg1);
//    }
//
//    public NodeSet<OWLClass> getSuperClasses(OWLClassExpression arg0, boolean arg1)
//            throws InconsistentOntologyException, ClassExpressionNotInProfileException,
//            FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
//        return reasoner.getSuperClasses(arg0, arg1);
//    }
//
//    public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getSuperDataProperties(arg0, arg1);
//    }
//
//    public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(OWLObjectPropertyExpression arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getSuperObjectProperties(arg0, arg1);
//    }
//
//    public long getTimeOut() {
//        return reasoner.getTimeOut();
//    }
//
//    public NodeSet<OWLClass> getTypes(OWLNamedIndividual arg0, boolean arg1)
//            throws InconsistentOntologyException, FreshEntitiesException,
//            ReasonerInterruptedException,
//            TimeOutException {
//        return reasoner.getTypes(arg0, arg1);
//    }
//
//    public Node<OWLClass> getUnsatisfiableClasses()
//            throws ReasonerInterruptedException, TimeOutException,
//            InconsistentOntologyException {
//        return reasoner.getUnsatisfiableClasses();
//    }
//
//    public void interrupt() {
//        reasoner.interrupt();
//    }
//
//    public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException {
//        return reasoner.isConsistent();
//    }
//
//    public boolean isEntailed(OWLAxiom arg0)
//            throws ReasonerInterruptedException, UnsupportedEntailmentTypeException,
//            TimeOutException,
//            AxiomNotInProfileException, FreshEntitiesException,
//            InconsistentOntologyException {
//        return reasoner.isEntailed(arg0);
//    }
//
//    public boolean isEntailed(Set<? extends OWLAxiom> arg0)
//            throws ReasonerInterruptedException, UnsupportedEntailmentTypeException,
//            TimeOutException,
//            AxiomNotInProfileException, FreshEntitiesException,
//            InconsistentOntologyException {
//        return reasoner.isEntailed(arg0);
//    }
//
//    public boolean isEntailmentCheckingSupported(AxiomType<?> arg0) {
//        return reasoner.isEntailmentCheckingSupported(arg0);
//    }
//
//    public boolean isPrecomputed(InferenceType arg0) {
//        return reasoner.isPrecomputed(arg0);
//    }
//
//    public boolean isSatisfiable(OWLClassExpression arg0)
//            throws ReasonerInterruptedException, TimeOutException,
//            ClassExpressionNotInProfileException,
//            FreshEntitiesException, InconsistentOntologyException {
//        return reasoner.isSatisfiable(arg0);
//    }
//
//}
