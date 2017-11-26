package org.integratedmodelling.klab.api.data.semantic;

import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.klab.api.observations.ISubject;

public interface IObservationSemantics {

    IKimConceptStatement getObservationType();
    
    ISubject getObserver();
    
}
