package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.services.IReasonerService;
import org.integratedmodelling.klab.owl.KLABReasoner;

public enum Reasoner implements IReasonerService {
    
    INSTANCE;

    private KLABReasoner reasoner;
    
    public void setReasoner(KLABReasoner klabReasoner) {
        this.reasoner = klabReasoner;
    }

    public void addOntology(IOntology ontology) {
        this.reasoner.addOntology(ontology);
    }
    
}