package org.integratedmodelling.klab.api.model;

import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * k.IM object corresponding to the definition of a concept (the statement that creates it, as opposed to the
 * declaration by composing one or more existing concepts). Contains the concept defined as well as the
 * syntactic elements that generated it.
 * 
 * The {@link #getChildren()} method of an IConceptDefinition only returns other IConceptDefinitions.
 * 
 * @author fvilla
 *
 */
public interface IConceptDefinition extends IKimObject, INamespaceQualified {

    /**
     * Get the top-level declared concept.
     * 
     * @return the concept resulting from the definition
     */
    IConcept getConcept();

}
