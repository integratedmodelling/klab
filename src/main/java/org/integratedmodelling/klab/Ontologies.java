package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.IOntologyService;

public enum Ontologies implements IOntologyService {
    INSTANCE;
    
    private Ontologies() {
    }
    
    public void initialize() {
        // load knowledge from resources, create concepts
    }
}
