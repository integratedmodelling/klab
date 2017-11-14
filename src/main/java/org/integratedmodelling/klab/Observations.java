package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IObservationService;

public enum Observations implements IObservationService {
    INSTANCE;

    public void registerSubjectClass(String concept, Class<? extends ISubject> cls) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public IContext initialize(ISubject observation, ISession session) {
        return null;
    }
}
