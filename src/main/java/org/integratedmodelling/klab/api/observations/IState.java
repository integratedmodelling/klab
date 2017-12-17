package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.data.IStorage;

public interface IState extends IObservation {
    
    IStorage getStorage();

}
