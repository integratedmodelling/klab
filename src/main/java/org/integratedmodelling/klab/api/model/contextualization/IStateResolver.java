package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * The resolver for a {@link IState} when the state must be resolved in a point-by-point
 * fashion.
 * 
 * @author ferdinando.villa
 *
 */
public interface IStateResolver extends IContextualizer {

  /**
   * Return the individual value at the passed locator.
   * 
   * @param observation
   * @param context
   * @param locator
   * @return
   */
  Object resolve(IStorage<?> observation, IComputationContext context, ILocator locator);

}
