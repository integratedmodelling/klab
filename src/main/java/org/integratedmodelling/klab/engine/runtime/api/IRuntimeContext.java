package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;

/**
 * This API extends {@link IComputationContext} to add setters and other functionalities that is
 * needed at runtime. It is provided to allow any {@link IRuntimeProvider} to define contexts as
 * they need.
 * 
 * @author Ferd
 *
 */
public interface IRuntimeContext extends IComputationContext {

  // TODO tentative
  Collection<IObservable> getKnownObservables();
  
  /**
   * Get the resolved object corresponding to the passed local name.
   * 
   * @param localName
   * @return
   */
  IObservationData get(String localName);

  /**
   * 
   * @return
   */
  IConfigurationDetector getConfigurationDetector();

  /**
   * Produce an exact copy of this context.
   * 
   * @return
   */
  IRuntimeContext copy();

  /**
   * Rename the passed observation data as the passed alias.
   * 
   * @param name
   * @param alias
   */
  void rename(String name, String alias);
}
