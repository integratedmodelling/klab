package org.integratedmodelling.klab.api.data;

import org.integratedmodelling.klab.api.data.raw.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * Interface for a component. If exactly one component implementing this is available, the system
 * will use that. Otherwise the configuration must be able to establish which storage component to
 * use.
 * 
 * @author ferdinando.villa
 *
 */
public interface IStorageProvider {

  /**
   * Create appropriate storage for the passed observable and scale. The storage must be able to
   * promote itself to probabilistic if a distribution is ever passed.
   * 
   * @param observable
   * @param scale
   * @param context 
   * @return
   */
  IDataArtifact createStorage(IObservable observable, IScale scale, IComputationContext context);

}
