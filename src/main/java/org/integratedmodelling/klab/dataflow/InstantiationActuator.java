package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class InstantiationActuator<T extends ICountableObservation> extends Actuator<T> {

  T observation;
  IInstantiator<?> instantiator;
  
  public InstantiationActuator() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public T run(IMonitor monitor) {
    // TODO Auto-generated method stub
    return null;
  }


}
