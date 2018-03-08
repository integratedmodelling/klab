package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IState extends IObservation {

//    /**
//     * Listener that we can install with {@link #addChangeListener} to be notified of
//     * changes.
//     * 
//     * FIXME turn into subscribing to event, passing a locator to establish relevance (when to fire)
//     * 
//     * @author ferd
//     * @deprecated use an event model
//     */
//    interface ChangeListener {
//
//        /**
//         * Called every time a new value is set.
//         * 
//         * @param offset
//         * @param value
//         */
//        void changed(ILocator offset, Object value);
//
//        /**
//         * Called once before a new transition is executed or at the end of
//         * initialization.
//         * 
//         * @param transaction
//         */
//        void transitionDone(ITransition transaction);
//    }
//


    /**
     * Return the total number of values determined by the scale of the owning
     * ISubject.
     * 
     * @return the value count. Should be 1 or more.
     */
    long getValueCount();

    /**
     * Get a value at the passed locator. The locator may be just a granule of the 
     * state's scale, or express a different topology from the same or another, 
     * involving mediation.
     *  
     * @param locator a locator from our own or another compatible scale.
     * @return the value at the passed locator, which may come from a different
     *  scale.
     */
    Object getValue(ILocator locator);

    /**
     * True if the state has the same value overall despite the scale.
     * 
     * @return true if constant
     */
    boolean isConstant();

    /**
     * True if the state is expected to change in time. This depends on semantics: 
     * 
     * @return true if dynamic
     */
    boolean isDynamic();

//    /**
//     * Add a listener to notify when any value is changed.
//     * 
//     * @param listener
//     * @deprecated use an event model
//     */
//    void addChangeListener(ChangeListener listener);

    /**
     * Return either the original state or a wrapper that will allow get/set of values 
     * in a specified observation semantics.
     *  
     * @param observable an observable that must be identical semantically but may have different
     *        observation semantics, e.g. a "by" clause or different units/currencies.
     * @return the (possibly wrapped) state
     */
    IState as(IObservable observable);
    
}
