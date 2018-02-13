package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class ResolutionActuator<T extends IObservation> extends Actuator<T> {

  T            observation;
  IResolver<?> resolver;

  public ResolutionActuator() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public T run(IMonitor monitor) {
    // TODO Auto-generated method stub
    return null;
  }


}
