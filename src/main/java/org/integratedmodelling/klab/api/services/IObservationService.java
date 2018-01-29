package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.model.IObserver;

/**
 * Manage observations stored locally (as IObserver specifications) and provide an API to query and 
 * retrieve those stored on the k.LAB network.
 * 
 * @author ferdinando.villa
 *
 */
public interface IObservationService {

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
