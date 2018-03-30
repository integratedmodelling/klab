package org.integratedmodelling.klab.api.model;

/**
 * Any object that is part of a namespace implements this interface, which
 * simply provides access to the namespace the object is part of.
 * 
 * @author fvilla
 *
 */
public interface INamespaceQualified {

    /**
     * Return the namespace this is part of.
     * @return namespace this belongs to.
     */
    INamespace getNamespace();
    
}
