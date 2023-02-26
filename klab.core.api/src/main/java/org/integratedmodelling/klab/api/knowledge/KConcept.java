package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.data.KMetadata;

public interface KConcept extends KSemantics {

    /**
     * 
     * @return
     */
    Collection<SemanticType> getType();

    /**
     * 
     * @return
     */
    KMetadata getMetadata();

    /**
     * If our semantic type is UNION or INTERSECTION, return the operands of the logical connector.
     * Otherwise return the singleton of this object.
     * 
     * @return
     */
    Collection<KConcept> operands();
    
    Collection<KConcept> children();

    KConcept parent();

    Collection<KConcept> parents();
    
    Collection<KConcept> allChildren();
    
    Collection<KConcept> allParents();
    
    Collection<KConcept> closure();

}
