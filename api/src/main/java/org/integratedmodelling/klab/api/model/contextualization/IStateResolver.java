package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.knowledge.IObservable;
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
   * @param observable the semantics for what is being computed and returned
   * @param context the computation context, located at the specific state
   * @return the computed value at the locator
   * @throws KlabException
   */
  Object resolve(IObservable observable, IComputationContext context) throws KlabException;

}
