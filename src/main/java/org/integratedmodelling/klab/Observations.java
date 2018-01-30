package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.observation.Subject;

public enum Observations implements IObservationService {
    INSTANCE;

    public void registerSubjectClass(String concept, Class<? extends ISubject> cls) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void releaseNamespace(String name) {
        // TODO remove all artifacts from local kbox
    }
    
    @Override
    public void index(IObserver observer) {
        // TODO
    }

    @Override
    public Subject createSubject(IObserver observer, IMonitor monitor) {
      // TODO Auto-generated method stub
      return null;
    }
}
