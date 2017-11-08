package org.integratedmodelling.klab.api.data.semantic;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.observations.ISubject;

public interface IObservationSemantics {

    IKimConcept getObservationType();
    
    ISubject getObserver();
    
}
