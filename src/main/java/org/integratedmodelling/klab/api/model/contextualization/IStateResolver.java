package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The resolver for a {@link IState} when the state must be resolved in a point-by-point fashion.
 * 
 * @author ferdinando.villa
 *
 */
public interface IStateResolver extends IContextualizer {

  /**
   * Return the individual value at the locator identified by
   * {@link IComputationContext#getGeometry() the context's geometry}. This will be called for as
   * many times as there are subdivisions in the scale.
   * 
   * @param observation
   * @param context the computation context, located at the specific state
   * @return the computed value at the locator
   * @throws KlabException
   */
  Object resolve(IDataArtifact observation, IComputationContext context) throws KlabException;

}
