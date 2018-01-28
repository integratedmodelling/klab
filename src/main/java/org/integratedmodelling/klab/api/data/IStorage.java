package org.integratedmodelling.klab.api.data;

import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

public interface IStorage<T> {


    /**
     * Get the n-th object
     * @param index
     * @return value at index
     */
    T get(int index);

    /**
     * Set the value at given index. Improper values are a runtime exception.
     * 
     * @param index
     * @param value
     */
    void set(int index, Object value);

    /**
     * Bulk set of raw data in specified position.
     * @param data
     * @param locators
     */
    void set(Object data, Locator... locators);

    /**
     * Number of total objects.
     * 
     * @return total count of states
     */
    int size();

    /**
     * Dynamic storage is for data that won't change after initialization is complete, i.e.
     * will not change in time. This is more a "dynamic" than "read only" status
     * 
     * Distinguishing dynamic storage is important because if styatic, no space will be
     * wasted for history in backing datasets and model output (the time dimension won't
     * be added to the corresponding variables). Space occupation for temporal/spatial
     * datasets can be very high.
     * 
     * By default, all inputs of a model will be static unless the corresponding states 
     * are tagged writable by a contextualizer during initialize() or the model has 
     * change/integrate actions that depend on time. Outputs are writable by default;
     * contextualizers should create read-only states when appropriate.
     * 
     * @return true if dynamic
     */
    boolean isDynamic();

    /**
     * Minimum value of numeric equivalent of content across the whole storage, or
     * Double.NaN if not applicable or no-data across.
     * 
     * @return min value if any
     */
    double getMin();

    /**
     * Maximum value of numeric equivalent of content across the whole storage, or
     * Double.NaN if not applicable or no-data across.
     * 
     * @return max value if any
     */
    double getMax();

    /**
     * If true, this stores probability distributions.
     * 
     * @return
     */
    boolean isProbabilistic();
    
}
