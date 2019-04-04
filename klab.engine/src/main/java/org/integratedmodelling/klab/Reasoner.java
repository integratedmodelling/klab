package org.integratedmodelling.klab;

import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IReasonerService;
import org.integratedmodelling.klab.owl.KlabReasoner;
import org.integratedmodelling.klab.owl.Ontology;

public enum Reasoner implements IReasonerService {
    
    INSTANCE;
	
    private KlabReasoner reasoner;

    private Reasoner() {
		Services.INSTANCE.registerService(this, IReasonerService.class);
    }
    
    public void setReasoner(KlabReasoner klabReasoner) {
        this.reasoner = klabReasoner;
    }

    public void addOntology(Ontology ontology) {
        this.reasoner.addOntology(ontology);
    }

    public Ontology getOntology() {
        return (Ontology)reasoner.getOntology();
    }

    @Override
    public boolean isSatisfiable(IConcept concept) {
        return reasoner.isSatisfiable(concept);
    }

    @Override
    public Set<IConcept> getParentClosure(IConcept main) {
        return reasoner.getParentClosure(main);
    }

    @Override
    public Set<IConcept> getSemanticClosure(IConcept main) {
        return reasoner.getSemanticClosure(main);
    }
    
}