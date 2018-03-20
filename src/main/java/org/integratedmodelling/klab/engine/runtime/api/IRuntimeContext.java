package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

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
   * Create a child context for the passed observable, containing the target
   * observation implementation.
   * 
   * @param target
   * @return
   */
  public IRuntimeContext getChild(IObservable target);
  
  /**
   * Set the passed data object in the symbol table.
   * 
   * @param name
   * @param data
   */
  void setData(String name, IObservationData data);
  
  /**
   * Set POD data or parameters.
   * 
   * @param name
   * @param value
   */
  void set(String name, Object value);
  
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

  /**
   * Export the network structure to a GEFX file.
   * 
   * @param outFile
   */
  void exportStructure(String outFile);
  
  /**
   * Return a valid monitor for any communication.
   * 
   * @return
   */
  IMonitor getMonitor();
}
