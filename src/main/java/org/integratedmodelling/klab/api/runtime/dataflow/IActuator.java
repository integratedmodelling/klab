package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * Each node in a dataflow is an actuator. Some actuators may be references, in which case the
 * original actuator will always come before them.
 * 
 * @author ferdinando.villa
 *
 */
public interface IActuator {

  /**
   * All actuators have a name. References may provide a different name for the same actuator.
   * 
   * @return the name
   */
  String getName();


  /**
   * All child actuators in order of declaration.
   * 
   * @return all the internal actuators in order of declaration.
   */
  public List<IActuator> getActuators();

  /**
   * Return the subset of actuators that reference others in the same dataflow.
   * 
   * @return all imported actuators
   */
  List<IActuator> getInputs();

  /**
   * Return all actuators that have been declared as exported.
   * 
   * @return all exported actuators
   */
  List<IActuator> getOutputs();

  /**
   * Each actuator may have a specific scale.
   * 
   * @return the scale. Only the root dataflow and those actuators for which a scale was
   *         specifically given will return a non-empty scale.
   */
  IScale getScale();
}
