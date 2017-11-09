package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;

public interface IConceptService {

    IProperty getProperty(String propertyId);

    IConcept getConcept(String conceptId);

    IConcept declare(String declaration);

}
