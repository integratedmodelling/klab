package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.data.raw.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

/**
 * The k.LAB runtime. 
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

  IRuntimeProvider getRuntimeProvider();

}
