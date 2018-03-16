package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

/**
 * FIXME review API - this is exposing all the wrong things.
 * 
 * @author ferdinando.villa
 *
 */
public interface IRuntimeService {

  void info(Object... o);

  void warn(Object... o);

  void error(Object... o);

  void debug(Object... o);

  /**
   * Storage for states is provided by a component found in the classpath. If more storage
   * components are available, configuration must have been defined to choose it. This allows
   * 
   * @return the storage provider.
   * @throws KlabRuntimeException if no storage provider is installed or there is more than one
   *         without appropriate configuration.
   */
  IStorageProvider getStorageProvider();

  /**
   * Create and return storage for a singleton state of the passed observable, holding one value
   * that won't change in any dimension of the scale. Requested by the runtime when constant values
   * are used in specifications and no modifications are made that impact the value across a
   * context. The singleton state is able to promote itself to a regular one if it setters are
   * invoked after the initial instantiation, as a response to an external event not predictable
   * from code analysis.
   * 
   * @param observable
   * @param scale
   * @return singleton storage for the passed observable
   */
  IStorage<?> getSingletonStorage(IObservable observable, IScale scale);

}
