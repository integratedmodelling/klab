package org.integratedmodelling.klab.api.model.contextualization;

import javax.annotation.Nullable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;

/**
 * A Resolver is a {@link IContextualizer} that <i>explains</i> an observation by ensuring that it
 * conforms to its definition.
 * 
 * @author ferdinando.villa
 * @param <T> the observation type resolved
 *
 */
public abstract interface IResolver<T extends IObservation> extends IContextualizer {

  /**
   * Called at each relevant extent location for the scale and the geometry of the observation being
   * resolved. A {@link IState} resolver will call
   * {@link #resolve(IObservation, IDirectObservation, Locator)} at each extent (so potentially
   * multiple times per transition}, while any direct observation will only call it once at
   * initialization and once per transition.
   * 
   * @param observation the observation being resolved.
   * @param context the observation that provides the context for the one being resolved. It will be
   *        null when that observation is the root context.
   * @param locator defines the position on the observation's scale.
   */
  void resolve(T observation, @Nullable IDirectObservation context, Locator locator);

}
