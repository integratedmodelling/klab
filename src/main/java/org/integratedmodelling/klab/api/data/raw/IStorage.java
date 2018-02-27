package org.integratedmodelling.klab.api.data.raw;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

/**
 * IStorage is a {@link IRawState} that is typed and contains API to be used within a semantic
 * {@link IState}, so it admits {@link Locator}s as indices for getting and setting values.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface IStorage<T> extends IRawState {

  /**
   * Get the n-th object.
   * 
   * @param index
   * @return value at index
   */
  T get(long index);

  /**
   * Set the value at given index. Improper values are a runtime exception.
   * 
   * @param index
   * @param value a compatible value. Usually of type T, but can be others - e.g. a probability
   *        distribution for it. The state is expected to quickly promote itself to a different
   *        underlying implementation if a compatible value of a new type is expected.
   *        
   * @throws IllegalArgumentException if value is incompatible with type.
   */
  void set(long index, Object value);

  /**
   * Bulk set of raw data in specified position.
   * 
   * @param data
   * @param locators
   */
  void set(Object data, Locator... locators);

  /**
   * Total number of values. Should be compatible with the multiplicity of the scale in the state
   * that owns t.
   * 
   * @return total count of states
   */
  long size();

}
