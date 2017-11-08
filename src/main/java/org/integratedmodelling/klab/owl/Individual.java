//package org.integratedmodelling.klab.owl;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.integratedmodelling.api.knowledge.IConcept;
//import org.integratedmodelling.api.knowledge.IIndividual;
//import org.integratedmodelling.api.knowledge.IKnowledge;
//import org.integratedmodelling.api.knowledge.IOntology;
//import org.integratedmodelling.api.knowledge.IProperty;
//import org.integratedmodelling.api.knowledge.ISemantic;
//import org.integratedmodelling.api.metadata.IMetadata;
//import org.integratedmodelling.api.runtime.IContext;
//import org.integratedmodelling.common.metadata.Metadata;
//import org.integratedmodelling.common.model.runtime.AbstractContext;
//import org.integratedmodelling.common.utils.NameGenerator;
//import org.semanticweb.owlapi.model.AddAxiom;
//import org.semanticweb.owlapi.model.IRI;
//import org.semanticweb.owlapi.model.OWLDataFactory;
//import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
//import org.semanticweb.owlapi.model.OWLIndividual;
//import org.semanticweb.owlapi.model.OWLLiteral;
//import org.semanticweb.owlapi.model.OWLNamedIndividual;
//import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
//import org.semanticweb.owlapi.model.OWLOntology;
//
//public class Individual implements IIndividual {
//
//    protected OWLIndividual individual;
//    protected OWLOntology   ontology;
//    protected IKimConcept      concept;
//    protected Metadata      metadata;
//    protected IContext      context;
//    protected String        owlName;
//
//    /*
//     * ONLY for bean instantiator: call define() just after.
//     */
//    public Individual() {
//    }
//    
//    /**
//     * This constructor is the only one that should be used from the API.
//     * 
//     * @param observable
//     * @param context
//     */
//    Individual(OWLIndividual individual, IContext context) {
//        this.individual = individual;
//        this.owlName = individual.isNamed() ? ((OWLNamedIndividual)individual).getIRI().getFragment() : NameGenerator.shortUUID();
//        this.context = context;
//        this.ontology = ((Ontology)((AbstractContext)context).getOntology()).getOWLOntology();
//        this.metadata = new Metadata();
//    }
//
//    /**
//     * This constructor is the only one that should be used from the API.
//     * 
//     * @param observable
//     * @param context
//     */
//    public Individual(IKimConcept observable, String name, IContext context) {
//        define(observable, name, context);
//    }
//
//    public Individual(Individual individual) {
//        this.individual = individual.individual;
//        this.ontology = individual.ontology;
//        this.concept = individual.concept;
//        this.context = individual.context;
//        this.owlName = individual.owlName;
//        this.metadata = individual.metadata;
//    }
//
//    public void define(IKimConcept observable, String name, /* @Nullable */ IContext context) {
//        this.context = context;
//        define(observable, name, context == null ? observable.getOntology()
//                : ((AbstractContext) context).getOntology());
//    }
//
//    public IOntology getOntology() {
//        return ((AbstractContext)this.context).getOntology();
//    }
//    
//    public void define(IKimConcept concept, String name, IOntology ontology) {
//
//        this.concept = concept;
//        this.owlName = name;
//        this.ontology = ((Ontology) ontology).getOWLOntology();
//        List<AddAxiom> axioms = new ArrayList<>();
//        ((Ontology) ontology).addImport(concept.getOntology());
//        OWLDataFactory factory = ((Ontology) ontology).ontology.getOWLOntologyManager().getOWLDataFactory();
//        this.individual = factory
//                .getOWLNamedIndividual(IRI
//                        .create(this.ontology.getOntologyID().getOntologyIRI() + "#" + name));
//        axioms.add(new AddAxiom(this.ontology, factory
//                .getOWLClassAssertionAxiom(((Concept) concept)._owl, this.individual)));
//        for (AddAxiom aa : axioms) {
//            this.ontology.getOWLOntologyManager().applyChange(aa);
//        }
//        
//        ((Ontology)ontology).individuals.put(name, this);
//    }
//
//    @Override
//    public IKimConcept getType() {
//        return concept;
//    }
//
//    @Override
//    public Collection<IIndividual> getIndividuals(IProperty property) {
//        List<IIndividual> ret = new ArrayList<>();
//        for (OWLIndividual ind : individual.getObjectPropertyValues(((Property)property)._owl.asOWLObjectProperty(), ontology)) {
//            ret.add(new Individual(ind, context));
//        }
//        return ret;
//    }
//
//    @Override
//    public Collection<Object> getData(IProperty property) {
//        List<Object> ret = new ArrayList<>();
//        for (OWLLiteral dat : individual.getDataPropertyValues(((Property)property)._owl.asOWLDataProperty(), ontology)) {
//            if (dat.isBoolean()) {
//                ret.add(dat.parseBoolean());
//            } else if (dat.isBoolean()) {
//                ret.add(dat.parseBoolean());
//            } else if (dat.isDouble()) {
//                ret.add(dat.parseDouble());
//            } else if (dat.isFloat()) {
//                ret.add(dat.parseFloat());
//            } else if (dat.isInteger()) {
//                ret.add(dat.parseFloat());
//            } else {
//                ret.add(dat.getLiteral());
//            } 
//        }
//        return ret;
//    }
//
//    @Override
//    public Collection<IProperty> getObjectRelationships() {
//        List<IProperty> ret = new ArrayList<>();
//        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> pvals = individual.getObjectPropertyValues(ontology);
//        for (OWLObjectPropertyExpression p : pvals.keySet()) {
//            IProperty prop = ((Concept)concept)._manager.getPropertyFor(p.asOWLObjectProperty().getIRI());
//            if (prop != null) {
//                ret.add(prop);
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public Collection<IProperty> getDataRelationships() {
//        List<IProperty> ret = new ArrayList<>();
//        Map<OWLDataPropertyExpression, Set<OWLLiteral>> pvals = individual.getDataPropertyValues(ontology);
//        for (OWLDataPropertyExpression p : pvals.keySet()) {
//            IProperty prop = ((Concept)concept)._manager.getPropertyFor(p.asOWLDataProperty().getIRI());
//            if (prop != null) {
//                ret.add(prop);
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public IKimMetadata getMetadata() {
//        if (metadata == null) {
//            metadata = new Metadata();
//        }
//        return metadata;
//    }
//
//    @Override
//    public boolean is(ISemantic type) {
//        return concept.is(type.getType());
//    }
//
//}
