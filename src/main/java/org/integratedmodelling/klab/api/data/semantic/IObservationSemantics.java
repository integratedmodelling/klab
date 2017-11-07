package org.integratedmodelling.klab.api.data.semantic;

import org.integratedmodelling.kim.api.IConcept;
import org.integratedmodelling.klab.api.observations.ISubject;

public interface IObservationSemantics {

    IConcept getObservationType();
    
    ISubject getObserver();
    
}
