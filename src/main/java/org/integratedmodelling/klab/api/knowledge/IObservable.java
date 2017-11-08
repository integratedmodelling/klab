package org.integratedmodelling.klab.api.knowledge;

import java.util.Optional;

import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.klab.api.data.semantic.IObservationSemantics;
import org.integratedmodelling.klab.api.observations.ISubject;

public interface IObservable extends ISemantic, IKimObservable {

    String getName();
    
    Optional<IObservationSemantics> getObservationSemantics();
    
    Optional<ISubject> getObserver();
    
}
