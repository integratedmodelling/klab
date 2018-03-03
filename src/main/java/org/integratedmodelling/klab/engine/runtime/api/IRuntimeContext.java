package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.raw.IRawObservation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;

/**
 * This API extends {@link IComputationContext} to add setters and other functionalities that is
 * needed at runtime. It is provided to allow any {@link IRuntimeProvider} to define contexts as
 * they need.
 * 
 * @author Ferd
 *
 */
public interface IRuntimeContext extends IComputationContext {

  /**
   * Return a new context with the local environment, including all names for previous observations
   * and inputs, translated to what's understood within the passed actuator.
   * 
   * @param executor 
   * 
   * @return a context localized to the passed actuator
   */
  IRuntimeContext localize(IActuator executor);

  // TODO tentative
  Collection<IObservable> getKnownObservables();
  
  /**
   * Get the resolved object corresponding to the passed local name.
   * 
   * @param localName
   * @return
   */
  IRawObservation get(String localName);

  /**
   * 
   * @return
   */
  IConfigurationDetector getConfigurationDetector();
}
