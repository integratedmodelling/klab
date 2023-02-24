package org.integratedmodelling.klab.api.knowledge;

import java.util.Collection;

import org.integratedmodelling.klab.api.data.KMetadata;

public interface KConcept extends KKnowledge {

    /**
     * 
     * @return
     */
    Collection<SemanticType> getType();
    
    /**
     * 
     * @param other
     * @return
     */
    boolean is(KConcept other);

    /**
     * 
     * @param type
     * @return
     */
    boolean is(SemanticType type);
    
    /**
     * 
     * @return
     */
    KMetadata getMetadata();

}
