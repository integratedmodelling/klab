package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.services.IOntologyService;
import org.integratedmodelling.klab.owl.OWL;

public enum Ontologies implements IOntologyService {
    INSTANCE;
    
    private Ontologies() {
    }
    
    @Override
    public IOntology require(String name) {
        return OWL.INSTANCE.requireOntology(name, OWL.INTERNAL_ONTOLOGY_PREFIX);    
    }
    
    @Override
    public void release(IOntology ontology) {
        OWL.INSTANCE.releaseOntology(ontology);
    }
}
