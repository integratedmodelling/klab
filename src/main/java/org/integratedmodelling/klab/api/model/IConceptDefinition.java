package org.integratedmodelling.klab.api.model;

import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * k.IM object corresponding to the top-level definition of a concept. Contains
 * the concept defined as well as the syntactic elements that generated it.
 * 
 * @author fvilla
 *
 */
public interface IConceptDefinition extends IKimObject, INamespaceQualified {

    /**
     * Get the top-level declared concept.
     * @return the concept resulting from the definition
     */
    IConcept getConcept();
    
}
