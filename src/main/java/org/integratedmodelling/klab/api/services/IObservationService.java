package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.model.IObserver;
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
     * @return the initialized context.
     */
    IContext initialize(ISubject observation, ISession session);

    /**
     * Release all information pertaining to named namespace, both in live and 
     * persistent storage.
     * 
     * @param name
     */
    void releaseNamespace(String name);

    /**
     * Index passed observation definition for retrieval.
     * 
     * @param observer
     */
    void index(IObserver observer);

}
