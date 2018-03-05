package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A Resolver is a {@link IContextualizer} that <i>explains</i> an existing observation by ensuring
 * that it conforms to its definition.
 * 
 * @author ferdinando.villa
 * @param <T> the observation type resolved
 *
 */
public abstract interface IResolver<T extends IObservationData> extends IContextualizer {

  /**
   * Called at each relevant extent location for the scale and the geometry of the observation being
   * resolved.
   * 
   * @param observation the observation being resolved.
   * @param context the runtime context of the computation.
   * @return the final observation - either the same passed or a new one if mediation was necessary.
   */
  T resolve(T observation, IComputationContext context);

}
