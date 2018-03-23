package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A Resolver is a {@link IContextualizer} that <i>explains</i> an existing observation by ensuring
 * that it conforms to its definition.
 * 
 * @author ferdinando.villa
 * @param <T> the observation type resolved
 *
 */
public abstract interface IResolver<T extends IArtifact> extends IContextualizer {

  /**
   * Called once per temporal transition for the scale and the geometry of the observation being
   * resolved.
   * 
   * @param ret the observation being resolved.
   * @param context the runtime context of the computation.
   * @return the final observation - either the same passed or a new one if mediation was necessary.
   * @throws KlabException 
   */
  T resolve(T ret, IComputationContext context) throws KlabException;

}
