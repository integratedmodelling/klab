package org.integratedmodelling.klab.api.data.artifacts;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * A {@code IDataArtifact} is a {@link IArtifact} that is typed, owns storage and admits
 * {@link ILocator}s as indices for getting and setting POD values in it. The storage must be
 * conformant with the {@link IGeometry#size() size} and dimensions of the {@link #getGeometry()
 * linked} {@link IGeometry geometry}.
 * <p>
 * According to the context of computation, the size of a data artifact may differ from
 * {@link IGeometry#size()}. For example, a non-dynamic state in a dynamic context (where time
 * advances but the observable cannot be inferred to change in the context) may only receive updates
 * in case of event-related modifications. In such cases the state may only contain the time
 * dimensions where change has happened.
 * <p>
 * 
 * @author Ferd
 */
public interface IDataArtifact extends IArtifact {

  /**
   * Get the POD object pointed to by the locator. If the locator implies mediation, this should be
   * supported. If the locator is incompatible with the geometry, throw an exception.
   * 
   * @param index a locator for the state. If the locator implies mediation, propagation or
   *        aggregation should be done.
   * @return value at index
   * @throws IllegalArgumentException if the locator is not compatible with the artifact's geometry.
   */
  Object get(ILocator index);
  
  /**
   * Get the POD object pointed to by the locator. If the locator implies mediation, this should be
   * supported. If the locator is incompatible with the geometry, throw an exception.
   * 
   * @param index a locator for the state. If the locator implies mediation, propagation or
   *        aggregation should be done.
   * @param cls the class of the result we want
   * @return value at index
   * @throws IllegalArgumentException if the locator is not compatible with the artifact's geometry.
   */
  <T> T get(ILocator index, Class<T> cls);

  /**
   * Set the value(s) at given index. Improper values or locators cause an unchecked exception.
   * 
   * @param index a locator for the state. If the locator implies mediation, propagation or
   *        aggregation should be done.
   * @param value a compatible value. Usually of type T, but can be others - e.g. a probability
   *        distribution for it. The state is expected to quickly promote itself to a different
   *        underlying implementation if a compatible value of a new type is expected.
   * 
   * @return the linear offset corresponding to the locator in storage (for checking and debugging
   *         only)
   * @throws IllegalArgumentException if value is incompatible with type or locator is not
   *         compatible with the geometry.
   */
  long set(ILocator index, Object value);

  /**
   * Total number of values. Must be compatible with the size of the dimensions of the underlying
   * geometry.
   * 
   * @return total count of states
   */
  long size();

}
