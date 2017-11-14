package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.api.runtime.ISession;

public interface IObservationService {

    /**
     * The functions that resolves a subject, returning the corresponding
     * live context.
     * 
     * @param observation
     * @param session 
     * @return
     */
    IContext initialize(ISubject observation, ISession session);

}
