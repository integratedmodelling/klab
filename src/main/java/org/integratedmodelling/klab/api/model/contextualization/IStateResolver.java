package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
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
   * @param locator A locator corresponding to a single extent state.
   * @return the computed value at the locator
   */
  Object resolve(IDataArtifact observation, IComputationContext context, IScale locator);

}
