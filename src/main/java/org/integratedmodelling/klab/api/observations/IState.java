package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IState extends IObservation {
   
    /**
     * 
     * @return
     */
    IStorage<?> getStorage();


    /**
     * Listener that we can install with {@link #addChangeListener} to be notified of
     * changes.
     * 
     * @author ferd
     *
     */
    interface ChangeListener {

        /**
         * Called every time a new value is set.
         * 
         * @param offset
         * @param value
         */
        void changed(int offset, Object value);

        /**
         * Called once before a new transition is executed or at the end of
         * initialization.
         * 
         * @param transaction
         */
        void transitionDone(ITransition transaction);
    }

    /**
     * Mediators are created by extents and are used to implement views of a state that
     * mediate values to another scale.
     * 
     * A mediator should be aware that the extents it mediates may have changed (it can
     * use States.hasChanged() to inspect that) and be able to readjust if necessary. This
     * will properly handle moving agents.
     * 
     * FIXME all mediators should be change listeners for the mediated state, and
     * rearrange the mediation strategy at each change as needed - we should subscribe
     * them automatically.
     * 
     * @author ferdinando.villa
     *
     */
    interface Mediator {

        /**
         * Aggregation mode. The default is AVERAGE for intensive properties and
         * non-physical properties or SUM for extensive properties, but data reduction
         * traits in the target may modify it (e.g. we may want the MAX if we tag the
         * final observer with im:Maximum). MAJORITY will be the default for qualitative
         * and semi-qualitative observers; at some point we may want to add fuzzy
         * membership and other more sophisticated strategies for probabilistic observers.
         * 
         * @author ferdinando.villa
         *
         */
        enum Aggregation {
            NONE,
            SUM,
            AVERAGE,
            MIN,
            MAX,
            MAJORITY,
            MAXIMUM_LIKELIHOOD
        }

        /**
         * These keys MAY be available after each mediation in the state's metadata. Their
         * meaning may differ according to the observer.
         * 
         * @author ferdinando.villa
         *
         */
        public final static String SPACE_MIN_VALUE          = "Mediator.SPACE_MIN_VALUE";
        public final static String SPACE_MAX_VALUE          = "Mediator.SPACE_MAX_VALUE";
        public final static String SPACE_VALUE_SUM          = "Mediator.SPACE_VALUE_SUM";
        public final static String SPACE_VALUE_DISTRIBUTION = "Mediator.SPACE_VALUE_DISTRIBUTION";
        public final static String SPACE_TOTAL_VALUES       = "Mediator.SPACE_TOTAL_VALUES";
        public final static String SPACE_CONFIDENCE         = "Mediator.SPACE_CONFIDENCE";
        public final static String SPACE_ERROR              = "Mediator.SPACE_ERROR";
        public final static String TIME_MIN_VALUE           = "Mediator.TIME_MIN_VALUE";
        public final static String TIME_MAX_VALUE           = "Mediator.TIME_MAX_VALUE";
        public final static String TIME_VALUE_SUM           = "Mediator.TIME_VALUE_SUM";
        public final static String TIME_VALUE_DISTRIBUTION  = "Mediator.TIME_VALUE_DISTRIBUTION";
        public final static String TIME_TOTAL_VALUES        = "Mediator.TIME_TOTAL_VALUES";
        public final static String TIME_CONFIDENCE          = "Mediator.TIME_CONFIDENCE";
        public final static String TIME_ERROR               = "Mediator.TIME_ERROR";

        /**
         * The kind of aggregation that the mediation implies.
         * 
         * @return aggregation type
         */
        Aggregation getAggregation();

        /**
         * Apply the locators to the original state, adding whatever other locators the
         * mediation strategy implies. Return the aggregated value implied by the
         * strategy.
         * 
         * @param originalState
         * @param otherLocators
         *
         * @return a mediated object
         */
        Object mediateFrom(IState originalState, IScale.Locator... otherLocators);

        /**
         * Apply the passed value to our scale and return the result.
         * 
         * @param value
         * @param index
         * @return a mediated object
         */
        Object mediateTo(Object value, int index);

        /**
         * Get all the locators that will map the original state's scale to the passed
         * index in the mediated scale. Weights should be assigned according to coverage
         * and aggregation strategy.
         * 
         * @param index
         * @return the locators needed to mediate
         */
        List<IScale.Locator> getLocators(int index);

        /**
         * Reduce the passed collection of pairs (value, weight) to one value according to
         * aggregation strategy.
         * 
         * @param toReduce
         * @param metadata
         *            a map to fill with any relevant statistics related to the
         *            aggregation (errors, uncertainty, boundaries, distributions, truth
         *            values etc) using the keys above.
         * 
         * @return the reduced value
         */
        Object reduce(Collection<IPair<Object, Double>> toReduce, IMetadata metadata);
    }

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
    Object getValue(Locator locator);


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

    /**
     * Add a listener to notify when any value is changed.
     * 
     * @param listener
     */
    void addChangeListener(ChangeListener listener);

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
