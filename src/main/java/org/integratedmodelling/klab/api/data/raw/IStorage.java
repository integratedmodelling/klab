package org.integratedmodelling.klab.api.data.raw;

import org.integratedmodelling.klab.api.observations.scale.ILocator;

/**
 * {@code IStorage} is a {@link IObservationData} that is typed and admits {@link ILocator}s as
 * indices for getting and setting POD values.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface IStorage<T> extends IObservationData {

  /**
   * Get the n-th object.
   * 
   * @param index
   * @return value at index
   */
  T get(ILocator index);

  /**
   * Set the value at given index. Improper values are a runtime exception.
   * 
   * @param index
   * @param value a compatible value. Usually of type T, but can be others - e.g. a probability
   *        distribution for it. The state is expected to quickly promote itself to a different
   *        underlying implementation if a compatible value of a new type is expected.
   * 
   * TODO check if this can be removed from the API
   * 
   * @throws IllegalArgumentException if value is incompatible with type.
   */
  void set(ILocator index, Object value);

  /**
   * Total number of values. Must be compatible with the size of the dimensions of the underlying
   * geometry.
   * 
   * @return total count of states
   */
  long size();

}
