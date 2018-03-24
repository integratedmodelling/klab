package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;

/**
 * This API extends {@link IComputationContext} to add setters and other functionalities that are
 * needed at runtime. It is used within common computation code so that any {@link IRuntimeProvider}
 * may implement the computation contexts as needed.
 * 
 * @author Ferd
 *
 */
public interface IRuntimeContext extends IComputationContext {

  /**
   * Create a child context for the passed actuator, containing a new target observation
   * implementation for the artifact type and geometry specified in the actuator. The observation is
   * created with the same scale of the current target and the current target is set as its parent.
   * 
   * @param target
   * 
   * @return the child context that will resolve the target
   */
  public IRuntimeContext createChild(IActuator target);

  /**
   * Create a child context for a previously instantiated observation, setting it as a target for
   * the computation. The passed observation must have been created using
   * {@link #newObservation(IObservable, org.integratedmodelling.kim.api.data.IGeometry)} or
   * {@link #newRelationship(IObservable, org.integratedmodelling.kim.api.data.IGeometry, IObjectArtifact, IObjectArtifact)}.
   * 
   * @param target
   * @param actuator the actuator handling the instantiation
   * @return the child context to resolve the target
   */
  public IRuntimeContext createChild(IObservation target, IActuator actuator);

  /**
   * Set the passed data object in the symbol table.
   * 
   * @param name
   * @param data
   */
  void setData(String name, IArtifact data);

  /**
   * Set POD data or parameters.
   * 
   * @param name
   * @param value
   */
  void set(String name, Object value);

  /**
   * Each context that handles a temporal scale must expose a configuration detector.
   * 
   * @return
   */
  IConfigurationDetector getConfigurationDetector();

  /**
   * Produce a deep copy of this context, with a new resource catalog, leaving only the original
   * instances of the context-wide members such as provenance, configuration detector, event bus and
   * structures. This is meant to be able to rename elements without harm.
   * 
   * @return an identical context with a rewriteable object catalog and parameters.
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
  void exportNetwork(String outFile);

  /**
   * The target artifact of the passed actuator's computation. In k.LAB usually an IObservation.
   * External computations may use non-semantic artifacts. These should be created if necessary; if
   * they haven't been already, they should be created as non-semantic artifacts.
   * 
   * @param actuator
   * 
   * @return the target artifact for the passed actuator.
   */
  IArtifact getTargetArtifact(IActuator actuator);

  /**
   * Set the main target of the computation being carried on by the actuator. Used by
   * Actuator.compute().
   * 
   * @param target
   */
  void setTarget(IArtifact target);

  /**
   * The API must be able to set the geometry for downstream computations.
   * 
   * @param geometry
   */
  void setGeometry(IGeometry geometry);

  /**
   * Called after successful computation passing each annotation that was defined for the model.
   * 
   * @param annotation
   */
  public void processAnnotation(IKimAnnotation annotation);


}
