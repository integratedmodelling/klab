package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;
import org.integratedmodelling.klab.api.observations.scale.IScale;

public interface IActuator {

  String getName();

  List<IPort> getInputs();

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
