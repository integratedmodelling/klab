package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IState extends IObservation {

    /**
     * True if the state has the same value overall independent of scale.
     * 
     * @return true if constant
     */
    boolean isConstant();

    /**
     * True if the state is expected to change in time. This depends on semantics.
     * 
     * @return true if dynamic
     */
    boolean isDynamic();

    /**
     * Return either the original state or a wrapper that will allow get/set of values 
     * in a specified observation semantics.
     *  
     * @param observable an observable that must be identical semantically but may have different
     *        observation semantics, e.g. a "by" clause or different units/currencies.
     * @return the (possibly wrapped) state
     */
    IState as(IObservable observable);
    
    /**
     * Specialize the data for a state.
     * 
     * @return the observation data. Never null.
     */
    @Override
    IStorage<?> getData();
    
}
