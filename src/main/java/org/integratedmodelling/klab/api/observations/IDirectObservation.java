package org.integratedmodelling.klab.api.observations;

import java.util.Collection;

public interface IDirectObservation extends IObservation {

    String getName();
    
    Collection<IState> getStates();
    
}
