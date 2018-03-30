package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.knowledge.IOntology;

public interface IOntologyService {

    IOntology require(String name);

    void release(IOntology ontology);

}
