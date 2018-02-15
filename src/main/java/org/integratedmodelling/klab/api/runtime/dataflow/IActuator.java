package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * Each node in a dataflow is an actuator. The dataflow itself is a subclass of actuator that can
 * have children.
 * 
 * @author ferdinando.villa
 *
 */
public interface IActuator {

  /**
   * 
   * @return
   */
  String getName();

  /**
   * 
   * @return
   */
  List<IPort> getInputs();

  /**
   * 
   * @return
   */
  List<IPort> getOutputs();

  /**
   * Each actuator may have a specific scale, although all are constrained by the one of the
   * containing dataflow.
   * 
   * @return the scale. Only those for which a scale was specifically given will return a non-empty
   *         scale.
   */
  IScale getScale();
}
