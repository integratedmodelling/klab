package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.semantic.IObservationSemantics;

public interface IState extends IObservation {
    
    IObservationSemantics getObservationSemantics();
    
    IStorage getStorage();
}
