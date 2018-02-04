package org.integratedmodelling.klab.api.data.raw;

import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

/**
 * IStorage is a IRawState that is typed and contains API to be used within a semantic IState, so it can be
 * set using {@link Locator}s.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface IStorage<T> extends IRawState {

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

}
