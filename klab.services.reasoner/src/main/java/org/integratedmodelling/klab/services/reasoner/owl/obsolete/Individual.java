package org.integratedmodelling.klab.services.reasoner.owl.obsolete;
//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IIndividual;
//import org.integratedmodelling.klab.api.knowledge.IMetadata;
//import org.integratedmodelling.klab.api.knowledge.IOntology;
//import org.integratedmodelling.klab.api.knowledge.IProperty;
//import org.integratedmodelling.klab.api.knowledge.ISemantic;
//import org.integratedmodelling.klab.data.Metadata;
//import org.integratedmodelling.klab.utils.NameGenerator;
//import org.semanticweb.owlapi.model.AddAxiom;
//import org.semanticweb.owlapi.model.IRI;
//import org.semanticweb.owlapi.model.OWLDataFactory;
//import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
//import org.semanticweb.owlapi.model.OWLIndividual;
//import org.semanticweb.owlapi.model.OWLLiteral;
//import org.semanticweb.owlapi.model.OWLNamedIndividual;
//import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
//
//public class Individual implements IIndividual {
//
//    protected OWLIndividual individual;
//    protected IOntology     ontology;
//    protected IConcept      concept;
//    protected Metadata      metadata;
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
//     * @param semantics
//     * @param context
//     */
//    Individual(OWLIndividual individual, IOntology ontology) {
//        this.individual = individual;
//        this.owlName = individual.isNamed() ? ((OWLNamedIndividual) individual).getIRI().getFragment()
//                : NameGenerator.shortUUID();
//        this.ontology = ontology;
//        this.metadata = new Metadata();
//    }
//
//    /**
//     * This constructor is the only one that should be used from the API.
//     * 
//     * @param observable
//     * @param name
//     * @param context
//     * @param ontology
//     */
//    public Individual(IConcept observable, String name, IOntology ontology) {
//        define(observable, name, ontology);
//    }
//
//    public Individual(Individual individual) {
//        this.individual = individual.individual;
//        this.ontology = individual.ontology;
//        this.concept = individual.concept;
//        this.owlName = individual.owlName;
//        this.metadata = individual.metadata;
//    }
//
//    public void define(IConcept concept, String name, IOntology ontology) {
//
//        this.concept = concept;
//        this.owlName = name;
//        this.ontology = ontology;
//        List<AddAxiom> axioms = new ArrayList<>();
//        ((Ontology) ontology).addImport(concept.getOntology());
//        OWLDataFactory factory = ((Ontology) ontology).ontology.getOWLOntologyManager().getOWLDataFactory();
//        this.individual = factory
//                .getOWLNamedIndividual(IRI
//                        .create(((Ontology) this.ontology).getOWLOntology().getOntologyID().getOntologyIRI()
//                                + "#" + name));
//        axioms.add(new AddAxiom(((Ontology) this.ontology).getOWLOntology(), factory
//                .getOWLClassAssertionAxiom(((Concept) concept)._owl, this.individual)));
//        for (AddAxiom aa : axioms) {
//            ((Ontology) this.ontology).getOWLOntology().getOWLOntologyManager().applyChange(aa);
//        }
//
//        ((Ontology) ontology).individuals.put(name, this);
//    }
//
//    @Override
//    public IConcept getType() {
//        return concept;
//    }
//
//    @Override
//    public Collection<IIndividual> getIndividuals(IProperty property) {
//        List<IIndividual> ret = new ArrayList<>();
//        for (OWLIndividual ind : individual
//                .getObjectPropertyValues(((Property) property)._owl
//                        .asOWLObjectProperty(), ((Ontology) this.ontology).getOWLOntology())) {
//            ret.add(new Individual(ind, ontology));
//        }
//        return ret;
//    }
//
//    @Override
//    public Collection<Object> getData(IProperty property) {
//        List<Object> ret = new ArrayList<>();
//        for (OWLLiteral dat : individual
//                .getDataPropertyValues(((Property) property)._owl
//                        .asOWLDataProperty(), ((Ontology) this.ontology).getOWLOntology())) {
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
//        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> pvals = individual
//                .getObjectPropertyValues(((Ontology) this.ontology).getOWLOntology());
//        for (OWLObjectPropertyExpression p : pvals.keySet()) {
//            IProperty prop = OWL.INSTANCE.getPropertyFor(p.asOWLObjectProperty().getIRI());
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
//        Map<OWLDataPropertyExpression, Set<OWLLiteral>> pvals = individual
//                .getDataPropertyValues(((Ontology) this.ontology).getOWLOntology());
//        for (OWLDataPropertyExpression p : pvals.keySet()) {
//            IProperty prop = OWL.INSTANCE.getPropertyFor(p.asOWLDataProperty().getIRI());
//            if (prop != null) {
//                ret.add(prop);
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public IMetadata getMetadata() {
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
//    @Override
//	public String getName() {
//		return owlName;
//	}
//
//}
