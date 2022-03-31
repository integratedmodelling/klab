package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;

/**
 * Observations use this interface to get notified when other observations they depend on change.
 * 
 * @author ferdinando.villa
 *
 */
public interface IModificationListener {

    /**
     * 
     * @param modified
     */
    void onModification(IObservation modified);

    /**
     * Called when the first actual data are inserted (the object may be a POD or an observation in
     * the case of groups).
     * 
     * @param state
     */
    void onFirstNontrivialState(Object state, ITime currentTime);

    /**
     * Called when state referring to a time slice yet unobserved are first added. Not called at
     * initialization.
     * 
     * @param time
     */
    void onTemporalExtension(ITime time);

}
