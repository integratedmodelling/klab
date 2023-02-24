package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.services.reasoner.objects.Concept;
import org.integratedmodelling.klab.api.services.reasoner.objects.Observable;

public interface KReasoner {
    /**
     * 
     * @param definition
     * @return
     */
    Concept resolveConcept(String definition);

    /**
     * 
     * @param definition
     * @return
     */
    Observable resolveObservable(String definition);
}
