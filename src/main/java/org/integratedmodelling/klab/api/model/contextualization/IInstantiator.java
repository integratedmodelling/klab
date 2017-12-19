package org.integratedmodelling.klab.api.model.contextualization;

import java.util.List;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

/**
 * Instantiators are contextualizers that can be provided for any direct observation and are called
 * to produce observations in a context.
 * 
 * @author ferdinando.villa
 *
 * @param <T>
 */
public interface IInstantiator<T extends IDirectObservation> extends IContextualizer {

    /**
     * Instantiate and return the target observations in the passed context. Those observations will
     * be independently resolved afterwards by the dataflow.
     * 
     * @param context the context observation.
     * @param transition the current transition, which could be initialization.
     *
     * @return a list of observations, possibly empty but never null.
     */
    List<T> instantiate(ISubject context, Locator transition);
    
}
