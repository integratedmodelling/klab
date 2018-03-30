package org.integratedmodelling.klab.api.services;

import java.util.Set;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public interface IReasonerService {

    /**
     * 
     * @param main
     * @return
     */
    Set<IConcept> getSemanticClosure(IConcept main);

    /**
     * 
     * @param main
     * @return
     */
    Set<IConcept> getParentClosure(IConcept main);

    /**
     * 
     * @param concept
     * @return
     */
    boolean isSatisfiable(IConcept concept);


}
